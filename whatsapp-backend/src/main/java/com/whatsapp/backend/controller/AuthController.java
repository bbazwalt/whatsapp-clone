package com.whatsapp.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.backend.config.TokenProvider;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.UserRepository;
import com.whatsapp.backend.request.LoginRequest;
import com.whatsapp.backend.response.AuthResponse;
import com.whatsapp.backend.service.CustomUserService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;
	private CustomUserService customUserService;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,TokenProvider tokenProvider,
			CustomUserService customUserService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider=tokenProvider;
		this.customUserService = customUserService;
		
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String fullName = user.getFull_name();
		String password = user.getPassword();
		User isUser = userRepository.findByEmail(email);
		if (isUser != null) {
			throw new UserException("A user with the given email already exists" + email);
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFull_name(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		userRepository.save(createdUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) {
		String email = req.getEmail();
		String password = req.getPassword();
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}

	public Authentication authenticate(String Username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(Username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
