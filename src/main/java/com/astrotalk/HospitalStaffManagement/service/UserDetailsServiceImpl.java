package com.astrotalk.HospitalStaffManagement.service;

import com.astrotalk.HospitalStaffManagement.entity.Staff;
import com.astrotalk.HospitalStaffManagement.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails userDetails = null;
        Optional<Staff> staffOptional = staffRepository.findByStaffEmail(email);
        if (staffOptional.isPresent())
            userDetails = staffOptional.get();
        else {
            throw new UsernameNotFoundException(email);
        }
        return userDetails;
    }
}
