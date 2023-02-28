package com.gl.surabhiChains2.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gl.surabhiChains2.entities.User;
import com.gl.surabhiChains2.models.UserInfo;
import com.gl.surabhiChains2.repositories.UserRepository;
import com.gl.surabhiChains2.udExceptions.UserNotFoundException;
import com.gl.surabhiChains2.utilities.UserDetailsImpl;

@Service
public class UserServiceImpl implements UserService,UserDetailsService
{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public User registerUser(User user)
	{
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		//Load userDetails from DB if user is found else raise exception
		Optional<User> optUser = userRepo.findByUserName(userName);
		if(optUser.isEmpty())
			throw new UsernameNotFoundException(userName+" Not Registerd" );
		
		User foundUser = optUser.get();
		
		return UserDetailsImpl.build(foundUser);
			
	}
	
	//return user  object if found
	@Override
	public User getUserById(int userId)
	{
		return userRepo.findById(userId).get();
	}

	//return userName by userId
	@Override
	public String getUserNameById(int userId) 
	{
		Optional<User> user = userRepo.findById(userId);
		return user.get().getUserName();
	}

	//check whether user exist or not
	@Override
	public boolean existsByUserName(String userName)
	{
		return userRepo.existsByUserName(userName);
	}

	//get user object by username
	@Override
	public User getUserByUserName(String userName) throws UserNotFoundException
	{
		Optional<User> optUser =  userRepo.findByUserName(userName);
		if(optUser.isEmpty())
			throw new UserNotFoundException(userName);
		else
			return optUser.get();
	}

	@Override
	public User updateUser(String userName, String newRole) throws UserNotFoundException
	{
		User foundUser = getUserByUserName(userName);
		foundUser.setRole(newRole);
		return userRepo.save(foundUser);
			
	}

	@Override
	public String deleteUser(String newUserName) throws UserNotFoundException
	{
		User foundUser = getUserByUserName(newUserName);
		userRepo.delete(foundUser);	
		return "DELETED";
	}

	@Override
	public List<UserInfo> getAllUsers()
	{
		List<User> users = userRepo.findAll();
		List<UserInfo> usersModel = new ArrayList<UserInfo>();
		for(User user : users)
		{
			usersModel.add( UserInfo.builder()
																.message("Active")
																.userId(user.getUid())
																.userName(user.getUserName())
																.role(user.getRole())
																.email(user.getEmail())
																.build() );
		}
		return usersModel;
	}
	
}
