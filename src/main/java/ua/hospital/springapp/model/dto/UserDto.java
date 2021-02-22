package ua.hospital.springapp.model.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.hospital.springapp.model.entity.Role;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private int id;
	private Set<Role> roles;
	private PersonDto personDto;
	private String username;
	
	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}
	
	public boolean isDoctor() {
		return roles.contains(Role.DOCTOR);
	}
	
	public boolean isNurse() {
		return roles.contains(Role.NURSE);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", role:")
				.append(roles)
				.append(", person info: ")
				.append(personDto)
				.append(", login: ")
				.append(username)
				.toString();
	}

}
