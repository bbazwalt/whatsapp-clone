package com.whatsapp.backend.user;

public class UpdateUserRequest {

	private String fullName;
	private String profilePicture;

	public UpdateUserRequest() {
		super();
	}

	public UpdateUserRequest(String fullName, String profilePicture) {
		super();
		this.fullName = fullName;
		this.profilePicture = profilePicture;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

}
