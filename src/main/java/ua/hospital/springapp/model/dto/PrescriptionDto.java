package ua.hospital.springapp.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.hospital.springapp.model.entity.Prescription.PrescriptionType;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {
	private int id;
	private String descriptionEn;
	private String descriptionUk;
	private PrescriptionType type;
	private LocalDateTime completionTime;
	private int patientId;
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", patient id: ")
				.append(patientId)
				.append(", descriptionEn: ")
				.append(descriptionEn)
				.append(", descriptionUk: ")
				.append(descriptionUk)
				.append(", type: ")
				.append(type)
				.append(", completion time: ")
				.append(completionTime)
				.toString();
	}
	
}
