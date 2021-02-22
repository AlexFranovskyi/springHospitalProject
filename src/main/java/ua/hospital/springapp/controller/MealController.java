package ua.hospital.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.dto.MealDto;
import ua.hospital.springapp.model.service.MealService;

@Controller
public class MealController {
	private static final Logger logger = LogManager.getLogger(MealController.class);
	
	@Autowired
	MealService mealService;
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN') || hasAuthority('ROLE_DOCTOR')")
	@GetMapping("meal_list")
	public String mealList(Model model,
			@RequestParam(required = false) Integer patientId) {
		
		if(Optional.ofNullable(patientId).isPresent()) {
			model.addAttribute("patientId", patientId);
			model.addAttribute("message", "mealAssigning");
			logger.info("List is formed to assign a meal to the patient");
		}
		
		List<MealDto> list = mealService.findAll();
		model.addAttribute("mealList", list);
		logger.info("List of meals is send");
		return "views/mealList";
	}

}
