package com.whatsappclone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.whatsappclone.config.TokenProvider;
import com.whatsappclone.exception.UserException;
import com.whatsappclone.modal.User;
import com.whatsappclone.repository.UserRepository;
import com.whatsappclone.request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService {

	private UserRepository userRepository;
	private TokenProvider tokenProvider;

	public UserServiceImplementation(UserRepository userRepository, TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public User findUserById(Integer id) throws UserException {
		Optional<User> opt = userRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("No user found with the id" + id);

	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email = tokenProvider.getEmailFromToken(jwt);
		if (email == null) {
			throw new BadCredentialsException("Invalid Token");
		}
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("No user found with the email" + email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		User user = findUserById(userId);
		if (req.getFullName() != null) {
			user.setFullName(req.getFullName());
		}
		if (req.getProfilePicture() != null) {
			user.setProfilePicture(req.getProfilePicture());
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users = userRepository.searchUser(query);
		return users;
	}

}
