package com.astrotalk.HospitalStaffManagement.repository;

import com.astrotalk.HospitalStaffManagement.entity.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Optional<Staff> findByStaffEmail(String email);

}
