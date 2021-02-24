package ua.hospital.springapp.model.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		if(doctorRepository.save(doctor).getId() > 0) {
			logger.info("New doctor is saved");
			return true;
		}
		logger.info("New doctor is not saved");
		return false;
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
	
	@Transactional
	public boolean assignCategory(int doctorId, int categoryId) {
		if (doctorRepository.assignCategory(doctorId, categoryId) > 0) {
			return true;
		}
		return false;
	}

}
