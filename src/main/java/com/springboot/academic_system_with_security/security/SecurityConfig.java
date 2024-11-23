package com.springboot.academic_system_with_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf->csrf.disable())
                //.httpBasic(Customizer.withDefaults())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize-> authorize
                        //For Swagger and OpenAPI (Allow access to Swagger and OpenAPI without authentication)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        //For Role class
                        .requestMatchers(HttpMethod.GET,"/api/roles").hasRole("ADMIN")//Only admins can view all the available roles.
                        .requestMatchers(HttpMethod.GET,"/api/roles-by-id/*").hasRole("ADMIN")//Only admins can view a specific role by its Id.
                        //For User class
                        .requestMatchers(HttpMethod.POST,"/auth/users-admin-register").hasRole("ADMIN")//Only admins can register other admin users.
                        .requestMatchers(HttpMethod.POST,"/auth/users-login").permitAll()//Public endpoint for user login (anyone can log in).
                        .requestMatchers(HttpMethod.PUT,"/auth/users/*").hasRole("ADMIN")//Only admins can update the profiles of other users, regardless of their role.
                        .requestMatchers(HttpMethod.GET, "/auth/users").hasRole("ADMIN")//Only admins can view the profiles of all users, regardless of their role.
                        .requestMatchers(HttpMethod.GET, "/auth/users-by-id/*").hasRole("ADMIN")//Only admins can view the details of a specific user by its Id, regardless of the user's role.
                        .requestMatchers(HttpMethod.DELETE, "/auth/users/*").hasRole("ADMIN")//Only admins can delete the profiles of other users, regardless of their role.
                        //For Professor class
                        .requestMatchers(HttpMethod.GET,"/api/professors").hasRole("ADMIN")//Only admin can view all professors.
                        .requestMatchers(HttpMethod.GET,"/api/professors-by-id/*").hasAnyRole("ADMIN","PROFESSOR")//Professors can view their own profile, admin can view any professor's profile.
                        .requestMatchers(HttpMethod.POST,"/api/professors-register").hasRole("ADMIN")//Only admin can register professors.
                        .requestMatchers(HttpMethod.PUT,"/api/professors/*").hasAnyRole("ADMIN","PROFESSOR")//Professors can update their own profile, admin can update any profile.
                        .requestMatchers(HttpMethod.DELETE,"/api/professors/*").hasAnyRole("ADMIN")//Only admin can delete professors.
                        //For Course class
                        .requestMatchers(HttpMethod.GET,"/api/courses").hasAnyRole("ADMIN","STUDENT")//Both admin and students can view the list of available courses.
                        .requestMatchers(HttpMethod.GET,"/api/courses-by-id/*").hasRole("ADMIN")//Only admin can view the details of a specific course by its Id.
                        .requestMatchers(HttpMethod.GET,"/api/courses-by-professor-id/*").hasAnyRole("ADMIN","PROFESSOR")//Admin and professors can view the list of courses taught by a specific professor.
                        .requestMatchers(HttpMethod.POST,"/api/courses/*").hasRole("ADMIN")//Only admin can create new courses.
                        .requestMatchers(HttpMethod.PUT,"/api/courses/*").hasRole("ADMIN")//Only admin can update course information.
                        .requestMatchers(HttpMethod.DELETE,"/api/courses/*").hasRole("ADMIN")//Only admin can delete courses.
                        //For Student class
                        .requestMatchers(HttpMethod.GET,"/api/students").hasRole("ADMIN")//Only admin can view all students.
                        .requestMatchers(HttpMethod.GET,"/api/students-by-id/*").hasAnyRole("ADMIN","STUDENT")//Students can view their own profile, admin can view any student's profile.
                        .requestMatchers(HttpMethod.POST,"/api/students-register").hasRole("ADMIN")//Only admin can register students.
                        .requestMatchers(HttpMethod.PUT,"/api/students/*").hasAnyRole("ADMIN","STUDENT")//Students can update their own profile, admin can update any profile.
                        .requestMatchers(HttpMethod.DELETE,"/api/students/*").hasAnyRole("ADMIN")//Only admin can delete students
                        //for CourseStudent class
                        .requestMatchers(HttpMethod.GET,"/api/course-students-by-id").hasRole("ADMIN")//Only admin can retrieve course-student records by Id.
                        .requestMatchers(HttpMethod.GET,"/api/students/*/courses").hasAnyRole("ADMIN","STUDENT")//Students can view their own courses; admin can view any student's courses.
                        .requestMatchers(HttpMethod.GET,"/api/course-students/*/details-course").hasAnyRole("ADMIN","STUDENT")//Students can view their own course details; admin can view any student's course details.
                        .requestMatchers(HttpMethod.GET,"/api/courses/*/students").hasAnyRole("ADMIN","PROFESSOR")//Professors can view students enrolled in their courses; admin can view any course's students.
                        .requestMatchers(HttpMethod.GET,"/api/courses/*/students-containing-name").hasAnyRole("ADMIN","PROFESSOR")//Professors can view students containing any name in their courses; admin can access students containing any name in all courses.
                        .requestMatchers(HttpMethod.GET,"/api/course-students/*/details-student").hasAnyRole("ADMIN","PROFESSOR")//Professors can view students' details in their courses; admin can view students' details in all courses.
                        .requestMatchers(HttpMethod.POST,"/api/course-students").hasAnyRole("ADMIN","STUDENT")//Students can enroll in courses; admin can enroll students in courses.
                        .requestMatchers(HttpMethod.PUT,"/api/course-students-score").hasAnyRole("ADMIN","PROFESSOR")//Both admin and professors can update student scores.
                        .requestMatchers(HttpMethod.DELETE,"/api/course-students").hasAnyRole("ADMIN","STUDENT")//Students can unenroll from courses; admin can unenroll students from courses.
                        .anyRequest().denyAll())
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)//Or UsernamePasswordAuthenticationFilter.class //BasicAuthenticationFilter.class
                .build();
    }

}
