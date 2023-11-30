package com.whatsapp.backend.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.backend.chat.Chat;
import com.whatsapp.backend.chat.ChatService;
import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.MessageException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.user.User;
import com.whatsapp.backend.user.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	UserService userService;

	@Autowired
	ChatService chatService;

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getChatMessages(Long chatId, User reqUser) throws UserException, ChatException {
		Chat chat = chatService.findChatById(chatId);
		if (!chat.getUsers().contains(reqUser)) {
			throw new UserException("You are not related to this chat" + chat.getId());
		}
		List<Message> messages = messageRepository.findByChatId(chat.getId());
		return messages;
	}

	@Override
	public Message findMessageById(Long messageId) throws MessageException {
		Optional<Message> opt = messageRepository.findById(messageId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message not found with the id" + messageId);
	}

	@Override
	public void deleteMessage(Long messageId, User reqUser) throws UserException, MessageException {
		Message message = findMessageById(messageId);
		if (message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You can't delete another user's message" + reqUser.getFullName());
	}

}
