package com.whatsapp.backend.user;

import java.util.List;

import com.whatsapp.backend.exception.UserException;

public interface UserService {

	public User findUserById(Long id) throws UserException;

	public User findUserProfile(String jwt) throws UserException;

	public User updateUser(Long userId, UpdateUserRequest req) throws UserException;

	public List<User> searchUser(String query);

}
