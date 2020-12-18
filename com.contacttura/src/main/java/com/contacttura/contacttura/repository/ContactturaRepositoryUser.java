package com.contacttura.contacttura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contacttura.contacttura.model.User;

@Repository
public interface ContactturaRepositoryUser extends JpaRepository<User, Long> {
	
	

}
