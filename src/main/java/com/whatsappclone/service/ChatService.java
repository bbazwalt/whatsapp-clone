package com.whatsappclone.service;

import java.util.List;

import com.whatsappclone.exception.ChatException;
import com.whatsappclone.exception.UserException;
import com.whatsappclone.model.Chat;
import com.whatsappclone.model.User;
import com.whatsappclone.request.GroupChatRequest;

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
