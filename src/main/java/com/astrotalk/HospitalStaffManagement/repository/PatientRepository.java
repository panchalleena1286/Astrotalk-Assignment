package com.astrotalk.HospitalStaffManagement.repository;

import com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus;
import com.astrotalk.HospitalStaffManagement.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    List<Patient> findAllByPatientStatus(PatientStatus status);
    Optional<Patient> findByPatientEmail(String email);
}
