package com.astrotalk.HospitalStaffManagement.entity;

import com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message="Name cannot be empty")
    private String name;

    @NotEmpty(message="age cannot be empty")
    private Integer age;

    @NotEmpty(message="Room cannot be empty")
    private String room;

    @NotEmpty(message="DoctorName cannot be empty")
    @JoinColumn(name = "staff_name")
    private String doctorName;

    @NotEmpty(message="mobile cannot be empty")
    private String mobile;

    @NotEmpty(message="Admit Date cannot be empty")
    private Date admitDate;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    private PatientStatus status;

    private int totalExpenseAmount;

    private int totalAmountPaid;

    private boolean expensesSettled;
}