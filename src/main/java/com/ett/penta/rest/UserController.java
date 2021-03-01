package com.ett.penta.rest;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ett.penta.model.User;
import com.ett.penta.model.dto.UserDto;
import com.ett.penta.rest.util.ApiResponse;
import com.ett.penta.rest.util.HeaderUtil;
import com.ett.penta.service.UserService;

@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4203")
@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final String ENTITY_NAME = "user";
	private static final Integer pageSize = 10;
	@Autowired
	UserService userService;
	//@Autowired
    //PasswordEncoder passwordEncoder;
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAllusers() {
		//System.out.println("Delete All users...");
		ResponseEntity<String> Resultat;
		try {
			userService.deleteAllusers();
			Resultat = ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAllAlert(ENTITY_NAME ))
					.build();
		}catch(Exception e){
			Resultat =  ResponseEntity.notFound().headers(HeaderUtil.createEntityDeletionAllAlert(ENTITY_NAME ))
					.build();
		}
		return Resultat;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteuser(@PathVariable("id") long id) {
		
		ResponseEntity<String> Resultat;
		User us = userService.getUserById(id);
		userService.postuser(us);
		try {
			
			Resultat = ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, Long.toString(id)))
					.build();
		}catch(Exception e){
			Resultat =  ResponseEntity.notFound().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, Long.toString(id)))
					.build();
		}
		return Resultat;
	}
	@GetMapping(value = "/email/{email}")
	public User findByEmail(@PathVariable String email) {
		User user = userService.findByEmail(email);
		return user;
	}

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable(value = "userId") Long userId) {
		return userService.getUserById(userId);
	}
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        User user = new User(null,signUpRequest.getFirstName(), signUpRequest.getLastName(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        /*Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("Admin Role not set."));*/
        User result = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getFirstName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}

