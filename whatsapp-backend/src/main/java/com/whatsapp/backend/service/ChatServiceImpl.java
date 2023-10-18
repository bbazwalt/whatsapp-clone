package com.whatsapp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.ChatRepository;
import com.whatsapp.backend.request.GroupChatRequest;

@Service
public class ChatServiceImpl implements ChatService{

	private ChatRepository chatRepository;
	private UserService userService;
	
	public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
		super();
		this.userService=userService;
		this.chatRepository = chatRepository;
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
        chat.setIs_group(false);
        return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Long chatId) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);
		if(chat.isPresent()){
			return chat.get();
		}
		throw new ChatException("Chat not found with the id"+chatId);
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
		group.setIs_group(true);
		group.setChat_image(req.getChatImage());
		group.setChatName(req.getChatName());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);
		for(Long userId : req.getUserIds()) {
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}
		return chatRepository.save(group);
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
		throw new ChatException("Chat not found with the id"+chatId);
	}

	@Override
	public Chat renameGroup(Long chatId, String groupName, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			if(chat.getUsers().contains(reqUser)) {
				chat.setChatName(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("Only group members can rename this group");			
		}
		throw new ChatException("Chat not found with the id"+chatId);
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
		throw new ChatException("Chat not found with the id"+chatId);
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
