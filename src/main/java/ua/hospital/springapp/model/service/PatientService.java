package ua.hospital.springapp.model.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.hospital.springapp.model.dto.PatientDto;
import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.repository.PatientRepository;
import ua.hospital.springapp.support.EntityDtoConverter;

@Service
public class PatientService {
	private static final Logger logger = LogManager.getLogger(PatientService.class);
	
	@Autowired
	PatientRepository patientRepository;
	
	public boolean createPatient(Patient patient) {
		if(patientRepository.save(patient).getId() > 0) {
			logger.info("Successful attempt to register patient");
			return true;
		}
		logger.info("Unsuccessful attempt to register patient");
		return false;
	}
	
	public Page<PatientDto> findPatientsPaginated(Pageable pageable){
		return patientRepository.findAll(pageable)
				.map(EntityDtoConverter::PatientToDto);
	}
	
	public Optional<PatientDto> findById(int patientId){
		return patientRepository.findById(patientId)
				.map(EntityDtoConverter::PatientToDto);
	}
	
	public boolean assignDoctor(int patientId, int doctorId) {
		if (patientRepository.assignDoctor(patientId, doctorId) > 0) {
			logger.info("Doctor is assigned to patient");
			return true;
		}
		logger.info("Doctor is not assigned to patient");
		return false;
	}
	
	public Page<PatientDto> findByDoctorIdPaginated(int doctorId, Pageable pageable){
		return patientRepository.findByDoctorIdAndDischargeDateTimeIsNull(doctorId, pageable)
				.map(EntityDtoConverter::PatientToDto);
	}
	
	public boolean defineDiagnosis(int patientId, String diagnosisEn, String diagnosisUk) {
		if (patientRepository.defineDiagnosis(patientId, diagnosisEn, diagnosisUk) > 0) {
			logger.info("Diagnosis info is sent to the database");
			return true;
		}
		logger.info("Diagnosis info is not sent to the database");
		return false;
	}
	
	public boolean assignMeal(int patientId, int mealId) {
		if (patientRepository.assignMeal(patientId, mealId) > 0) {
			logger.info("Meal is assigned to patient");
			return true;
		}
		logger.info("Meal is not assigned to patient");
		return false;
	}
	
	public boolean removeMeal(int patientId, int mealId) {
		if (patientRepository.removeMeal(patientId, mealId) > 0) {
			logger.info("Meal is removed from patient");
			return true;
		}
		logger.info("Meal is not removed from patient");
		return false;		
	}
	
	public boolean dischargePatient(int patientId) {
		if (patientRepository.dischargePatient(patientId) > 0) {
			logger.info("Patient is discharged");
			return true;
		}
		logger.info("Patient is not discharged");
		return false;
	}
}
