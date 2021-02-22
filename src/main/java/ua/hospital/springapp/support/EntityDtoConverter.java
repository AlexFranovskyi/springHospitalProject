package ua.hospital.springapp.support;

import java.util.Optional;
import java.util.stream.Collectors;

import ua.hospital.springapp.model.dto.CategoryDto;
import ua.hospital.springapp.model.dto.DoctorDto;
import ua.hospital.springapp.model.dto.MealDto;
import ua.hospital.springapp.model.dto.PatientDto;
import ua.hospital.springapp.model.dto.PersonDto;
import ua.hospital.springapp.model.dto.PrescriptionDto;
import ua.hospital.springapp.model.dto.UserDto;
import ua.hospital.springapp.model.entity.Category;
import ua.hospital.springapp.model.entity.Doctor;
import ua.hospital.springapp.model.entity.Meal;
import ua.hospital.springapp.model.entity.Patient;
import ua.hospital.springapp.model.entity.Person;
import ua.hospital.springapp.model.entity.Prescription;
import ua.hospital.springapp.model.entity.User;

public class EntityDtoConverter {
	
	public static PersonDto PersonToDto(Person person) {
		return PersonDto.builder()
				.id(person.getId())
				.firstNameEn(person.getFirstNameEn())
				.firstNameUk(person.getFirstNameUk())
				.lastNameEn(person.getLastNameEn())
				.lastNameUk(person.getLastNameUk())
				.birthDate(person.getBirthDate())
				.build();
	}
	
	public static UserDto UserToDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.username(user.getUsername())
				.personDto(Optional.ofNullable(user.getPerson())
						.map(EntityDtoConverter::PersonToDto).orElse(null))
				.roles(user.getRoles())
				.build();
	}
	
	public static DoctorDto DoctorToDto(Doctor doctor) {
		return DoctorDto.builder()
				.id(doctor.getId())
				.userDto(Optional.ofNullable(doctor.getUser())
						.map(EntityDtoConverter::UserToDto).orElse(null))
				.categoryDto(Optional.ofNullable(doctor.getCategory())
						.map(EntityDtoConverter::CategoryToDto).orElse(null))
				.patientAmount(doctor.getPatientAmount())
				.build();
	}
	
	public static CategoryDto CategoryToDto(Category category) {
		return CategoryDto.builder()
				.id(category.getId())
				.nameEn(category.getNameEn())
				.nameUk(category.getNameUk())
				.build();
	}
	
	public static MealDto MealToDto(Meal meal) {
		return MealDto.builder()
				.id(meal.getId())
				.nameEn(meal.getNameEn())
				.nameUk(meal.getNameUk())
				.build();
	}
	
	public static PrescriptionDto PrescriptionToDto(Prescription prescription) {
		return PrescriptionDto.builder()
				.id(prescription.getId())
				.descriptionEn(prescription.getDescriptionEn())
				.descriptionUk(prescription.getDescriptionUk())
				.type(prescription.getType())
				.completionTime(prescription.getCompletionTime())
				.patientId(Optional.ofNullable(prescription.getPatient())
						.map(p -> p.getId()).orElse(null))
				.build();
	}
	
	public static PatientDto PatientToDto(Patient patient) {
		return PatientDto.builder()
				.id(patient.getId())
				.personDto(Optional.ofNullable(patient.getPerson())
						.map(EntityDtoConverter::PersonToDto).orElse(null))
				
				.arriveDateTime(patient.getArriveDateTime())
				.diagnosisEn(patient.getDiagnosisEn())
				.diagnosisUk(patient.getDiagnosisUk())
				.dischargeDateTime(patient.getDischargeDateTime())
				
				.doctorDto(Optional.ofNullable(patient.getDoctor())					
						.map(EntityDtoConverter::DoctorToDto).orElse(null))
				
				.prescriptions(patient.getPrescriptions().stream()					
						.map(EntityDtoConverter::PrescriptionToDto).collect(Collectors.toList()))
				
				.meals(patient.getMeals().stream()
						.map(EntityDtoConverter::MealToDto).collect(Collectors.toSet()))
				.build();
	}

}
