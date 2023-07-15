package com.astrotalk.HospitalStaffManagement.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;

import com.astrotalk.HospitalStaffManagement.constant.enums.Designation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "staff")
@Data
public class Staff implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "staff_id")
    private Integer id;

    @Column(name = "staff_name")
    @NotEmpty(message="Name cannot be empty")
    private String staffName;

    @Column(name = "staff_email",unique = true)
    @NotEmpty(message = "Email cannot be empty")
    private String staffEmail;

    @Column(name = "staff_password")
    @NotEmpty(message="Password cannot be empty")
    private String staffPassword;

    @Column(name = "staff_designation")
    private Designation staffDesignation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
