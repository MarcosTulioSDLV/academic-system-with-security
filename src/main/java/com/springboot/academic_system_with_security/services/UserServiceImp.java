package com.springboot.academic_system_with_security.services;

import com.springboot.academic_system_with_security.dtos.UserRequestDto;
import com.springboot.academic_system_with_security.dtos.UserResponseDto;
import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.exceptions.InvalidRoleIdsException;
import com.springboot.academic_system_with_security.exceptions.UserNotFoundException;
import com.springboot.academic_system_with_security.exceptions.UsernameExistsException;
import com.springboot.academic_system_with_security.mappers.UserMapper;
import com.springboot.academic_system_with_security.models.Course;
import com.springboot.academic_system_with_security.models.CourseStudent;
import com.springboot.academic_system_with_security.models.Role;
import com.springboot.academic_system_with_security.models.UserEntity;
import com.springboot.academic_system_with_security.repository.UserRepository;
import com.springboot.academic_system_with_security.security.Util.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Autowired
    public UserServiceImp(UserRepository userRepository,UserMapper userMapper, RoleService roleService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        UserEntity user= findUserById(id);
        return userMapper.toUserResponseDto(user);
    }

    private UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with id: " + id + " not found!"));
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        UserEntity user= userMapper.toUser(userRequestDto);
        Long roleAdminId= roleService.findRoleByRoleName(RoleName.ROLE_ADMIN).getId();

        user = registerUser(user,List.of(roleAdminId));

        return userMapper.toUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserEntity registerUser(UserEntity user,List<Long> roleIds){
        validateUniqueUsername(user);

        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        List<Role> roles= roleService.findRolesByIds(roleIds);
        validateNotEmptyRoleList(roles);

        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }

    private void validateUniqueUsername(UserEntity user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UsernameExistsException("Username: "+ user.getUsername()+" already exists!");
        }
    }

    private void validateNotEmptyRoleList(List<Role> roles) {
        if(roles.isEmpty()){
            throw new InvalidRoleIdsException("Roles do not exist!");
        }
    }

    @Override
    @Transactional
    public String loginUser(UserRequestDto userRequestDto) {
        UserEntity user= userMapper.toUser(userRequestDto);
        var usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.createToken(authentication);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto,Long id) {
        UserEntity user= userMapper.toUser(userRequestDto);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity recoveredUser= findUserById(id);
        validateFieldsUpdateConflict(user,recoveredUser);

        BeanUtils.copyProperties(user,recoveredUser,"roles","student","professor");
        return userMapper.toUserResponseDto(recoveredUser);
    }

    private void validateFieldsUpdateConflict(UserEntity user, UserEntity recoveredUser) {
        if(usernameExistsAndBelongsToAnotherInstance(user.getUsername(), recoveredUser)){
            throw new UsernameExistsException("Username: "+ user.getUsername()+" already exists!");
        }
    }

    private boolean usernameExistsAndBelongsToAnotherInstance(String username,UserEntity recoveredUser) {
        return userRepository.existsByUsername(username) && !username.equals(recoveredUser.getUsername());
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        UserEntity user= findUserById(id);
        unenrollFromRelatedCourses(user);
        userRepository.delete(user);
    }

    private void unenrollFromRelatedCourses(UserEntity user){
        if(user.getStudent()==null)//Note:This is necessary because the user could be a student or an admin (in those cases,the student property would be null).
            return;
        user.getStudent().getCourseStudentList().stream()
                .map(CourseStudent::getCourse)
                .forEach(Course::unenrollStudent);
    }

}
