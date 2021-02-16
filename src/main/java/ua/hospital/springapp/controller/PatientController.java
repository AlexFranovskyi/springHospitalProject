package ua.hospital.springapp.controller;

import java.time.LocalDate;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.entity.Person;
import ua.hospital.springapp.model.service.PatientService;
import ua.hospital.springapp.support.Constants;
import ua.hospital.springapp.support.ValidationHelper;

@Controller
public class PatientController {
	private final static Logger logger = LogManager.getLogger(PatientController.class);
	
	@Autowired
	PatientService patientService;
	
	@Secured("ADMIN")
	@GetMapping("patient_registration_form")
	public String patientRegistrationForm(){
		return "views/patientRegistrationForm";
	}
	
	@Secured("ADMIN")
	@PostMapping("register_patient")
	public String registerPatient(Model model,
			@RequestParam("firstNameEn") String firstNameEn,
			@RequestParam("firstNameUk") String firstNameUk,
			@RequestParam("lastNameEn") String lastNameEn,
			@RequestParam("lastNameUk") String lastNameUk,
			
			@RequestParam("birthDate") 
				@DateTimeFormat(pattern = Constants.LOCAL_DATE_PATTERN) LocalDate birthDate) {
		
		if (!ValidationHelper.isLatinName(firstNameEn) || !ValidationHelper.isCyrillicName(firstNameUk) ||
				!ValidationHelper.isLatinName(lastNameEn) || !ValidationHelper.isCyrillicName(lastNameUk)) {
			
			logger.info("invalid patient registration information");
			model.addAttribute("message", "invalidData");
			return "views/patientRegistrationForm";
		}
		
		Person person = Person.builder()
				.firstNameEn(firstNameEn)
				.firstNameUk(firstNameUk)
				.lastNameEn(lastNameEn)
				.lastNameUk(lastNameUk)
				.birthDate(birthDate)
				.build();
		
		Patient patient = Patient.builder()
				.person(person)
				.build();
		
		if (patientService.createPatient(patient)) {
			logger.info("New patient is registered successfully");
			return "redirect:";
		}
		
		logger.info("Couldn't register new patient");
		model.addAttribute("message", "patientRegistrationFailed");
		return "views/patientRegistrationForm";
	}

}
