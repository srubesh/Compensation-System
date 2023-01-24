package com.compensationplan.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.compensationplan.entities.CompensationPlan;
import com.compensationplan.payload.request.CreatePlanRequest;

class CompensationPlanMapperTest {

	private CompensationPlanMapper mapper = Mappers.getMapper(CompensationPlanMapper.class);
	
  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
	  CreatePlanRequest simpleSource = new CreatePlanRequest();
      simpleSource.setPartnerName("partner name");
      CompensationPlan destination = mapper.sourceToDestination(simpleSource);

      assertEquals(simpleSource.getPartnerName(), destination.getPartnerName());
  }

}
