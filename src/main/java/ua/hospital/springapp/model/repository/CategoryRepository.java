package ua.hospital.springapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ua.hospital.springapp.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	int deleteCategoryById(int id);
	
	@Modifying
	@Query("UPDATE Category c set c.nameEn = :nameEn, c.nameUk = :nameUk WHERE c.id = :id")
	int updateCategoryById(int id, String nameEn, String nameUk);

}
