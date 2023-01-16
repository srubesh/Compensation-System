package com.compensationplan.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.compensationplan.entities.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long eId;

	private Long employeeId;

	private String firstname;
	
	private String lastname;
	
	private String location;
	
	private String jobTitle;
	
	private String department;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;
	
	private Boolean active;

	public UserDetailsImpl(Long id, Long employeeId, String firstname,String lastname,String location,String jobTitle,
			String department,String password, Collection<? extends GrantedAuthority> authorities, Boolean active) {
		this.eId = id;
		this.employeeId = employeeId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.location = location;
		this.jobTitle = jobTitle;
		this.department = department;
		this.password = password;
		this.authorities = authorities;
		this.active = active;
	}

	public static UserDetailsImpl build(Users user) {
//		List<GrantedAuthority> authorities = user.getRole().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
//				.collect(Collectors.toList());
		
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getName().name()));
		

		return new UserDetailsImpl(
				user.geteId(), 
				user.getEmployeeId(), 
				user.getFirstname(),
				user.getLastname(),
				user.getLocation(),
				user.getJobTitle(),
				user.getDepartment(),
				user.getPassword(), 
				authorities,
				user.getActive());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	public Long geteId() {
		return eId;
	}

	public String getUsername() {
		return employeeId.toString();
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public String getLocation() {
		return location;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public String getPassword() {
		return password;
	}
	
	public Boolean getActive() {
		return active;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(eId, user.eId);
	}
}
