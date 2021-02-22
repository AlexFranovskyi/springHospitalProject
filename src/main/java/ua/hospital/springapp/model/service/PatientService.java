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
		try {
			patientRepository.save(patient);
		} catch(IllegalArgumentException ex) {
			logger.info("Unsuccessful attempt to register patient");
			return false;
		}
		return true;
	}
	
	public Page<PatientDto> findPatientsPaginated(Pageable pageable){
		return patientRepository.findAll(pageable)
				.map(EntityDtoConverter::PatientToDto);
	}
	
	public Optional<PatientDto> findById(int patientId){
		return patientRepository.findById(patientId)
				.map(EntityDtoConverter::PatientToDto);
	}
}
