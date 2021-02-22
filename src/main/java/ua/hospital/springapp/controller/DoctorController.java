package ua.hospital.springapp.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.dto.DoctorDto;
import ua.hospital.springapp.model.service.DoctorService;
import ua.hospital.springapp.support.Constants;

@Controller
public class DoctorController {
	private static final Logger logger = LogManager.getLogger(DoctorController.class);
	
	@Autowired
	DoctorService doctorService;
	
	@GetMapping("doctor_list")
	public String doctorList(Model model,
			@PageableDefault(size = Constants.PAGE_SIZE) Pageable pageable,
			@RequestParam(required = false) Integer patientId) {
		if(pageable.getSort().isEmpty()) {
			String locale = LocaleContextHolder.getLocale().toString();
			String lastNameEn= new StringBuilder("user.person.lastName")
					.append(Character.toUpperCase(locale.charAt(0)))
					.append(locale.charAt(1))
					.toString();
			
			pageable = PageRequest.of(0, Constants.PAGE_SIZE, Sort.by(lastNameEn));
		}
		
		if (Optional.ofNullable(patientId).filter(p -> p > 0).isPresent()) {
			model.addAttribute("patientId", patientId);
			model.addAttribute("action", "doctor assigning");
			logger.info("List is formed to select a doctor");
		}
		
		Page<DoctorDto> page = doctorService.findDoctorsPaginated(pageable);
		logger.info(page.toList());
		model.addAttribute("page", page);
		return "views/doctorList";
	}
	
	@PostMapping("assign_category")
	public String categoryAssign(Model model,
		@RequestParam int doctorId,
		@RequestParam int categoryId) {
		
		if (doctorService.assignCategory(doctorId, categoryId)) {
			logger.info("Category is successfully assigned to Doctor");
			return new StringBuilder("redirect:/profile?userId=").append(doctorId)
					.append("&userRole=DOCTOR").toString();
		}
		
		logger.error("Some error occured while category assigning to the doctor");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}

}
