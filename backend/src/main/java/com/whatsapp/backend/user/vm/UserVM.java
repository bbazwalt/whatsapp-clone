package com.whatsapp.backend.user.vm;

import com.whatsapp.backend.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVM {

	private Long id;
	private String fullName;
	private String email;
	private String profilePicture;

	public UserVM(User user) {
		this.setId(user.getId());
		this.setFullName(user.getFullName());
		this.setEmail(user.getEmail());
		this.setProfilePicture(user.getProfilePicture());
	}
}
