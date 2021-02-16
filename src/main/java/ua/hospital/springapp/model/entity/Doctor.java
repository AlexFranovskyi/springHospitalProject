package ua.hospital.springapp.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Formula;

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
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Category category;
	
	@Formula("(SELECT count(patient.id) FROM patient WHERE patient.doctor_id = id AND discharge_date_time IS NULL)")
	private int patientAmount;
	
	public Doctor(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("\nDoctorProfile id: ")
				.append(id)
				.append("; User - ")
				.append(user)
				.append(";\n category - ")
				.append(category)
				.append("; amount of patients: ")
				.append(patientAmount)
				.append(';')
				.toString();
	}

}
