package com.astrotalk.HospitalStaffManagement.service;

import com.astrotalk.HospitalStaffManagement.constant.enums.Designation;
import com.astrotalk.HospitalStaffManagement.dto.AuthenticationRequest;
import com.astrotalk.HospitalStaffManagement.dto.AuthenticationResponse;
import com.astrotalk.HospitalStaffManagement.dto.RegisterRequestBody;
import com.astrotalk.HospitalStaffManagement.entity.Staff;
import com.astrotalk.HospitalStaffManagement.exception.StaffNotExistsException;
import com.astrotalk.HospitalStaffManagement.repository.StaffRepository;
import com.astrotalk.HospitalStaffManagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest) {

        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        // Retrieve the staff entity by email
        Optional<Staff> staffOptional = staffRepository.findByStaffEmail(email);
        // Check if the staff exists and compare the passwords
        if (staffOptional.isPresent() && passwordEncoder.matches(password, staffOptional.get().getStaffPassword())) {
            Staff staff = staffOptional.get();
                final String jwt = jwtUtil.generateToken(authenticationRequest.getEmail(), new LinkedList<String>());
                return new AuthenticationResponse(jwt);
        }
        throw new StaffNotExistsException("Staff does Not Exists");
    }

    public Staff createStaff(RegisterRequestBody registerRequestBody) {

        if (staffRepository.findByStaffEmail(registerRequestBody.getEmail()).isEmpty()) {
            Staff staff = new Staff();
            staff.setStaffEmail(registerRequestBody.getEmail());
            staff.setStaffPassword(passwordEncoder.encode(registerRequestBody.getPassword()));
            staff.setStaffName(registerRequestBody.getName());
            staff.setStaffDesignation(Designation.valueOf(registerRequestBody.getDesignation().toString()));
            return staffRepository.save(staff);
        }
        return null;
    }
}
