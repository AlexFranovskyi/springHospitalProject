package ua.hospital.springapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.hospital.springapp.model.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
