package com.whatsappclone.service;

import java.util.List;

import com.whatsappclone.exception.UserException;
import com.whatsappclone.modal.User;

public interface UserService {
	
	public User findUserById(Integer id);
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public List<User> searchUser(String query);
	

}
