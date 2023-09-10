package com.whatsapp.backend.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.JoinColumn;
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

	private String chat_name;
	private String chat_image;
	private boolean is_group;

	@ManyToOne
	@JoinColumn
	private User created_by;

	@ManyToMany
	private Set<User> users = new HashSet<>();

	@ManyToMany
	private Set<User> admins = new HashSet<>();
	
	@OneToMany
	private List<Message> messages = new ArrayList<>();

	public Chat() {
		super();
	}

	public Chat(Long id, String chat_name, String chat_image, boolean is_group, User created_by, Set<User> users,
			Set<User> admins, List<Message> messages) {
		super();
		this.id = id;
		this.chat_name = chat_name;
		this.chat_image = chat_image;
		this.is_group = is_group;
		this.created_by = created_by;
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

	public String getChat_name() {
		return chat_name;
	}

	public void setChat_name(String chat_name) {
		this.chat_name = chat_name;
	}

	public String getChat_image() {
		return chat_image;
	}

	public void setChat_image(String chat_image) {
		this.chat_image = chat_image;
	}

	public boolean isIs_group() {
		return is_group;
	}

	public void setIs_group(boolean is_group) {
		this.is_group = is_group;
	}

	public User getCreated_by() {
		return created_by;
	}

	public void setCreated_by(User created_by) {
		this.created_by = created_by;
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
