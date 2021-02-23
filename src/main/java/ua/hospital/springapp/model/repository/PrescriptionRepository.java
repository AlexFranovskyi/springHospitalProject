package ua.hospital.springapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.hospital.springapp.model.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
		
	@Modifying
	@Transactional
	@Query("UPDATE Prescription p set p.completionTime = CURRENT_TIMESTAMP WHERE p.id = :id")
	int completePrescription(int id);
	
	@Transactional
	int deletePrescriptionById(int id);

}
