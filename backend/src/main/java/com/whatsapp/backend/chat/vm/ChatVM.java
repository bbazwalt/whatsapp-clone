package com.whatsapp.backend.chat.vm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.whatsapp.backend.chat.Chat;
import com.whatsapp.backend.message.Message;
import com.whatsapp.backend.message.vm.MessageVM;
import com.whatsapp.backend.user.User;
import com.whatsapp.backend.user.vm.UserVM;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatVM {

	private Long id;
	private String chatName;
	private String chatImage;
	private boolean isGroup;
	private UserVM createdBy;
	private Set<UserVM> users = new HashSet<>();
	private Set<UserVM> admins = new HashSet<>();
	private List<MessageVM> messages = new ArrayList<>();

	public ChatVM(Chat chat) {
		this.setId(chat.getId());
		this.setChatName(chat.getChatName());
		this.setChatImage(chat.getChatImage());
		this.setGroup(chat.isGroup());
		this.setCreatedBy(new UserVM(chat.getCreatedBy()));
		this.setUsers(toUserVM(chat.getUsers()));
		this.setAdmins(toUserVM(chat.getAdmins()));
		this.setMessages(toMessageVM(chat.getMessages()));

	}

	private Set<UserVM> toUserVM(Set<User> users) {
		Set<UserVM> set = new HashSet<>();
		for (User user : users) {
			set.add(new UserVM(user));
		}
		return set;
	}

	private List<MessageVM> toMessageVM(List<Message> messages) {
		List<MessageVM> list = new ArrayList<>();
		for (Message message : messages) {
			list.add(new MessageVM(message));
		}
		return list;
	}

}
