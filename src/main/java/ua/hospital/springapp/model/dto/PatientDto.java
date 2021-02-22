package ua.hospital.springapp.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
public class PatientDto {
	private int id;
	private PersonDto personDto;
	private LocalDateTime arriveDateTime;
	
	private String diagnosisEn;
	private String diagnosisUk;
	private LocalDateTime dischargeDateTime;
	
	private DoctorDto doctorDto;
	private List<PrescriptionDto> prescriptions;
	private Set<MealDto> meals;
	
	@Override
	public String toString() {
		return new StringBuilder("\nid: ")
				.append(id)
				.append(", person info: ")
				.append(personDto)
				.append("; arriving time: ")
				.append(arriveDateTime)
				.append("; diagnosis (en): ")
				.append(diagnosisEn)
				.append("; diagnosis (uk): ")
				.append(diagnosisUk)
				.append("; dischargeTime: ")
				.append(dischargeDateTime)
				.append(";\n doctor info: ")
				.append(doctorDto)
				.append("\n prescriptions: ")
				.append(prescriptions)
				.append(";\n meals: ")
				.append(meals)
				.toString();
	}

}
