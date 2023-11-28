package com.whatsapp.backend.chat;

import java.util.List;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.user.User;

public interface ChatService {

	public Chat createChat(User reqUser, Long userId) throws UserException;

	public Chat findChatById(Long chatId) throws ChatException;

	public List<Chat> findAllChatByUserId(Long userId) throws UserException;

	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

	public Chat addUserToGroup(Long userId, Long chatId, User reqUser) throws UserException, ChatException;

	public Chat renameGroup(Long chatId, String groupName, User reqUser) throws UserException, ChatException;

	public Chat removeUserFromGroup(Long chatId, Long userId, User reqUser)
			throws UserException, ChatException;

	public void deleteChat(Long chatId, Long userId) throws UserException, ChatException;

}
