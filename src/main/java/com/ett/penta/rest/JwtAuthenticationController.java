package com.ett.penta.rest;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ett.penta.config.JwtTokenProvider;
import com.ett.penta.model.User;
import com.ett.penta.model.dto.LoginRequest;
import com.ett.penta.model.dto.SignUpRequest;
import com.ett.penta.model.dto.UserPrincipal;
import com.ett.penta.rest.util.ApiResponse;
import com.ett.penta.service.CustomUserDetailsService;
import com.ett.penta.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenUtil;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private UserService userService;
	@Autowired
    PasswordEncoder passwordEncoder;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	public RequestMappingHandlerMapping requestMappingHandlerMapping;
	@PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws  IOException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal user = tokenProvider.getUser(authentication);
        HashMap<String, Serializable> re = new HashMap<String, Serializable>();
        re.put("jwtToken", jwt);
        re.put("user", user);
        //SendEmail.sendmail(loginRequest.getEmail(), "login", "you login");
        return ResponseEntity.ok(re);
    }
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(null,signUpRequest.getPassword(),signUpRequest.getFirstName(), signUpRequest.getLastName(),
                 signUpRequest.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/")
                .buildAndExpand(result.getFirstName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

	@RequestMapping("/endpoints")
	public @ResponseBody
	Object showEndpointsAction() throws SQLException, IOException
	{
	        return requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(t ->
	               (t.getMethodsCondition().getMethods().size() == 0 ? "GET" : t.getMethodsCondition().getMethods().toArray()[0]) + " " +                    
	               t.getPatternsCondition().getPatterns().toArray()[0]
	        ).toArray();
	 }
}
