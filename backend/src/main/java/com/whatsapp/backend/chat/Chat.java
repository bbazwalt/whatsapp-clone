package com.whatsapp.backend.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.whatsapp.backend.message.Message;
import com.whatsapp.backend.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
