package com.springboot.academic_system_with_security.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.academic_system_with_security.models.UserEntity;
import com.springboot.academic_system_with_security.repository.UserRepository;
import com.springboot.academic_system_with_security.security.Util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    //METHOD 1 (Authorization by Retrieving Permissions from the Database)
    /*
        Description: In this approach, the token is only used to authenticate the user’s identity. For each request, user details, including permissions, are retrieved from the database.
        Advantage: This method ensures that role or permission changes in the database are immediately enforced, reducing the risk of outdated permissions.
        Disadvantage: It adds extra load to the database and can impact performance, especially in high-traffic systems, as each request requires a new database lookup for user roles and permissions.
        Revoked Access: Immediately prevents access for deleted or deactivated users since the database is checked each time.
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken= request.getHeader("Authorization");//Or HttpHeaders.AUTHORIZATION
        if(jwtToken!=null){
            jwtToken= jwtToken.substring(7);
            DecodedJWT decodedJWT= jwtUtils.validateToken(jwtToken);
            String username= jwtUtils.extractUsername(decodedJWT);

            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            List<? extends GrantedAuthority> authorityList= new ArrayList<>(userDetails.getAuthorities());

            var authentication= new UsernamePasswordAuthenticationToken(username,null,authorityList);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    //METHOD 2 (Authorization Based on Permissions Stored in JWT)
    /*
        Description: Permissions and roles are embedded directly in the JWT token. Once the token is generated, all required data is within the token itself.
        Advantage: This is highly efficient because it eliminates the need for database queries on each request, improving response times and reducing load on the database.
        Disadvantage: Since permissions are "frozen" in the token until it expires, any changes to user roles or permissions in the database won’t immediately reflect in the token. This could potentially allow unauthorized access if a user’s roles change but their token is still valid.
        Removed User Access: If a user is deleted from the database, they can still access the system until the token expires, as their credentials are verified solely from the token without checking the database.
     */
    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken= request.getHeader("Authorization");//Or HttpHeaders.AUTHORIZATION
        if(jwtToken!=null){
            jwtToken= jwtToken.substring(7);
            DecodedJWT decodedJWT= jwtUtils.validateToken(jwtToken);

            String username= jwtUtils.extractUsername(decodedJWT);
            String authorities= jwtUtils.extractClaim(decodedJWT,"authorities").asString();
            List<? extends GrantedAuthority> authorityList= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            var authentication= new UsernamePasswordAuthenticationToken(username,null,authorityList);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }*/

}
