package ua.hospital.springapp.model.service;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import ua.hospital.springapp.model.entity.Category;
import ua.hospital.springapp.model.repository.CategoryRepository;

@SpringBootTest
class CategoryServiceTest {
	
	@InjectMocks
	CategoryService categoryService;
	
	@Mock
	CategoryRepository categoryRepository;

	@Test
	void testCreateCategory() {
		Category category = new Category("Test", "Тест");

		Mockito.when(categoryRepository.save(category))
		.thenReturn(new Category(1, "Test", "Тест"));
		Assert.assertEquals(true, categoryService.createCategory(category));
	}

	@Test
	void testUpdateCategory() {
		Category category = new Category(1, "Test", "Тест");

		Mockito.when(categoryRepository.updateCategoryById(1, "Test", "Тест"))
		.thenReturn(1);
		Assert.assertEquals(true, categoryService.updateCategory(category));
	}

	@Test
	void testDeleteCategory() {
		Mockito.when(categoryRepository.deleteCategoryById(1))
		.thenReturn(1);
		Assert.assertEquals(true, categoryService.deleteCategory(1));
	}

	@Test
	void testFindAll() {
		Category category = new Category(1, "Test", "Тест");
		List<Category> list = Collections.singletonList(category);
		
		Mockito.when(categoryRepository.findAll())
		.thenReturn(list);
		Assert.assertEquals(true, categoryService.findAll().size() == 1);
	}

}
