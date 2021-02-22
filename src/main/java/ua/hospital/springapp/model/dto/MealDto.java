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
public class MealDto {
	private int id;
	private String nameEn;
	private String nameUk;
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", nameEn: ")
				.append(nameEn)
				.append(", nameUk: ")
				.append(nameUk)
				.toString();
	}

}
