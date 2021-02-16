package ua.hospital.springapp.model.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.repository.PatientRepository;

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
}
