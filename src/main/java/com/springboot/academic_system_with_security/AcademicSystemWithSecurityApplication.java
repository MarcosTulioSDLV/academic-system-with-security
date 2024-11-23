package com.springboot.academic_system_with_security;

import com.springboot.academic_system_with_security.enums.RoleName;
import com.springboot.academic_system_with_security.models.Role;
import com.springboot.academic_system_with_security.models.UserEntity;
import com.springboot.academic_system_with_security.repository.RoleRepository;
import com.springboot.academic_system_with_security.repository.UserRepository;
import com.springboot.academic_system_with_security.services.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AcademicSystemWithSecurityApplication implements CommandLineRunner {

	private final PasswordEncoder passwordEncoder;

	@Autowired
    public AcademicSystemWithSecurityApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(AcademicSystemWithSecurityApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Default Admin user: marcos\n" +
				"Default password: 123, Encoded password: "+passwordEncoder.encode("123"));
	}

}
