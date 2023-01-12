package com.compensationplan.payload.request;

import java.util.Date;

import com.compensationplan.entities.Users;

import lombok.Data;

@Data
public class CreatePlanRequest {

	private String partnerName;

	private String compensationPlan;

	private String calculationMethod;

	private int minimum;

	private int maximum;

	private int percentage;

	private Date fromDate;

	private Date toDate;

}
