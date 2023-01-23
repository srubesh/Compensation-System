package com.compensationplan.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.compensationplan.entities.CompensationPlan;
import com.compensationplan.entities.ERole;
import com.compensationplan.entities.Role;
import com.compensationplan.entities.Users;
import com.compensationplan.payload.response.MessageResponse;
import com.compensationplan.repository.RoleRepository;
import com.compensationplan.service.CompensationPlanService;
import com.compensationplan.service.UserService;

@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes= {Application.class})
class CompensationPlanControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private UserService userServiceMock;
	
	@MockBean
	private CompensationPlanService compensationPlanServiceMock;
	
	@MockBean
	private RoleRepository roleRepo;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void saveCompensationPlan_when_validRequest() throws Exception {
		Role role=new Role(ERole.ROLE_PLAN_USER,"Role User");
		role.setRoleId("U001");
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",role,"test123",true);
		
		when(userServiceMock.existByEmployeeId(any())).thenReturn(true);
		when(userServiceMock.findByEmployeeId(any())).thenReturn(user);
		Mockito.<ResponseEntity<?>>when(compensationPlanServiceMock.saveCompensationPlan(any())).thenReturn(ResponseEntity.ok(new MessageResponse("Plan created successfully!")));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/compensationplan/createplan/11111")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"partnerName\":\"partnerName\",\"compensationPlan\":\"plan\",\"calculationMethod\":\"volume\"}")
				)
		//.andExpect(Status());
				.andExpect(jsonPath("$.message").value("Plan created successfully!"));
	}
	
	@Test
	public void saveCompensationPlan_when_userNotExist() throws Exception {
		Role role=new Role(ERole.ROLE_PLAN_USER,"Role User");
		role.setRoleId("U001");
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",role,"test123",true);
		
		when(userServiceMock.existByEmployeeId(any())).thenReturn(false);
		when(userServiceMock.findByEmployeeId(any())).thenReturn(user);
		Mockito.<ResponseEntity<?>>when(compensationPlanServiceMock.saveCompensationPlan(any())).thenReturn(ResponseEntity.ok(new MessageResponse("Plan created successfully!")));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/compensationplan/createplan/11111")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"partnerName\":\"partnerName\",\"compensationPlan\":\"plan\",\"calculationMethod\":\"volume\"}")
				)
		//.andExpect(Status());
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void saveCompensationPlan_when_userNotCreater() throws Exception {
		Role role=new Role(ERole.ROLE_PLAN_USER,"Role User");
		role.setRoleId("U002");
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",role,"test123",true);
		
		when(userServiceMock.existByEmployeeId(any())).thenReturn(true);
		when(userServiceMock.findByEmployeeId(any())).thenReturn(user);
		Mockito.<ResponseEntity<?>>when(compensationPlanServiceMock.saveCompensationPlan(any())).thenReturn(ResponseEntity.ok(new MessageResponse("Plan created successfully!")));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/compensationplan/createplan/11111")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"partnerName\":\"partnerName\",\"compensationPlan\":\"plan\",\"calculationMethod\":\"volume\"}")
				)
		//.andExpect(Status());
				.andExpect(jsonPath("$.message").value("This user is not a plan creator"));
	}
	
	@Test
	public void getAllCompensationPlan_when_validRequest() throws Exception {
		CompensationPlan plan = new CompensationPlan();
		plan.setPartnerName("name");
		plan.setCompensationPlan("General");
		plan.setCalculationMethod("valume");
		
		when(compensationPlanServiceMock.getAllCompensationPlan()).thenReturn(List.of(plan));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/compensationplan/getallplans")
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$[0].partnerName").value("name"));
	}
	
	@Test
	public void getMyCompensationPlan_when_validRequest() throws Exception {
		Role role=new Role(ERole.ROLE_PLAN_USER,"Role User");
		role.setRoleId("U001");
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",role,"test123",true);
		
		CompensationPlan plan = new CompensationPlan();
		plan.setPartnerName("name");
		plan.setCompensationPlan("General");
		plan.setCalculationMethod("valume");
		
		when(userServiceMock.existByEmployeeId(any())).thenReturn(true);
		when(userServiceMock.findByEmployeeId(any())).thenReturn(user);
		when(compensationPlanServiceMock.getMyCompensationPlan(any())).thenReturn(List.of(plan));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/compensationplan/getmyplans/11111")
				.accept(MediaType.APPLICATION_JSON)
				//.content("{\"partnerName\":\"partnerName\",\"compensationPlan\":\"plan\",\"calculationMethod\":\"volume\"}")
				)
		//.andExpect(Status());
				.andExpect(jsonPath("$[0].partnerName").value("name"));
		
	}
	
	@Test
	public void getMyCompensationPlan_when_userNotExist() throws Exception {
		Role role=new Role(ERole.ROLE_PLAN_USER,"Role User");
		role.setRoleId("U001");
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",role,"test123",true);
		
		CompensationPlan plan = new CompensationPlan();
		plan.setPartnerName("name");
		plan.setCompensationPlan("General");
		plan.setCalculationMethod("valume");
		
		when(userServiceMock.existByEmployeeId(any())).thenReturn(false);
		when(userServiceMock.findByEmployeeId(any())).thenReturn(user);
		when(compensationPlanServiceMock.getMyCompensationPlan(any())).thenReturn(List.of(plan));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/compensationplan/getmyplans/11111")
				.accept(MediaType.APPLICATION_JSON)
				//.content("{\"partnerName\":\"partnerName\",\"compensationPlan\":\"plan\",\"calculationMethod\":\"volume\"}")
				)
		//.andExpect(Status());
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getMyCompensationPlan_when_userNotCreater() throws Exception {
		Role role=new Role(ERole.ROLE_PLAN_USER,"Role User");
		role.setRoleId("U002");
		Users user = new Users(11111L,"user","1","chennai","associate","analysis",role,"test123",true);
		
		CompensationPlan plan = new CompensationPlan();
		plan.setPartnerName("name");
		plan.setCompensationPlan("General");
		plan.setCalculationMethod("valume");
		
		when(userServiceMock.existByEmployeeId(any())).thenReturn(true);
		when(userServiceMock.findByEmployeeId(any())).thenReturn(user);
		when(compensationPlanServiceMock.getMyCompensationPlan(any())).thenReturn(List.of(plan));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/compensationplan/getmyplans/11111")
				.accept(MediaType.APPLICATION_JSON)
				//.content("{\"partnerName\":\"partnerName\",\"compensationPlan\":\"plan\",\"calculationMethod\":\"volume\"}")
				)
		//.andExpect(Status());
				.andExpect(jsonPath("$.message").value("This user is not a plan creator"));
	}


}
