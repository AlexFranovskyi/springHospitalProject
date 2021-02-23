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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.dto.MealDto;
import ua.hospital.springapp.model.entity.Meal;
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
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("create_meal")
	public String mealCreate(Model model,
			@RequestParam String nameEn,
			@RequestParam String nameUk) {
		
		if (mealService.createMeal(new Meal(nameEn, nameUk))) {
			logger.info("new meal is created successfully");
			return "redirect:/meal_list";
		}
		
		logger.info("couldn't complete creation of the new meal");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("delete_meal")
	public String mealCreate(Model model,
			@RequestParam int mealId) {
		if(mealService.deleteMeal(mealId)) {
			logger.info("The meal is successfully deleted");
			return "redirect:/meal_list";
		}
		
		logger.error("Some error occured while meal deleting");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("update_meal")
	public String mealCreate(Model model,
			@RequestParam int mealId,
			@RequestParam String nameEn,
			@RequestParam String nameUk) {
		if(mealService.updateMeal(new Meal(mealId, nameEn, nameUk))) {
			logger.info("The meal is successfully updated");
			return "redirect:/meal_list";
		}
		
		logger.error("Some error occured while meal updating");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}

}
