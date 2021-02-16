package ua.hospital.springapp.model.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String firstNameEn;
	
	@NotNull
	private String firstNameUk;
	
	@NotNull
	private String lastNameEn;
	
	@NotNull
	private String lastNameUk;
	
	@NotNull
	private LocalDate birthDate;
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", nameEn: ")
				.append(firstNameEn)
				.append(' ')
				.append(lastNameEn)
				.append(", nameUk: ")
				.append(firstNameUk)
				.append(' ')
				.append(lastNameUk)
				.append("; date of birth: ")
				.append(birthDate)
				.toString();		
	}

}
