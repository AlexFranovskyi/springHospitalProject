package ua.hospital.springapp.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ua.hospital.springapp.model.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	Optional<Doctor> findByUserUsername(String username);
	
	@Modifying
	@Query("UPDATE Doctor d set d.category.id = :categoryId WHERE d.id = :doctorId")
	int assignCategory(int doctorId, int categoryId);

}
