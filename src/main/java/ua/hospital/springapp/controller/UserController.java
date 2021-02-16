package ua.hospital.springapp.controller;

import java.time.LocalDate;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("user_registration_form")
	String userRegistrationForm() {
		return "views/userRegistrationForm";
	}
	
	@Secured("ADMIN")
	@PostMapping("register_user")
	String registerUser(Model model,
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
				.role(role)
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
