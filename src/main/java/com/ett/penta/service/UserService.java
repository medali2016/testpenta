package com.ett.penta.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ett.penta.exception.ResourceNotFoundException;
import com.ett.penta.model.User;
import com.ett.penta.repository.UserRepository;


@Service
@Transactional
public class UserService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private  UserRepository userRepository;

	public User save(User user) {
		log.debug("Request to save User : {}", user);
		//article.isNormalised(false);
		//:userRepositoryElastic.save(user);
		User result = userRepository.save(user);
		return result; //test branch
		//test to sarray branch
	}
	
	public boolean existsByEmail(String emial) {
		User user = userRepository.getByEmail(emial); //add test 2 to test br
		if (user != null) {
			return true;
		}
		return false;
	}
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
				 
	}
	public ResponseEntity<String> deleteAllusers() {
		System.out.println("Delete All users...");
		userRepository.deleteAll();
		return new ResponseEntity<>("All users have been deleted!", HttpStatus.OK);
	}

	public void deleteuser(long id) {
		userRepository.deleteById(id);
		
	}

	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
	}
	public User postuser(User user) {
		System.out.print("user.getEmail()");
		User _user = userRepository.save(user);
		return _user;
	}
	public Page<User> getAllusers(Pageable pageable) {
		System.out.println("Get all users..."); 
		return userRepository.findAll(pageable);
		// return users;
	}
}
