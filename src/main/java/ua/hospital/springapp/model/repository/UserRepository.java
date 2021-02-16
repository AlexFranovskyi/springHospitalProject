package ua.hospital.springapp.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.hospital.springapp.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
