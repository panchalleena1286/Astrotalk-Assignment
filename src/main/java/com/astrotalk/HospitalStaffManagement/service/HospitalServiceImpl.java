package com.astrotalk.HospitalStaffManagement.service;

import com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus;
import com.astrotalk.HospitalStaffManagement.entity.Patient;
import com.astrotalk.HospitalStaffManagement.exception.PatientAlreadyExistsException;
import com.astrotalk.HospitalStaffManagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus.ADMITTED;

@Service
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ExpenseService expenseService;


    @Override
    public List<HashMap<String, Object>> getAllAdmittedPatients() {

        List<Patient> admittedPatientsList = patientRepository.findAllByStatus(String.valueOf(ADMITTED));
        List<HashMap<String, Object>> patientMap = new LinkedList<>();
        for (Patient patient : admittedPatientsList) {
            HashMap<String, Object> res = new HashMap<String, Object>();
            res.put("name", patient.getName());
            res.put("age", patient.getAge());
            res.put("room", patient.getRoom());
            res.put("doctor", patient.getDoctorName());
            res.put("mobile", patient.getMobile());
            res.put("admit date", patient.getAdmitDate());
            res.put("status ", patient.getStatus());
            patientMap.add(res);
        }
        return patientMap;
    }

    @Override
    public HashMap<String, Object> getPatient(String name) {
        HashMap<String, Object> res = null;
        Optional<Patient> optionalPatient = patientRepository.findByName(name);
        if (optionalPatient.isPresent()) {
            res = new HashMap<>();
            Patient patient = optionalPatient.get();
            res.put("id", patient.getId());
            res.put("name", patient.getName());
            res.put("age", patient.getAge());
            if (patient.getExpenses() != null && !patient.getExpenses().isEmpty())
                res.put("expenses", patient.getExpenses());
            res.put("mobile", patient.getMobile());
            res.put("Status", patient.getStatus());
            return res;
        }
        return res;
    }

    @Override
    public Patient admitPatient(Patient patient) throws PatientAlreadyExistsException {
        Optional<Patient> optionalPatient = patientRepository.findByName(patient.getName());
        if (optionalPatient.isPresent()) {
            throw new PatientAlreadyExistsException("Patient Already Exists");
        }
        Patient patient1 = new Patient();
        patient1.setStatus(ADMITTED);
        patient1.setExpensesSettled(false);
        return patientRepository.save(patient);
    }

    @Override
    public String dischargePatient(String name) {
        Optional<Patient> optionalPatient = patientRepository.findByName(name);
        if (optionalPatient.isPresent() && optionalPatient.get().isExpensesSettled()) {
            Patient patient = optionalPatient.get();
            int total = expenseService.getTotalAmountPending(patient.getName());
            if (total > 0) {
                return "Patient cannot be discharged, Amount remaining: " + total;
            } else {
                patient.setStatus(PatientStatus.DISCHARGED);
                patient.setExpensesSettled(true);
                patientRepository.save(patient);
                return "Patient discharged successfully";
            }
        }
        return "Patient does not Exists";
    }

    public String updatePatient(Patient patient) {
        Optional<Patient> optionalPatient = patientRepository.findByName(patient.getName());
        if(optionalPatient.isPresent()){
            Patient existing = optionalPatient.get();
            if(existing.getAge() != null){
                existing.setAge(patient.getAge());
            }
            if(patient.getName() != null){
                existing.setName(patient.getName());
            }
            if(patient.getRoom() != null){
                existing.setRoom(patient.getRoom());
            }
            if(patient.getMobile() != null){
                existing.setMobile(patient.getMobile());
            }
            if(patient.getDoctorName() != null){
                existing.setDoctorName(patient.getDoctorName());
            }
            patientRepository.save(existing);
        }
        return "Patient details Successfully updated";
    }
}
