package com.compensationplan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compensationplan.entities.CompensationPlan;
import com.compensationplan.payload.request.CreatePlanRequest;
import com.compensationplan.payload.response.MessageResponse;
import com.compensationplan.repository.CompensationPlanRepository;

@Service
public class CompensationPlanService {
	
	@Autowired
	CompensationPlanRepository compensationPlanRepository;

	public ResponseEntity<?> saveCompensationPlan(CompensationPlan plan) {
		
		compensationPlanRepository.save(plan);
		return ResponseEntity.ok(new MessageResponse("Plan created successfully!"));
	}

	public List<CompensationPlan> getAllCompensationPlan() {
		List<CompensationPlan> planList = compensationPlanRepository.findAll();
		return planList;
	}

}
