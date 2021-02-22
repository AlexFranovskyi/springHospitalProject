package ua.hospital.springapp.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.hospital.springapp.model.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	Optional<Doctor> findByUserUsername(String username);

}
