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
import ua.hospital.springapp.model.dto.CategoryDto;
import ua.hospital.springapp.model.entity.Category;
import ua.hospital.springapp.model.service.CategoryService;
import ua.hospital.springapp.support.ValidationHelper;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Controller
public class CategoryController {
	private static final Logger logger = LogManager.getLogger(CategoryController.class);
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("category_list")
	public String categoryList(Model model,
			@RequestParam(required = false) Integer doctorId) {
		
		if(Optional.ofNullable(doctorId).isPresent()) {
			model.addAttribute("doctorId", doctorId);
			model.addAttribute("message", "categoryAssigning");
			logger.info("List is formed to assign a category to the doctor");
		}
		
		List<CategoryDto> categories = categoryService.findAll();
		model.addAttribute("categoryList", categories);
		logger.info("List of categories is send");
		return "views/categoryList";
	}
	
	@PostMapping("create_category")
	public String categoryCreate(Model model,
			@RequestParam String nameEn,
			@RequestParam String nameUk) {
		if (!ValidationHelper.isLatinName(nameEn) || !ValidationHelper.isCyrillicName(nameUk)) {
			logger.info("Invalid category registration information");
			model.addAttribute("message", "invalidData");
			return "error/errorMessage";
		}
		
		if (categoryService.createCategory(new Category(nameEn, nameUk))) {
			logger.info("new category is created successfully");
			return "redirect:/category_list";
		}
		
		logger.info("couldn't complete creation of the new category");
		model.addAttribute("message", "errorOccured");
		return "error/errorMessage";
	}
	
	@PostMapping("category_update")
	public String categoryUpdate(Model model,
			@RequestParam int categoryId,
			@RequestParam String nameEn,
			@RequestParam String nameUk) {
		
		if (categoryId <= 0 || !ValidationHelper.isLatinName(nameEn) || !ValidationHelper.isCyrillicName(nameUk)) {
			logger.info("Invalid category registration information");
			model.addAttribute("message", "invalidData");
			return "error/errorMessage";
		}
		
		if(categoryService.updateCategory(new Category(categoryId, nameEn, nameUk))) {
			logger.info("Category is successfully updated");
			return "redirect:/category_list";
		}
		
		logger.error("Some error occured while category updating");
		model.addAttribute("message", "errorOccured");
		return "/error/errorMessage";
	}
	
	@PostMapping("category_delete")
	public String categoryDelete(Model model,
			@RequestParam int categoryId) {
		if(categoryService.deleteCategory(categoryId)) {
			logger.info("Category is successfully deleted");
			return "redirect:/category_list";
		}		
		
		logger.error("Some error occured while category deleting");
		model.addAttribute("message", "unableComplete");
		return "error/errorMessage";
	}
	
	
}
