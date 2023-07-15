package com.astrotalk.HospitalStaffManagement.repository;

import com.astrotalk.HospitalStaffManagement.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    List<Patient> findAllByStatus(String status);
    Optional<Patient> findByName(String name);
}
