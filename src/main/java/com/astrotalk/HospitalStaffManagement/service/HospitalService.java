package com.astrotalk.HospitalStaffManagement.service;

import com.astrotalk.HospitalStaffManagement.dto.PatientRequestBody;
import com.astrotalk.HospitalStaffManagement.entity.Patient;
import com.astrotalk.HospitalStaffManagement.exception.PatientAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface HospitalService {

    public List<HashMap<String, Object>> getAllAdmittedPatients();
    public HashMap<String, Object> getPatientByEmail(String email);
    public Patient admitPatient(PatientRequestBody p);
    public String dischargePatient(String email);
    public String updatePatient(Patient patient);
}
