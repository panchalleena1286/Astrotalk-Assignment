package com.astrotalk.HospitalStaffManagement.dto;

import com.astrotalk.HospitalStaffManagement.constant.enums.Designation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequestBody {

	private String name;

	private String email;

	private String password;

	private Designation designation;

	private String department;

}
