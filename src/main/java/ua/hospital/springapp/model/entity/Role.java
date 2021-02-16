package ua.hospital.springapp.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
	
	NURSE,
	DOCTOR,
	ADMIN;
	
	@Override
	public String getAuthority() {
		return name();
	}

}
