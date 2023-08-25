package com.whatsapp.backend.service;

import java.util.List;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.MessageException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.SendMessageRequest;

public interface MessageService {
	
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatMessages(Long chatId, User reqUser) throws UserException, ChatException;
	
	public Message findMessageById(Long messageId) throws MessageException;
	
	public void deleteMessage(Long messageId, User reqUser) throws UserException, MessageException;

}
