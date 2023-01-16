package com.compensationplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compensationplan.entities.CompensationPlan;
import com.compensationplan.entities.Users;
import com.compensationplan.payload.request.CreatePlanRequest;
import com.compensationplan.payload.response.MessageResponse;
import com.compensationplan.service.CompensationPlanService;
import com.compensationplan.service.UserService;

@RestController
@RequestMapping("compensationplan")
@CrossOrigin
public class CompensationPlanController {
	
	@Autowired
	CompensationPlanService compensationPlanService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/createplan/{userId}")
	public ResponseEntity<?> saveCompensationPlan(@RequestBody CreatePlanRequest createPlanRequest, @PathVariable Long userId) {
		
		Users user = null;
		if(userService.existByEmployeeId(userId)) {
			user = userService.findByEmployeeId(userId);
			if(!user.getRole().getRoleId().equals("U001")) {
				return ResponseEntity.badRequest().body(new MessageResponse("This user is not a plan creator"));
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		CompensationPlan plan = new CompensationPlan();
		plan.setCreaterId(user);
		plan.setPartnerName(createPlanRequest.getPartnerName());
		plan.setCompensationPlan(createPlanRequest.getCompensationPlan());
		plan.setCalculationMethod(createPlanRequest.getCalculationMethod());
		plan.setMinimum(createPlanRequest.getMinimum());
		plan.setMaximum(createPlanRequest.getMaximum());
		plan.setPercentage(createPlanRequest.getPercentage());
		plan.setFromDate(createPlanRequest.getFromDate());
		plan.setToDate(createPlanRequest.getToDate());
		
		return compensationPlanService.saveCompensationPlan(plan);
	}
	
	@GetMapping("/getallplans")
	public ResponseEntity<?> getAllCompensationPlan() {
		List<CompensationPlan> planList = compensationPlanService.getAllCompensationPlan();
		
		return ResponseEntity.ok(planList);
	}
	
	@GetMapping("/getmyplans/{employeeId}")
	public ResponseEntity<?> getMyCompensationPlan(@PathVariable Long employeeId) {
		
		Users user = null;
		if(userService.existByEmployeeId(employeeId)) {
			user = userService.findByEmployeeId(employeeId);
			if(!user.getRole().getRoleId().equals("U001")) {
				return ResponseEntity.badRequest().body(new MessageResponse("This user is not a plan creator"));
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<CompensationPlan> planList = compensationPlanService.getMyCompensationPlan(user);
		
		return ResponseEntity.ok(planList);
	}

}
