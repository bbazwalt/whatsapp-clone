package com.whatsapp.backend.chat;

public class SingleChatRequest {

	private Long userId;
	
	public SingleChatRequest() {
		super();
	}
	
	public SingleChatRequest(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
