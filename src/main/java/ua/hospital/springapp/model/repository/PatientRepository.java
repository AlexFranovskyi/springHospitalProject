package ua.hospital.springapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.hospital.springapp.model.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
