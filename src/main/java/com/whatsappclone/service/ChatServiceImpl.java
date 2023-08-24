package com.whatsappclone.service;

import java.util.List;
import java.util.Optional;

import com.whatsappclone.exception.ChatException;
import com.whatsappclone.exception.UserException;
import com.whatsappclone.model.Chat;
import com.whatsappclone.model.User;
import com.whatsappclone.repository.ChatRepository;
import com.whatsappclone.request.GroupChatRequest;

public class ChatServiceImpl implements ChatService{

	private ChatRepository chatRepository;
	private UserService userService;
	
	public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
		this.userService=userService;
		this.chatRepository = chatRepository;
	}

	public ChatRepository getChatRepository() {
		return chatRepository;
	}

	public void setChatRepository(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Chat createChat(User reqUser, Long userId) throws UserException {
        User user = userService.findUserById(userId);
        Chat isChatExists = chatRepository.findSingleChatByUserIds(user, reqUser);
        if(isChatExists!=null) {
        	return isChatExists;
        }
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);
		return chat;
	}

	@Override
	public Chat findChatById(Long chatId) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);
		if(chat.isPresent()){
			return chat.get();
		}
		throw new ChatException("No chat found with the id"+chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Long userId) throws UserException {
		User user = userService.findUserById(userId);
		List<Chat> chats = chatRepository.findChatByUserId(user.getId());
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
		Chat group = new Chat();
		group.setGroup(false);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);
		for(Long userId : req.getUserIds()) {
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}
		return group;
	}

	@Override
	public Chat addUserToGroup(Long userId, Long chatId, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);
				return chatRepository.save(chat);
			}
			throw new UserException("Only admins can add users to this group");
		}
		throw new ChatException("No chat found with the id"+chatId);
	}

	@Override
	public Chat renameGroup(Long chatId, String groupName, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getUsers().contains(reqUser)) {
				chat.setChat_name(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("Only group members can rename this group");			
		}
		throw new ChatException("No chat found with the id"+chatId);
	}

	@Override
	public Chat removeUserFromGroup(Long chatId, Long userId, User reqUser)
			throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);
		if(opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getAdmins().contains(reqUser)) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			}
			else if(chat.getUsers().contains(reqUser)) {
				if(user.getId().equals(reqUser.getId())) {
					chat.getUsers().remove(user);
					return chatRepository.save(chat);
				}
			}
			else {
				throw new UserException("Only admins can remove other users from this group");
			}
		}
		throw new ChatException("No chat found with the id"+chatId);
	}

	@Override
	public void deleteChat(Long chatId, Long userId) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		
		if(opt.isPresent()) {
			Chat chat = opt.get();
			chatRepository.deleteById(chat.getId());
		}
	}

}
