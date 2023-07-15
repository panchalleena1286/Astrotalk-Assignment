package com.astrotalk.HospitalStaffManagement.controller;


import com.astrotalk.HospitalStaffManagement.entity.Patient;
import com.astrotalk.HospitalStaffManagement.exception.PatientNotExistsException;
import com.astrotalk.HospitalStaffManagement.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/patients")
    public ResponseEntity<Object> getAllAdmittedPatients(@RequestHeader("Authorization")String auth) {

        return ResponseEntity.ok(hospitalService.getAllAdmittedPatients());
    }

    @GetMapping("/patients/{name}")
    public ResponseEntity<Object> getPatient(@RequestHeader("Authorization")String auth, @RequestParam String name) {

        try {
            HashMap<String, Object> patient = hospitalService.getPatient(name);
            if(patient == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Patient Found with name");
            }
            return ResponseEntity.ok(patient);
        }catch(PatientNotExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(path="/admit", consumes="application/json")
    public Patient admitPatient(@RequestHeader("Authorization")String auth ,@RequestBody Patient patient) {

        return hospitalService.admitPatient(patient);
    }

    @PutMapping("/update/patient")
    public String updatePatient(@RequestHeader("Authorization")String auth ,@RequestBody Patient patient){
        return hospitalService.updatePatient(patient);
    }

    @PutMapping(path="/discharge/patient/{name}", consumes="application/json")
    public ResponseEntity<Object> dischargePatient(@RequestHeader("Authorization")String auth ,@RequestBody String name) {

        return ResponseEntity.ok(hospitalService.dischargePatient(name));
    }

}
