package com.astrotalk.HospitalStaffManagement.service;


import com.astrotalk.HospitalStaffManagement.entity.Expense;
import com.astrotalk.HospitalStaffManagement.entity.Patient;
import com.astrotalk.HospitalStaffManagement.exception.PatientNotExistsException;
import com.astrotalk.HospitalStaffManagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private PatientRepository patientRepository;

    public int getTotalAmountPending(String name) {
        Optional<Patient> patientOptional = patientRepository.findByName(name);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            List<Expense> expenses = patient.getExpenses();
            int totalExpenseAmount = expenses.stream().mapToInt(Expense::getItem_Price).sum();
            return totalExpenseAmount - patient.getTotalAmountPaid();
        } else {
            throw new PatientNotExistsException("Patient Not Exists");
        }
    }

    public Map<String, Integer> getAllBills(String name) {
        Optional<Patient> patientOptional = patientRepository.findByName(name);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            List<Expense> expenses = patient.getExpenses();
            Map<String, Integer> bills = new HashMap<>();
            for (Expense expense : expenses) {
                bills.put(expense.getItem_Name(), expense.getItem_Price());
            }
            return bills;
        } else {
            throw new PatientNotExistsException("Patient Not Exists");
        }
    }


}

