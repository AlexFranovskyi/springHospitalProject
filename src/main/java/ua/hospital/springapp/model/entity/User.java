package ua.hospital.springapp.model.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public boolean isAdmin() {
		return role == Role.ADMIN;
	}
	
	public boolean isDoctor() {
		return role == Role.DOCTOR;
	}
	
	public boolean isNurse() {
		return role == Role.NURSE;
	}
	
	@NotNull
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Person person;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@NotNull
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>(java.util.Arrays.asList(role));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", person info: ")
				.append(person)
				.append(", login: ")
				.append(username)
				.append(", password: ")
				.append(password)
				.toString();
	}

}
