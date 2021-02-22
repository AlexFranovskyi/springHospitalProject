package ua.hospital.springapp.model.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.hospital.springapp.model.dto.DoctorDto;
import ua.hospital.springapp.model.entity.Doctor;
import ua.hospital.springapp.model.repository.DoctorRepository;
import ua.hospital.springapp.support.EntityDtoConverter;

@Service
public class DoctorService {
	private final static Logger logger = LogManager.getLogger(DoctorService.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	public boolean createDoctor(Doctor doctor) {
		doctor.getUser().setPassword(passwordEncoder.encode(doctor.getUser().getPassword()));
		try {
			doctorRepository.save(doctor);
		} catch(IllegalArgumentException ex) {
			logger.info("attempt to register doctor under existing user login");
			return false;
		}
		return true;
	}
	
	public Optional<DoctorDto> findByUserUsername(String username){
		return doctorRepository.findByUserUsername(username)
				.map(EntityDtoConverter::DoctorToDto);	
	}
	
	public Page<DoctorDto> findDoctorsPaginated(Pageable pageable){
		return doctorRepository.findAll(pageable)
				.map(EntityDtoConverter::DoctorToDto);
	}
	
	public Optional<DoctorDto> findById(int doctorId){
		return doctorRepository.findById(doctorId).map(EntityDtoConverter::DoctorToDto);
	}
	
	public boolean assignCategory(int doctorId, int categoryId) {
		
	}

}
