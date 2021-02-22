package ua.hospital.springapp.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
	
	NURSE("ROLE_NURSE"),
	DOCTOR("ROLE_DOCTOR"),
	ADMIN("ROLE_ADMIN");
	
	private final String name;
	
	Role(String name){
		this.name = name;
	}
	
	@Override
	public String getAuthority() {
		return name;
	}

}
