package com.whatsapp.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.backend.configuration.TokenProvider;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.user.CustomUserService;
import com.whatsapp.backend.user.User;
import com.whatsapp.backend.user.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/auth")
@NoArgsConstructor
@AllArgsConstructor
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	CustomUserService customUserService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String fullName = user.getFullName();
		String password = user.getPassword();
		User isUser = userRepository.findByEmail(email);
		if (isUser != null) {
			throw new UserException("A user with the given email already exists" + email);
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
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
