package ua.hospital.springapp.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents Patient entity used in app's Model part.
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
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Person person;	
	
	@Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime arriveTime;
	
	@NotNull
	private String diagnosisEn;
	
	@NotNull
	private String diagnosisUk;
	
	private LocalDateTime dischargeDateTime;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Doctor doctor;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
	private List<Prescription> prescriptions;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "patient_has_meal")
	private Set<Meal> meals;
	
	@Override
	public String toString() {
		return new StringBuilder("\nid: ")
				.append(id)
				.append(", person info: ")
				.append(person)
				.append("; arriving time: ")
				.append(arriveTime)
				.append("; diagnosis (en): ")
				.append(diagnosisEn)
				.append("; diagnosis (uk): ")
				.append(diagnosisUk)
				.append("; dischargeTime: ")
				.append(dischargeDateTime)
				.append(";\n doctor info: ")
				.append(doctor)
				.append("\n prescriptions: ")
				.append(prescriptions)
				.append(";\n meals: ")
				.append(meals)
				.toString();
	}

	public void addPrescription(Prescription prescription) {
		this.getPrescriptions().add(prescription);
		prescription.setPatient(this);
	}
	
	@PreUpdate
	public void onPreUpdate() {
		if(dischargeDateTime != null) {
			throw new IllegalStateException();
		}
	}
	
}
