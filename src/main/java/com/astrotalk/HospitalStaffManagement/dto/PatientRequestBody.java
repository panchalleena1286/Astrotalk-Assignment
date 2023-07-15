package com.astrotalk.HospitalStaffManagement.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PatientRequestBody {

	private String patientName;

	private String patientEmail;

	private Integer patientAge;

	private String patientMobile;

	private String patientRoom;

	private String doctorName;

	private Date patientAdmitDate;
}
