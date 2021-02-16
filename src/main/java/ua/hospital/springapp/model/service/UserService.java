package ua.hospital.springapp.model.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.hospital.springapp.model.entity.User;
import ua.hospital.springapp.model.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	private final static Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).get();
	}
	
	public boolean createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			userRepository.save(user);
		} catch(IllegalArgumentException ex) {
			logger.info("attempt to register user under existing login");
			return false;
		}
		return true;
	}

}
