package com.whatsappclone.request;

public class UpdateUserRequest {

	private String fullName;
	private String profilePicture;

	public UpdateUserRequest() {
	}

	public UpdateUserRequest(String fullName, String profilePicture) {
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
