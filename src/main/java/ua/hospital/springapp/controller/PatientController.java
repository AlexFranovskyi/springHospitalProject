package ua.hospital.springapp.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.dto.PatientDto;
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
	
	@GetMapping("patients_list")
	public String patientList(Model model,
			@PageableDefault(size = Constants.PAGE_SIZE) Pageable pageable) {
		if(pageable.getSort().isEmpty()) {
			String locale = LocaleContextHolder.getLocale().toString();
			String lastNameEn= new StringBuilder("person.lastName")
					.append(Character.toUpperCase(locale.charAt(0)))
					.append(locale.charAt(1))
					.toString();
			
			pageable = PageRequest.of(0, Constants.PAGE_SIZE, Sort.by(lastNameEn));
		}
		
		Page<PatientDto> page = patientService.findPatientsPaginated(pageable);
		logger.info(page.toList());
		model.addAttribute("page", page);
		return "views/patientList";
	}
	
	@GetMapping("patient_get")
	public String patientGet(Model model,
			@RequestParam int patientId) {
		Optional<PatientDto> optional = patientService.findById(patientId);
		
		if(optional.isPresent()) {
			logger.info("Patient is found and sent");
			model.addAttribute("patient", optional.get());
			return "views/medicalCard";
		}
		
		logger.error("Some error occured while patient entity searching");
		model.addAttribute("message", "dataMissing");
		return "error/errorMessage";
	}

}
