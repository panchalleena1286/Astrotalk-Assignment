package com.astrotalk.HospitalStaffManagement.dto;

import com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus;
import com.astrotalk.HospitalStaffManagement.entity.Expense;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class PatientRequestBody {

	private String name;

	private int age;

	private String mobile;

	private String room;

	private String doctorName;

	private Date admitDate;

}
