package com.whatsappclone.service;

import java.util.List;

import com.whatsappclone.exception.UserException;
import com.whatsappclone.model.User;
import com.whatsappclone.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Long id) throws UserException;

	public User findUserProfile(String jwt) throws UserException;

	public User updateUser(Long userId, UpdateUserRequest req) throws UserException;

	public List<User> searchUser(String query);

}
