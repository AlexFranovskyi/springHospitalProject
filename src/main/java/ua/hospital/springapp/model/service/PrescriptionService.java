package ua.hospital.springapp.model.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.entity.Prescription;
import ua.hospital.springapp.model.repository.PatientRepository;
import ua.hospital.springapp.model.repository.PrescriptionRepository;

@Service
public class PrescriptionService {
	private static final Logger logger = LogManager.getLogger(PrescriptionService.class);
	
	@Autowired
	PrescriptionRepository prescriptionRepository;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Transactional
	public boolean createPrescription(Prescription prescription, int patientId) {
		Optional<Patient> optional = patientRepository.findById(patientId);
		if(!optional.isPresent()) {
			return false;
		}
		prescription.setPatient(optional.get());
		if(prescriptionRepository.save(prescription).getId() > 0) {
			return true;
		}
		return false;
	}
	
	public boolean completePrescription(int prescriptionId) {
		if(prescriptionRepository.completePrescription(prescriptionId) > 0) {
			logger.info("The prescription is completed");
			return true;
		}
		logger.info("The prescription is not completed");
		return false;
	}
	
	public boolean deletePrescription(int prescriptionId) {
		if(prescriptionRepository.deletePrescriptionById(prescriptionId) > 0) {
			logger.info("The prescription is deleted");
			return true;
		}
		logger.info("The prescription is not deleted");
		return false;
	}

}
