package com.whatsapp.backend.service;

import java.util.List;

import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Long id) throws UserException;

	public User findUserProfile(String jwt) throws UserException;

	public User updateUser(Long userId, UpdateUserRequest req) throws UserException;

	public List<User> searchUser(String query);

}
