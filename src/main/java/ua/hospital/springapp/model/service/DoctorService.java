package ua.hospital.springapp.model.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.hospital.springapp.model.entity.Doctor;
import ua.hospital.springapp.model.repository.DoctorRepository;

@Service
public class DoctorService {
	private final static Logger logger = LogManager.getLogger(DoctorService.class);
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	public boolean createDoctor(Doctor doctor) {
		//doctor.getUser().setPassword(passwordEncoder.encode(doctor.getUser().getPassword()));
		try {
			doctorRepository.save(doctor);
		} catch(IllegalArgumentException ex) {
			logger.info("attempt to register doctor under existing user login");
			return false;
		}
		return true;
	}

}
