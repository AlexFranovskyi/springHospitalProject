package ua.hospital.springapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.hospital.springapp.model.entity.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
	
	int deleteMealById(int id);
	
	@Modifying
	@Query("UPDATE Meal m set m.nameEn = :nameEn, m.nameUk = :nameUk WHERE m.id = :id")
	int updateMealById(int id, String nameEn, String nameUk);

}
