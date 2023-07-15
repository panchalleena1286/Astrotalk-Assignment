package com.astrotalk.HospitalStaffManagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashMap;

@Entity
@Data
@Table(name = "expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Date date;

	private String item_Name;

	private int item_Price;

	private int item_Unit;

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff billedBy;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	private boolean cleared;

}