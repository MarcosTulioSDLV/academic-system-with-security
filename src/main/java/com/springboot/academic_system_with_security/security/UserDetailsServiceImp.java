package com.springboot.academic_system_with_security.security;

import com.springboot.academic_system_with_security.models.UserEntity;
import com.springboot.academic_system_with_security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional//Note:This is necessary because roles are Lazy be default
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity= userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username: "+username+" not found!"));
        userEntity.getRoles().size();//Note:This is necessary because roles are Lazy be default //OR: Hibernate.initialize(userEntity.getRoles());//Force initialization of the role collection
        return userEntity;
    }

}
