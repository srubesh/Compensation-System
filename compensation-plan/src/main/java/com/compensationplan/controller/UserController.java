package com.compensationplan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compensationplan.entities.ERole;
import com.compensationplan.entities.Role;
import com.compensationplan.entities.Users;
import com.compensationplan.payload.request.LoginRequest;
import com.compensationplan.payload.request.SignupRequest;
import com.compensationplan.payload.response.JwtResponse;
import com.compensationplan.payload.response.MessageResponse;
import com.compensationplan.repository.RoleRepository;
import com.compensationplan.security.jwt.JwtUtils;
import com.compensationplan.security.service.UserDetailsImpl;
import com.compensationplan.service.UserService;


@RestController
@RequestMapping("compensationplan")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signup")
	public ResponseEntity<?> saveUser(@RequestBody SignupRequest signupRequest) {
		
		if(userService.existByEmployeeId(signupRequest.getEmployeeId())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: EmployeeID already exists!"));
		}
		
		String strRoles = signupRequest.getRole();
		Role role;

		switch (strRoles) {
			case "plan_user":
				Role planUser = roleRepository.findByName(ERole.ROLE_PLAN_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				role = planUser;
				break;
				
//			case "admin":
//				Role adminUser = roleRepository.findByName(ERole.ROLE_ADMIN_USER)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				role = adminUser;
//				break;
				
			default:
				Role reportUser = roleRepository.findByName(ERole.ROLE_REPORT_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				role = reportUser;
		}
		

		Users user = new Users();
		user.setEmployeeId(signupRequest.getEmployeeId());
		user.setFirstname(signupRequest.getFirstname());
		user.setLastname(signupRequest.getLastname());
		user.setLocation(signupRequest.getLocation());
		user.setJobTitle(signupRequest.getJobTitle());
		user.setDepartment(signupRequest.getDepartment());
		user.setRole(role);
		user.setPassword(encoder.encode(signupRequest.getPassword()));
		user.setActive(true);
		
		return userService.saveUser(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmployeeId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> role = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		if(!userDetails.getActive()) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND).ok(new MessageResponse("User Blocked! Please contact Admin"));
			return ResponseEntity.badRequest().body(new MessageResponse("User Blocked! Please contact Admin"));
		}
		

		JwtResponse response = new JwtResponse(jwt, userDetails.geteId(), userDetails.getUsername(),
				userDetails.getFirstname(), userDetails.getLastname(),userDetails.getLocation(),userDetails.getJobTitle(),
				userDetails.getDepartment(),role.get(0),userDetails.getActive());

		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers(){
		List<Users> userList = userService.findAllUsers();
		
		return ResponseEntity.ok(userList);
		
	}
	
//	@GetMapping("/getuserbyid/{id}")
//	public ResponseEntity<?> getUserById(@RequestParam Long id){
//		Users user = userService.findByEmployeeId(id);
//		
//		if(user!=null)
//			return ResponseEntity.ok(user);
//		
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		
//	}
	
	@PutMapping("/blockuser/{employeeId}/{block}")
	public ResponseEntity<?> blockOrUnblockUser(@PathVariable Long employeeId, @PathVariable String block){
		Users user = userService.findByEmployeeId(employeeId);
		//Users savedUser = null;
		
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		if (block.equalsIgnoreCase("yes") || block.equalsIgnoreCase("no") ) {
			if(block.equalsIgnoreCase("yes")) {
				user.setActive(false);
				userService.updateUser(user);
			}
			if(block.equalsIgnoreCase("no")) {
				user.setActive(true);
				userService.updateUser(user);
			}
			return ResponseEntity.ok(new MessageResponse("User updated successfully"));
		}
		else {
			return ResponseEntity.ok(new MessageResponse("Not a proper instruction"));
		}
	}
	
}
