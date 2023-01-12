package com.compensationplan.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;


@Data
@Entity
public class CompensationPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(columnDefinition = "BIGINT(20) NOT NULL UNIQUE KEY auto_increment")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATER_ID")
	private Users createrId;
	
	private String partnerName;
	
	private String compensationPlan;
	
	private String calculationMethod;
	
	private int minimum;
	
	private int maximum;
	
	private int percentage;
	
	private Date fromDate;
	
	private Date toDate;
	

}
