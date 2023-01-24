package com.compensationplan.mapper;

import org.mapstruct.Mapper;

import com.compensationplan.entities.CompensationPlan;
import com.compensationplan.payload.request.CreatePlanRequest;

@Mapper
public interface CompensationPlanMapper {
	
	CompensationPlan sourceToDestination(CreatePlanRequest source);

}
