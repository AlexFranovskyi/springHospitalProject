package ua.hospital.springapp.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.hospital.springapp.model.dto.CategoryDto;
import ua.hospital.springapp.model.entity.Category;
import ua.hospital.springapp.model.repository.CategoryRepository;
import ua.hospital.springapp.support.EntityDtoConverter;

@Service
public class CategoryService {
	private static final Logger logger = LogManager.getLogger(CategoryService.class);
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public boolean createCategory(Category category) {
		if(categoryRepository.save(category).getId() > 0) {
			logger.info("New category is added to database");
			return true;
		}
		logger.info("Could not add category to the database");
		return false;
	}
	
	@Transactional
	public boolean updateCategory(Category category) {
		if(categoryRepository.updateCategoryById(category.getId(), category.getNameEn(), category.getNameUk()) > 0) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean deleteCategory(int categoryId) {
		if(categoryRepository.deleteCategoryById(categoryId) > 0) {
			return true;
		}
		return false;
	}
	
	public List<CategoryDto> findAll(){
		return categoryRepository.findAll().stream()
				.map(EntityDtoConverter::CategoryToDto).collect(Collectors.toList());
	}

}
