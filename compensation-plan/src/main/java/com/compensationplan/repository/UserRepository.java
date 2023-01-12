package com.compensationplan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compensationplan.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	
	Boolean existsByEmployeeId(Long employeeId);
	
	Optional<Users> findByEmployeeId(Long employeeId);
	
	List<Users> findAll();

}
