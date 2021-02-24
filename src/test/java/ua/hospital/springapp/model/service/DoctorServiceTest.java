package ua.hospital.springapp.model.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.hospital.springapp.model.entity.Category;
import ua.hospital.springapp.model.entity.Doctor;
import ua.hospital.springapp.model.entity.Person;
import ua.hospital.springapp.model.entity.Role;
import ua.hospital.springapp.model.entity.User;
import ua.hospital.springapp.model.repository.DoctorRepository;

@SpringBootTest
class DoctorServiceTest {
	
	@InjectMocks
	DoctorService doctorService = new DoctorService();

	@Mock
	DoctorRepository doctorRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	void testCreateDoctor() {
		Category category = new Category("Test", "Тест");
		Person person = Person.builder()
				.firstNameEn("Test")
				.firstNameUk("Тест")
				.lastNameEn("Test")
				.lastNameUk("Тест")
				.birthDate(LocalDate.now())
				.build();
		User user = User.builder()
				.person(person)
				.password("pass")
				.username("name")
				.roles(Collections.singleton(Role.DOCTOR))
				.build();
		Doctor doctor = Doctor.builder()
				.user(user)
				.category(category)
				.build();
		
		Mockito.when(passwordEncoder.encode("pass")).thenReturn("pass");
		Mockito.when(doctorRepository.save(doctor))
		.thenReturn(Doctor.builder().category(category).user(user).id(1).build());
		Assert.assertEquals(true, doctorService.createDoctor(doctor));
	}

	@Test
	void testFindByUserUsername() {
		Category category = new Category("Test", "Тест");
		Person person = Person.builder()
				.firstNameEn("Test")
				.firstNameUk("Тест")
				.lastNameEn("Test")
				.lastNameUk("Тест")
				.birthDate(LocalDate.now())
				.build();
		User user = User.builder()
				.person(person)
				.password("pass")
				.username("name")
				.roles(Collections.singleton(Role.DOCTOR))
				.build();
		
		Mockito.when(doctorRepository.findByUserUsername("name"))
		.thenReturn(Optional.of(Doctor.builder().category(category).user(user).id(1).build()));
		Assert.assertEquals(true, doctorService.findByUserUsername("name").isPresent());
	}

	@Test
	void testFindById() {
		Category category = new Category("Test", "Тест");
		Person person = Person.builder()
				.firstNameEn("Test")
				.firstNameUk("Тест")
				.lastNameEn("Test")
				.lastNameUk("Тест")
				.birthDate(LocalDate.now())
				.build();
		User user = User.builder()
				.person(person)
				.password("pass")
				.username("name")
				.roles(Collections.singleton(Role.DOCTOR))
				.build();
		Doctor doctor = Doctor.builder()
				.id(1)
				.user(user)
				.category(category)
				.build();
		
		Mockito.when(doctorRepository.findById(1))
		.thenReturn(Optional.of(doctor));
		Assert.assertEquals(false, doctorService.findById(1).get().toString().equals(""));
	}

	@Test
	void testAssignCategory() {
		int value = 1;
		Mockito.when(doctorRepository.assignCategory(value, value)).thenReturn(value);
		Assert.assertEquals(true, doctorService.assignCategory(1, 1));
	}

}
