package com.contacttura.contacttura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contacttura.contacttura.model.User;

@Repository
public interface ContactturaRepositoryUser extends JpaRepository<User, Long> {
	
//buscar pelo nome do usuario
	User findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByUsernameAndPassword(String username, String password);
	
	User findByPassword(String password);
	//@Query
	//("Select U from User U where U.username=:Param")
	//User login(@Param ("Param")String login);
	
	


}
