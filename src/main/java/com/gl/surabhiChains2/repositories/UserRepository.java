package com.gl.surabhiChains2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.surabhiChains2.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

	Optional<User> findByUserName(String userName);

	boolean existsByUserName(String userName);

}
