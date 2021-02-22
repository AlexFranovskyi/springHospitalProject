package ua.hospital.springapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
	
	private int id;
	private UserDto userDto;
	private CategoryDto categoryDto;
	private int patientAmount;
	
	@Override
	public String toString() {
		return new StringBuilder("\nDoctorProfile id: ")
				.append(id)
				.append("; User - ")
				.append(userDto)
				.append(";\n category - ")
				.append(categoryDto)
				.append("; amount of patients: ")
				.append(patientAmount)
				.append(';')
				.toString();
	}

}
