package com.whatsappclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsappclone.modal.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	

}
