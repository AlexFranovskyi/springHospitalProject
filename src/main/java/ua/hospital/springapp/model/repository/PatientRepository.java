package ua.hospital.springapp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.hospital.springapp.model.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	@Modifying
	@Transactional
	@Query("UPDATE Patient p set p.doctor.id = :doctorId WHERE p.id = :patientId")
	int assignDoctor(int patientId, int doctorId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Patient p set p.diagnosisEn = :diagnosisEn, p.diagnosisUk = :diagnosisUk WHERE p.id = :patientId")
	int defineDiagnosis(int patientId, String diagnosisEn, String diagnosisUk);
	
	Page<Patient> findByDoctorIdAndDischargeDateTimeIsNull(int doctorId, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO patient_has_meal (patient_id, meals_id) VALUES (:patientId, :mealId)", nativeQuery = true)
	int assignMeal(int patientId, int mealId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM patient_has_meal phm WHERE phm.patient_id=:patientId AND phm.meals_id=:mealId", nativeQuery = true)
	int removeMeal(int patientId, int mealId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Patient p set p.dischargeDateTime=CURRENT_TIMESTAMP WHERE p.id = :patientId")
	int dischargePatient(int patientId);
}
