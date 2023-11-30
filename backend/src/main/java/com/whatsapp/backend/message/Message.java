package com.whatsapp.backend.message;

import java.time.LocalDateTime;

import com.whatsapp.backend.chat.Chat;
import com.whatsapp.backend.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String content;
	private LocalDateTime timestamp;

	@ManyToOne
	private User user;

	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

}
