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
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("patient_registration_form")
	public String patientRegistrationForm(){
		return "views/patientRegistrationForm";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
			logger.info(optional.get());
			model.addAttribute("patient", optional.get());
			return "views/medicalCard";
		}
		
		logger.error("Some error occured while patient entity searching");
		model.addAttribute("message", "dataMissing");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("assign_doctor")
	public String doctorAssign(Model model,
			@RequestParam int doctorId,
			@RequestParam int patientId) {
		
		if (patientService.assignDoctor(patientId, doctorId)) {
			logger.info("The doctor is assigned to the patient");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while doctor assigning to patient");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@GetMapping("doctor_patients_list")
	public String DoctorPatientsList(Model model,
			@RequestParam int doctorId,
			@PageableDefault(size = Constants.PAGE_SIZE) Pageable pageable) {
		if(pageable.getSort().isEmpty()) {
			String locale = LocaleContextHolder.getLocale().toString();
			String lastNameEn= new StringBuilder("person.lastName")
					.append(Character.toUpperCase(locale.charAt(0)))
					.append(locale.charAt(1))
					.toString();
			
			pageable = PageRequest.of(0, Constants.PAGE_SIZE, Sort.by(lastNameEn));
		}
		
		Page<PatientDto> page = patientService.findByDoctorIdPaginated(doctorId, pageable);
		logger.info(page.toList());
		model.addAttribute("message", "doctorPatientsList");
		model.addAttribute("doctorId", doctorId);
		model.addAttribute("page", page);
		return "views/patientList";
	}
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	@PostMapping("define_diagnosis")
	public String patientDiagnosis(Model model,
			@RequestParam int patientId,
			@RequestParam String diagnosisEn,
			@RequestParam String diagnosisUk) {
		
		if (patientService.defineDiagnosis(patientId, diagnosisEn, diagnosisUk)) {
			logger.info("The diagnosis is successfully defined to the Patient");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while diagnosis defining to a patient");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	@PostMapping("assign_patient_meal")
	public String patientMealAssign(Model model,
			@RequestParam int patientId,
			@RequestParam int mealId){
		if (patientService.assignMeal(patientId, mealId)) {
			logger.info("The meal is assigned successfully to the patient");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}		
		
		logger.error("Some error occured while meal assigning to the patient");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	@PostMapping("remove_patient_meal")
	public String patientMealRemove(Model model,
			@RequestParam int patientId,
			@RequestParam int mealId){
		if (patientService.removeMeal(patientId, mealId)) {
			logger.info("The meal is removed successfully from the patient");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}		
		
		logger.error("Some error occured while meal removing from the patient");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	@PostMapping("discharge_patient")
	public String patientDischarge(Model model,
			@RequestParam int patientId) {
		if (patientService.dischargePatient(patientId)) {
			logger.info("The patient is discharged successfully");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while discharging the patient");
		model.addAttribute("message", "errorOccured");
		return "WEB-INF/errors/errorMessage.jsp";
	}

}
