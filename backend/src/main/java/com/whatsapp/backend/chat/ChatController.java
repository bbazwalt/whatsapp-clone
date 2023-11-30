package com.whatsapp.backend.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.shared.ApiResponse;
import com.whatsapp.backend.user.User;
import com.whatsapp.backend.user.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/v1/chats")
@NoArgsConstructor
@AllArgsConstructor
public class ChatController {

	@Autowired
	ChatService chatService;

	@Autowired
	UserService userService;

	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, req.getUserId());
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(req, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> createGroupHandler(@PathVariable Long chatId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		Chat chat = chatService.findChatById(chatId);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
			throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
		return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
	}

	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Long chatId, @PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Long chatId, @PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.removeUserFromGroup(userId, chatId, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Long chatId, @PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		chatService.deleteChat(chatId, reqUser.getId());
		ApiResponse res = new ApiResponse("Chat deleted successfully", true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
