package com.whatsapp.backend.message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT m FROM Message m JOIN m.chat c WHERE c.id=:chatId")
	public List<Message> findByChatId(@Param("chatId") Long chatId);
}
