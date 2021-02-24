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

import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.entity.Person;
import ua.hospital.springapp.model.entity.Prescription;
import ua.hospital.springapp.model.repository.PatientRepository;
import ua.hospital.springapp.model.repository.PrescriptionRepository;

@SpringBootTest
class PrescriptionServiceTest {
	
	@InjectMocks
	PrescriptionService prescriptionService;
	
	@Mock
	PrescriptionRepository prescriptionRepository;
	
	@Mock
	PatientRepository patientRepository;

	@Test
	void testCreatePrescription() {
		int patientId = 1;
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
				.meals(Collections.emptySet())
				.build();
		
		Prescription prescription = Prescription.builder()
				.patient(patient)
				.build();
		
		Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
		Mockito.when(prescriptionRepository.save(prescription))
		.thenReturn(Prescription.builder()
				.patient(patient)
				.id(1)
				.build());
		Assert.assertEquals(true, prescriptionService.createPrescription(prescription, 1));
		
	}

	@Test
	void testCompletePrescription() {
		int value = 1;
		Mockito.when(prescriptionRepository.completePrescription(value)).thenReturn(value);
		Assert.assertEquals(true, prescriptionService.completePrescription(value));
	}

	@Test
	void testDeletePrescription() {
		int value = 1;
		Mockito.when(prescriptionRepository.deletePrescriptionById(value)).thenReturn(value);
		Assert.assertEquals(true, prescriptionService.deletePrescription(value));
	}

}
