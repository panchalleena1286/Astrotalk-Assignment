package com.astrotalk.HospitalStaffManagement.controller;


import com.astrotalk.HospitalStaffManagement.dto.AuthenticationRequest;
import com.astrotalk.HospitalStaffManagement.dto.AuthenticationResponse;
import com.astrotalk.HospitalStaffManagement.dto.RegisterRequestBody;
import com.astrotalk.HospitalStaffManagement.entity.Staff;
import com.astrotalk.HospitalStaffManagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private LoginService loginService;
    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Generate JWT token
            AuthenticationResponse authenticationResponse = loginService.createAuthenticationToken(authenticationRequest);
            // Return the token in the response
            return ResponseEntity.ok(authenticationResponse);

        } catch (AuthenticationException e) {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }



    }

    @PostMapping(path = "/signup", consumes = "application/Json")
    public ResponseEntity<Object> createStaff(@RequestBody RegisterRequestBody registerRequestBody) {
        Staff staff = loginService.createStaff(registerRequestBody);
        if (staff != null) {
            System.out.println("Staff created successfully: " );
            return new ResponseEntity<>(staff, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Staff already exists", HttpStatus.FORBIDDEN);
        }
    }
}