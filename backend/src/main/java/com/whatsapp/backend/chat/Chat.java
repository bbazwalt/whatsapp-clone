package com.whatsapp.backend.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.whatsapp.backend.message.Message;
import com.whatsapp.backend.user.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String chatName;
	private String chatImage;
	
	@Column(name = "is_group")
	private boolean isGroup;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@ManyToMany
	private Set<User> users = new HashSet<>();

	@ManyToMany
	private Set<User> admins = new HashSet<>();
	
	@OneToMany
	private List<Message> messages = new ArrayList<>();

	public Chat() {
		super();
	}

	public Chat(Long id, String chatName, String chatImage, boolean isGroup, User createdBy, Set<User> users,
			Set<User> admins, List<Message> messages) {
		super();
		this.id = id;
		this.chatName = chatName;
		this.chatImage = chatImage;
		this.isGroup = isGroup;
		this.createdBy = createdBy;
		this.users = users;
		this.admins = admins;
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public String getChatImage() {
		return chatImage;
	}

	public void setChat_image(String chatImage) {
		this.chatImage = chatImage;
	}

	public boolean isIsGroup() {
		return isGroup;
	}

	public void setIs_group(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<User> admins) {
		this.admins = admins;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
