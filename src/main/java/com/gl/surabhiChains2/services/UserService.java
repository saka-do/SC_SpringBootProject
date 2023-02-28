package com.gl.surabhiChains2.services;

import java.util.List;

import com.gl.surabhiChains2.entities.User;
import com.gl.surabhiChains2.models.UserInfo;
import com.gl.surabhiChains2.udExceptions.UserNotFoundException;

public interface UserService
{
	User registerUser(User user);
	User getUserById(int userId);
	String getUserNameById(int userId);
	boolean existsByUserName(String userName);
	User getUserByUserName(String userName) throws UserNotFoundException;
	User updateUser(String userName, String newRole) throws UserNotFoundException;
	String deleteUser(String userName) throws UserNotFoundException;
	List<UserInfo> getAllUsers();
}
