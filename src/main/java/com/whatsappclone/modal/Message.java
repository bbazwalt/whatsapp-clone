package com.whatsappclone.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String content;
	private LocalDateTime timestamp;

	@ManyToOne
	private User user;

	@ManyToOne
	private Chat chat;

	public Message() {
	}

	public Message(Integer id, String content, LocalDateTime timestamp, User user, Chat chat) {
		this.id = id;
		this.content = content;
		this.timestamp = timestamp;
		this.user = user;
		this.chat = chat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

}
