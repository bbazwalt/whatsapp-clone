package com.whatsapp.backend.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

	private Long userId;
	private Long chatId;
	private String content;

}
