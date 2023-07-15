package com.astrotalk.HospitalStaffManagement.config.filter;

import com.astrotalk.HospitalStaffManagement.entity.Staff;
import com.astrotalk.HospitalStaffManagement.service.UserDetailsServiceImpl;
import com.astrotalk.HospitalStaffManagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String AuthHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;
        if (AuthHeader != null && AuthHeader.startsWith("Bearer")) {
            jwt = jwtutil.resolveToken(request);
            username = jwtutil.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Staff staff = (Staff) this.userDetailsService.loadUserByUsername(username);
            if (jwtutil.validateToken(jwt, staff.getStaffEmail())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
