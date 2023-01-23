package com.compensationplan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.compensationplan.Application;
import com.compensationplan.entities.ERole;
import com.compensationplan.entities.Role;
import com.compensationplan.entities.Users;
import com.compensationplan.payload.response.MessageResponse;
import com.compensationplan.repository.RoleRepository;
import com.compensationplan.service.UserService;


@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes= {Application.class})
class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private UserService userServiceMock;
	
	@MockBean
	private RoleRepository roleRepo;
	
	@BeforeEach
	public void test() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void saveUser_when_validRequest() throws Exception {
		
		Optional<Role> role = Optional.of(new Role(ERole.ROLE_REPORT_USER,"app user"));
		Mockito.<ResponseEntity<?>>when(userServiceMock.saveUser(any(Users.class))).thenReturn(ResponseEntity.ok(new MessageResponse("User registered successfully!")));
		when(roleRepo.findByName(any(ERole.class))).thenReturn(role);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/compensationplan/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"employeeId\":\"11111\",\"role\":\"report_user\",\"password\":\"test123\"}")
				)
		//.andExpect(Status());
				.andExpect(jsonPath("$.message").value("User registered successfully!"));
	}

	@Test
	public void getAllUsers_when_validRequest() throws Exception{
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",null,"test123",true);
		
		when(userServiceMock.findAllUsers()).thenReturn(List.of(user));
		mockMvc.perform(MockMvcRequestBuilders.get("/compensationplan/getAllUsers")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$[0].employeeId").value("11111"));
	}
	
	@Test
	public void blockOrUnblockUser_when_validRequest() throws Exception{
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",null,"test123",true);
		
		when(userServiceMock.findByEmployeeId(any(Long.class))).thenReturn(user);
		when(userServiceMock.updateUser(any(Users.class))).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.put("/compensationplan/blockuser/11111/no")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.message").value("User updated successfully"));
		
		mockMvc.perform(MockMvcRequestBuilders.put("/compensationplan/blockuser/11111/yes")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.message").value("User updated successfully"));
		mockMvc.perform(MockMvcRequestBuilders.put("/compensationplan/blockuser/11111/hi")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.message").value("Not a proper instruction"));
	}
	
	@Test
	public void blockOrUnblockUser_when_inValidRequest() throws Exception{
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",null,"test123",true);
		
		when(userServiceMock.findByEmployeeId(any(Long.class))).thenReturn(null);
		//when(userServiceMock.updateUser(any(Users.class))).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.put("/compensationplan/blockuser/11111/no")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isNotFound());
	}
	
	
}
