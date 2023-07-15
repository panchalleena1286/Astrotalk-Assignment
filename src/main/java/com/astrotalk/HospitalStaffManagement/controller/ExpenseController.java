package com.astrotalk.HospitalStaffManagement.controller;

import com.astrotalk.HospitalStaffManagement.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;

	@GetMapping("/getTotalAmountPending")
	public ResponseEntity<Object> getTotalAmountPending(@RequestHeader("Authorization") String auth,
			@RequestParam String email) {
		return ResponseEntity.ok(expenseService.getTotalAmountPending(email));
	}

	@GetMapping("/getAllBill")
	public ResponseEntity<Object> getBillsOfPatient(@RequestHeader("Authorization") String auth, @RequestParam String email) {

		return ResponseEntity.ok(expenseService.getAllBills(email));
	}

}
