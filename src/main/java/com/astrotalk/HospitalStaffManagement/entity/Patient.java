package com.astrotalk.HospitalStaffManagement.entity;

import com.astrotalk.HospitalStaffManagement.constant.enums.PatientStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patient_id")
    private Integer id;

    @NotEmpty(message="Name cannot be empty")
    private String patientName;

    @NotEmpty(message="Email cannot be empty")
    private String patientEmail;

    @NotNull(message="age cannot be empty")
    private Integer patientAge;

    @NotEmpty(message="Room cannot be empty")
    private String patientRoom;

    @NotEmpty(message="DoctorName cannot be empty")
    private String doctorName;

    @NotEmpty(message="mobile cannot be empty")
    private String patientMobile;

    @NotNull(message="Admit Date cannot be empty")
    private Date patientAdmitDate;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Expense> patientExpenses;

    private PatientStatus patientStatus;

    private int patientTotalExpenseAmount;

    private int patientTotalAmountPaid;

    private boolean patientExpensesSettled;

}
