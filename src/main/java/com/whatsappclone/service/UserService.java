package com.whatsappclone.service;

import java.util.List;

import com.whatsappclone.exception.UserException;
import com.whatsappclone.modal.User;
import com.whatsappclone.request.UpdateUserRequest;

public interface UserService {
	
	public User findUserById(Integer id) throws UserException;
	
	public User findUserProfile(String jwt) throws UserException;
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException ;
	
	public List<User> searchUser(String query);
	

}
