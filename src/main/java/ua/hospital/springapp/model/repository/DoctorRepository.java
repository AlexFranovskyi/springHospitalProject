package ua.hospital.springapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.hospital.springapp.model.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
