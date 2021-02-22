package ua.hospital.springapp.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
	
	private int id;
	private String firstNameEn;
	private String firstNameUk;
	private String lastNameEn;
	private String lastNameUk;
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
