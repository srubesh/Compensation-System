package com.compensationplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compensationplan.entities.CompensationPlan;


@Repository
public interface CompensationPlanRepository extends JpaRepository<CompensationPlan, Long>{
	
	List<CompensationPlan> findAll();

}
