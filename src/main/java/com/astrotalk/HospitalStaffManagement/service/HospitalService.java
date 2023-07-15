package com.astrotalk.HospitalStaffManagement.service;

import com.astrotalk.HospitalStaffManagement.entity.Patient;
import com.astrotalk.HospitalStaffManagement.exception.PatientAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface HospitalService {

    public List<HashMap<String, Object>> getAllAdmittedPatients();
    public HashMap<String, Object> getPatient(String name);
    public Patient admitPatient(Patient p);
    public String dischargePatient(String name);
    public String updatePatient(Patient patient);
}
