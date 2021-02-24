package ua.hospital.springapp.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import ua.hospital.springapp.model.entity.Meal;
import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.entity.Person;
import ua.hospital.springapp.model.entity.Prescription;
import ua.hospital.springapp.model.repository.PatientRepository;

@SpringBootTest
class PatientServiceTest {

	@InjectMocks
	PatientService patientService = new PatientService();

	@Mock
	PatientRepository patientRepository;

	@Test
	public void testFindById() {
		int patientId = 1;
		
		Prescription prescription = Prescription.builder()
		.id(1)
		.build();
		
		Person person = Person.builder()
				.firstNameEn("Test")
				.firstNameUk("Тест")
				.lastNameEn("Test")
				.lastNameUk("Тест")
				.birthDate(LocalDate.now())
				.build();
		Patient patient = Patient.builder()
				.person(person)
				.id(patientId)
				.arriveDateTime(LocalDateTime.now())
				.prescriptions(new ArrayList<Prescription>())
				.meals(Collections.singleton(new Meal("Test", "Тест")))
				.build();
		
		patient.addPrescription(prescription);
		
		Mockito.when(patientRepository.findById(patientId))
		.thenReturn(Optional.of(patient));
		Assert.assertEquals(false, patientService.findById(patientId).get().toString().equals(""));
	}
	
	@Test
	public void testCreatePatient() {
		Patient patient = Patient.builder().build();

		Mockito.when(patientRepository.save(patient)).thenReturn(Patient.builder().id(1).build());
		Assert.assertEquals(true, patientService.createPatient(patient));
	}
	
	@Test
	public void testDischargePatient() {
		int value = 1;
		Mockito.when(patientRepository.dischargePatient(value)).thenReturn(value);
		Assert.assertEquals(true, patientService.dischargePatient(1));
	}
	
	@Test
	public void testAssignDoctor() {
		int value = 1;
		Mockito.when(patientRepository.assignDoctor(value, value)).thenReturn(value);
		Assert.assertEquals(true, patientService.assignDoctor(1, 1));
	}
	
	@Test
	public void testDefineDiagnosis() {
		int value = 1;
		Mockito.when(patientRepository.defineDiagnosis(value, "Ill", "Хворий")).thenReturn(value);
		Assert.assertEquals(true, patientService.defineDiagnosis(value, "Ill", "Хворий"));
	}
	
	@Test
	public void testAssignMeal() {
		int value = 1;
		Mockito.when(patientRepository.assignMeal(value, value)).thenReturn(value);
		Assert.assertEquals(true, patientService.assignMeal(1, 1));
	}
	
	@Test
	public void testRemoveMeal() {
		int value = 1;
		Mockito.when(patientRepository.removeMeal(value, value)).thenReturn(value);
		Assert.assertEquals(true, patientService.removeMeal(1, 1));
	}


}
