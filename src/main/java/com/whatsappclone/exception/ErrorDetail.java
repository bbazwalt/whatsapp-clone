package com.whatsappclone.exception;

import java.time.LocalDateTime;

public class ErrorDetail {

	private String error;
	private String message;
	private LocalDateTime timeStamp;

	public ErrorDetail() {
	}

	public ErrorDetail(String error, String message, LocalDateTime timeStamp) {
		this.error = error;
		this.message = message;
		this.timeStamp = timeStamp;
	}

}
