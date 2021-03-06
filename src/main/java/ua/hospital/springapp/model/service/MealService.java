package ua.hospital.springapp.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.hospital.springapp.model.dto.MealDto;
import ua.hospital.springapp.model.entity.Meal;
import ua.hospital.springapp.model.repository.MealRepository;
import ua.hospital.springapp.support.EntityDtoConverter;

@Service
public class MealService {
	private static final Logger logger = LogManager.getLogger(MealService.class);
	
	@Autowired
	MealRepository mealRepository;
	
	public boolean createMeal(Meal meal) {
		if(mealRepository.save(meal).getId() > 0) {
			logger.info("New meal is added to database");
			return true;
		}
		logger.info("Could not add meal to database");
		return false;
	}
	
	public boolean updateMeal(Meal meal) {
		if(mealRepository.updateMealById(meal.getId(), meal.getNameEn(), meal.getNameUk()) > 0) {
			logger.info("The meal is updated");
			return true;
		}
		logger.info("The meal is not updated");
		return false;
	}
	
	public boolean deleteMeal(int mealId) {
		if(mealRepository.deleteMealById(mealId) > 0) {
			logger.info("The meal is deleted");
			return true;
		}
		logger.info("The meal is not deleted");
		return false;
	}
	
	public List<MealDto> findAll(){
		return mealRepository.findAll().stream()
				.map(EntityDtoConverter::MealToDto).collect(Collectors.toList());
	}

}
