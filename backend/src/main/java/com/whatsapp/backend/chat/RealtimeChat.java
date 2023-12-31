package com.whatsapp.backend.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.whatsapp.backend.message.Message;

@Controller
public class RealtimeChat {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat/{chatId}")
	public Message sendToUser(@Payload Message message, @DestinationVariable String chatId) {
		simpMessagingTemplate.convertAndSendToUser(chatId, "/private", message);
		return message;
	}

}
