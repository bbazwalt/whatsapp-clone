package com.whatsapp.backend.shared;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

	private String error;
	private String message;
	private LocalDateTime timeStamp;

}
