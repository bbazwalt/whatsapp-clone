package com.whatsapp.backend.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.whatsapp.backend.message.Message;

public class RealtimeChat {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/message")
	@SendTo("/group/public")
	public Message receiveMessage(@Payload Message message) {

		simpMessagingTemplate.convertAndSend("/group/" + message.getChat().getId(), message);
		return message;
	}

}
