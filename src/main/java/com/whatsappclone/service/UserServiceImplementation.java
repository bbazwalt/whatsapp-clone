package com.whatsappclone.service;

import java.util.List;

import com.whatsappclone.exception.UserException;
import com.whatsappclone.modal.User;
import com.whatsappclone.repository.UserRepository;
import com.whatsappclone.request.UpdateUserRequest;

public class UserServiceImplementation implements UserService{

	private UserRepository userRepository;
	
	public UserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> opt = userRepository.f
		return null;
	}

	@Override
	public User findUserProfile(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> searchUser(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
