package com.compensationplan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="TBL_USER",
		uniqueConstraints = { 
		@UniqueConstraint(columnNames = "employeeId")
	})
public class Users {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(columnDefinition = "BIGINT(20) NOT NULL UNIQUE KEY auto_increment")
	private Long eId;
	
	@Id
	private Long employeeId;
	
	private String firstname;
	
	private String lastname;
	
	private String location;
	
	private String jobTitle;
	
	private String department;
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(	name = "user_roles", 
//				joinColumns = @JoinColumn(name = "user_id"), 
//				inverseJoinColumns = @JoinColumn(name = "role_name"))
//	private Set<Role> role = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	private String password;
	

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Long employeeId, String firstname, String lastname, String location, String jobTitle,
			String department, Role role, String password) {
		super();
		this.employeeId = employeeId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.location = location;
		this.jobTitle = jobTitle;
		this.department = department;
		this.role = role;
		this.password = password;
	}

	public Users(Long eId, Long employeeId, String firstname, String lastname, String location, String jobTitle,
			String department, Role role, String password) {
		this(employeeId,firstname,lastname,location,jobTitle, department, role, password);
		this.eId = eId;
	}
	
	

	public Long geteId() {
		return eId;
	}

	public void seteId(Long eId) {
		this.eId = eId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

//	public Set<Role> getRole() {
//		return role;
//	}
//
//	public void setRole(Set<Role> role) {
//		this.role = role;
//	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	@Override
	public String toString() {
		return "Users [eId=" + eId + ", employeeId=" + employeeId + ", firstname=" + firstname + ", lastname="
				+ lastname + ", location=" + location + ", jobTitle=" + jobTitle + ", department=" + department
				+ ", role=" + role + ", password=" + password + "]";
	}
	

}
