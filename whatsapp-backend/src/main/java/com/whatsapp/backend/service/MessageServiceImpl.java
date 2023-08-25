package com.whatsapp.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.MessageException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.MessageRepository;
import com.whatsapp.backend.request.SendMessageRequest;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	public MessageServiceImpl() {
		super();
	}

	public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ChatService chatService) {
		super();
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		return message;
	}

	@Override
	public List<Message> getChatMessages(Long chatId, User reqUser) throws UserException, ChatException {
		Chat chat = chatService.findChatById(chatId);
		if(!chat.getUsers().contains(reqUser)){
			throw new UserException("You are not related to this chat"+chat.getId());
		}
		List<Message> messages = messageRepository.findByChatId(chat.getId());
		return messages;
	}

	@Override
	public Message findMessageById(Long messageId) throws MessageException {
		Optional<Message> opt = messageRepository.findById(messageId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message not found with the id"+messageId);
	}

	@Override
	public void deleteMessage(Long messageId, User reqUser) throws UserException, MessageException {
		Message message = findMessageById(messageId);
		if(message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You can't delete another user's message"+reqUser.getFull_name());
	}

	public MessageRepository getMessageRepository() {
		return messageRepository;
	}

	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ChatService getChatService() {
		return chatService;
	}

	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
}
