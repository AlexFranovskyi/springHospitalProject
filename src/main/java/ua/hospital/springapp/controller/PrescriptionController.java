package ua.hospital.springapp.controller;

import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.entity.Prescription;
import ua.hospital.springapp.model.entity.Prescription.PrescriptionType;
import ua.hospital.springapp.model.entity.Role;
import ua.hospital.springapp.model.service.PrescriptionService;

@Controller
public class PrescriptionController {
	private static final Logger logger = LogManager.getLogger(PrescriptionController.class);
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	@PostMapping("create_prescription")
	public String prescriptionCreate(Model model,
			@RequestParam String descriptionEn,
			@RequestParam String descriptionUk,
			@RequestParam PrescriptionType prescriptionType,
			@RequestParam int patientId) {
		
		Prescription prescription = Prescription.builder()
				.descriptionEn(descriptionEn)
				.descriptionUk(descriptionUk)
				.type(prescriptionType)
				.build();
		
		if (prescriptionService.createPrescription(prescription, patientId)) {
			logger.info("New prescription is successfully created");
			return "redirect:/patient_get?patientId=" + patientId;
		}
		
		logger.info("Could not create new prescription");
		model.addAttribute("message", "invalidData");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR') || hasAuthority('ROLE_NURSE')")
	@PostMapping("complete_prescription")
	public String prescriptionComplete(Model model,
			@RequestParam int patientId,
			@RequestParam int prescriptionId,
			@RequestParam PrescriptionType prescriptionType) {
		
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		if (prescriptionType == PrescriptionType.OPERATION && !roles.contains(Role.DOCTOR)) {
			logger.error("Attempt to complete the prescription by inappropriate user");
			model.addAttribute("message", "invalidData");
			return "error/errorMessage";
		}
		
		if (prescriptionService.completePrescription(prescriptionId)) {
			logger.info("The prescription is completed successfully");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}
		
		logger.error("Some error occured while completing the prescription");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	@PostMapping("delete_prescription")
	public String prescriptionDelete(Model model,
			@RequestParam int patientId,
			@RequestParam int prescriptionId) {
		if (prescriptionService.deletePrescription(prescriptionId)) {
			logger.info("The prescription is deleted successfully");
			return new StringBuilder("redirect:/patient_get?patientId=")
					.append(patientId).toString();
		}		
		
		logger.error("Some error occured while deleting the prescription");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}

}
