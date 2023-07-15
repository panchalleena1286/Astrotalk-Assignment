package com.astrotalk.HospitalStaffManagement.service;

import com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus;
import com.astrotalk.HospitalStaffManagement.dto.PatientRequestBody;
import com.astrotalk.HospitalStaffManagement.entity.Patient;
import com.astrotalk.HospitalStaffManagement.exception.PatientAlreadyExistsException;
import com.astrotalk.HospitalStaffManagement.exception.PatientNotExistsException;
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

        List<Patient> admittedPatientsList = patientRepository.findAllByPatientStatus(PatientStatus.ADMITTED);
        if(admittedPatientsList.size() !=0){
            List<HashMap<String, Object>> patientMapList = new LinkedList<>();
            for (Patient patient : admittedPatientsList) {
                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("name", patient.getPatientName());
                result.put("email", patient.getPatientEmail());
                result.put("age", patient.getPatientAge());
                result.put("room", patient.getPatientRoom());
                result.put("doctor", patient.getDoctorName());
                result.put("mobile", patient.getPatientMobile());
                result.put("admitDate", patient.getPatientAdmitDate());
                result.put("status ", patient.getPatientStatus());
                patientMapList.add(result);

            }
            return patientMapList;
        }
        throw new PatientNotExistsException("No Patient exists");
    }

    @Override
    public HashMap<String, Object> getPatientByEmail(String email) {

        Optional<Patient> optionalPatient = patientRepository.findByPatientEmail(email);
        if (optionalPatient.isPresent()) {
            HashMap<String, Object> result = new HashMap<>();
            Patient patient = optionalPatient.get();
            result.put("id", patient.getId());
            result.put("name", patient.getPatientName());
            result.put("email", patient.getPatientEmail());
            result.put("age", patient.getPatientAge());
            result.put("room", patient.getPatientRoom());
            result.put("mobile", patient.getPatientMobile());
            result.put("Status", patient.getPatientStatus());
            result.put("doctor", patient.getDoctorName());
            result.put("admitDate", patient.getPatientAdmitDate());
            return result;
        }
        throw new PatientNotExistsException("Patient does not exist");
    }

    @Override
    public Patient admitPatient(PatientRequestBody patient) throws PatientAlreadyExistsException {
        Optional<Patient> optionalPatient = patientRepository.findByPatientEmail(patient.getPatientEmail());
        if (optionalPatient.isEmpty()) {
            Patient patientMock = new Patient();
            patientMock.setPatientName(patient.getPatientName());
            patientMock.setPatientEmail(patient.getPatientEmail());
            patientMock.setPatientAge(patient.getPatientAge());
            patientMock.setPatientRoom(patient.getPatientRoom());
            patientMock.setDoctorName(patient.getDoctorName());
            patientMock.setPatientMobile(patient.getPatientMobile());
            patientMock.setPatientAdmitDate(patient.getPatientAdmitDate());
            patientMock.setPatientStatus(ADMITTED);
            patientMock.setPatientExpensesSettled(false);
            return patientRepository.save(patientMock);
        }
        throw new PatientAlreadyExistsException("Patient Already Exists");
    }

    @Override
    public String dischargePatient(String email) {
        Optional<Patient> optionalPatient = patientRepository.findByPatientEmail(email);
        if (optionalPatient.isPresent() && optionalPatient.get().isPatientExpensesSettled()) {
            Patient patient = optionalPatient.get();
            int total = expenseService.getTotalAmountPending(patient.getPatientEmail());
            if (total > 0) {
                return "Patient cannot be discharged, Pending Amount: " + total;
            } else {
                patient.setPatientStatus(PatientStatus.DISCHARGED);
                patient.setPatientExpensesSettled(true);
                patientRepository.save(patient);
                return "Patient discharged successfully";
            }
        }
        throw new PatientNotExistsException("Patient does not exist");
    }

    public String updatePatient(Patient patient) {
        Optional<Patient> optionalPatient = patientRepository.findByPatientEmail(patient.getPatientEmail());
        if(optionalPatient.isPresent()){
            Patient existing = optionalPatient.get();
            if(existing.getPatientAge() != null){
                existing.setPatientAge(patient.getPatientAge());
            }
            if(patient.getPatientRoom() != null){
                existing.setPatientRoom(patient.getPatientRoom());
            }
            if(patient.getPatientMobile() != null){
                existing.setPatientMobile(patient.getPatientMobile());
            }
            if(patient.getDoctorName() != null){
                existing.setDoctorName(patient.getDoctorName());
            }
            patientRepository.save(existing);
            return "Patient details Successfully updated";
        }
        throw new PatientNotExistsException("Patient does not exist");
    }
}
