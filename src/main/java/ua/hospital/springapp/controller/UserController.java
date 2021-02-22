package ua.hospital.springapp.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.hospital.springapp.model.dto.DoctorDto;
import ua.hospital.springapp.model.dto.UserDto;
import ua.hospital.springapp.model.entity.Doctor;
import ua.hospital.springapp.model.entity.Person;
import ua.hospital.springapp.model.entity.Role;
import ua.hospital.springapp.model.entity.User;
import ua.hospital.springapp.model.service.DoctorService;
import ua.hospital.springapp.model.service.UserService;
import ua.hospital.springapp.support.Constants;
import ua.hospital.springapp.support.ValidationHelper;

@Controller
public class UserController {
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	DoctorService doctorService;
	
	@GetMapping("login")
	public String authentication() {
		if (isAuthenticated()) {
			return "redirect:";
		}
		logger.info("Entering login page");
		return "views/loginForm";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("user_registration_form")
	public String userRegistrationForm() {
		return "views/userRegistrationForm";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("register_user")
	public String registerUser(Model model,
			@RequestParam("firstNameEn") String firstNameEn,
			@RequestParam("firstNameUk") String firstNameUk,
			@RequestParam("lastNameEn") String lastNameEn,
			@RequestParam("lastNameUk") String lastNameUk,
			
			@RequestParam("birthDate") 
				@DateTimeFormat(pattern = Constants.LOCAL_DATE_PATTERN) LocalDate birthDate,
				
			@RequestParam("role") Role role,
			@RequestParam("login") String login,
			@RequestParam("password") String password) {
		
		if (!ValidationHelper.isLatinName(firstNameEn) || !ValidationHelper.isCyrillicName(firstNameUk) ||
				!ValidationHelper.isLatinName(lastNameEn) || !ValidationHelper.isCyrillicName(lastNameUk) ||
				 !ValidationHelper.isLogin(login) || !ValidationHelper.isPassword(password)) {
			
			logger.info("invalid user registration information");
			model.addAttribute("message", "invalidData");
			return "views/userRegistrationForm";
		}
				
		Person person = Person.builder()
				.firstNameEn(firstNameEn)
				.firstNameUk(firstNameUk)
				.lastNameEn(lastNameEn)
				.lastNameUk(lastNameUk)
				.birthDate(birthDate)
				.build();
		User user = User.builder()
				.roles(Collections.singleton(role))
				.person(person)
				.username(login)
				.password(password)
				.build();
		
		if (createAccordingToRole(user)) {
			logger.info("New account is successfully created");
			return "redirect:";
		}
		logger.info("Could not create new account");
		model.addAttribute("message", "userRegistrationFailed");
		return "views/userRegistrationForm";
	}
	
	@GetMapping("profile_own")
	public String profileOwn(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		Optional<DoctorDto> optionalDoctor = Optional.empty();
		if (authentication.getAuthorities().contains(Role.DOCTOR)) {
			optionalDoctor = doctorService.findByUserUsername(username);
		}
		if(optionalDoctor.isPresent()) {
			logger.info("Doctor is found and sent");
			logger.info( optionalDoctor.get());
			model.addAttribute("doctor", optionalDoctor.get());
			model.addAttribute("user", optionalDoctor.get().getUserDto());
			model.addAttribute("person", optionalDoctor.get().getUserDto().getPersonDto());
			return "views/profile";			
		}
		
		Optional<UserDto> optionalUser = Optional.empty();
		if (!authentication.getAuthorities().contains(Role.DOCTOR)) {
			optionalUser = userService.findByUsername(username);
		}
		
		if(optionalUser.isPresent()) {
			logger.info("User is found and sent");
			logger.info( optionalUser.get());
			model.addAttribute("user", optionalUser.get());
			model.addAttribute("person", optionalUser.get().getPersonDto());
			return "views/profile";
		}
		
		logger.error("Some error occured while entity searching");
		model.addAttribute("message", "dataMissing");
		return "error/errorMessage";
	}
	
	@GetMapping("profile")
	public String profile(Model model,
			@RequestParam int userId,
			@RequestParam Role userRole) {		
		
		Optional<DoctorDto> optionalDoctor = Optional.empty();
		if (userRole == Role.DOCTOR) {
			optionalDoctor = doctorService.findById(userId);
		}
		
		if(optionalDoctor.isPresent()) {
			logger.info("Doctor is found and sent");
			logger.info( optionalDoctor.get());
			model.addAttribute("doctor", optionalDoctor.get());
			model.addAttribute("user", optionalDoctor.get().getUserDto());
			model.addAttribute("person", optionalDoctor.get().getUserDto().getPersonDto());
			return "views/profile";			
		}
		
		Optional<UserDto> optionalUser = Optional.empty();
		if (userRole != Role.DOCTOR) {
			optionalUser = userService.findById(userId);
		}
		
		if(optionalUser.isPresent()) {
			logger.info("User is found and sent");
			logger.info( optionalUser.get());
			model.addAttribute("user", optionalUser.get());
			model.addAttribute("person", optionalUser.get().getPersonDto());
			return "views/profile";
		}
		
		logger.error("Some error occured while entity searching");
		model.addAttribute("message", "dataMissing");
		return "error/errorMessage";
	}
	

	
	private boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}
	
	
	/**
	 * Method that helps to choose how to process the {@link User} object
	 * regarding its {@link User#getRole()} value.
	 * 
	 * @param user - the User object to process by deeper methods
	 * @return - value returned by deeper methods.
	 */
	
	private boolean createAccordingToRole(User user) {
		if (user.isDoctor()) {
			return doctorService.createDoctor(new Doctor(user));
		}
		return userService.createUser(user);
	}
}
