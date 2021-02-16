package ua.hospital.springapp.model.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents Prescription entity, used in app's Model part.
 * Includes enum {@link PrescriptionType}.
 * 
 * @author Alexander-PC
 *
 */

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String descriptionEn;
	
	@NotNull
	private String descriptionUk;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private PrescriptionType type;
	
	
	private LocalDateTime completionTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Patient patient;

	public enum PrescriptionType {
		PROCEDURE, DRUGS, OPERATION
	}
	
	@Override
	public String toString() {
		return new StringBuilder("id: ")
				.append(id)
				.append(", patient id: ")
				.append(patient.getId())
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
	
	@PreRemove
	public void onPreRemove(){
		if (completionTime != null) {
			throw new IllegalStateException();
		}
	}
	
	@PreUpdate
	public void onPreUpdate(){
		if (completionTime != null) {
			throw new IllegalStateException();
		}
	}
	
}
