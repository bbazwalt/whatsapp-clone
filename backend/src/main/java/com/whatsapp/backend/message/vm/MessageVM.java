package com.whatsapp.backend.message.vm;

import java.time.LocalDateTime;

import com.whatsapp.backend.chat.vm.ChatVM;
import com.whatsapp.backend.message.Message;
import com.whatsapp.backend.user.vm.UserVM;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageVM {

	private Long id;
	private String content;
	private LocalDateTime timestamp;
	private UserVM user;
	private ChatVM chat;

	public MessageVM(Message message) {
		this.setId(message.getId());
		this.setContent(message.getContent());
		this.setTimestamp(message.getTimestamp());
		this.setUser(new UserVM(message.getUser()));
		this.setChat(new ChatVM(message.getChat()));
	}

}
