package com.compensationplan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compensationplan.entities.Users;
import com.compensationplan.payload.response.MessageResponse;
import com.compensationplan.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public Boolean existByEmployeeId(Long employeeId) {
		return userRepo.existsByEmployeeId(employeeId);
	}

	public ResponseEntity<?> saveUser(Users user) {		
		userRepo.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	public List<Users> findAllUsers() {
		List<Users> user =  userRepo.findAll();
		
		return user;
		
	}

	public Users findByEmployeeId(Long id) {
		Optional<Users> user = userRepo.findByEmployeeId(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	
}
