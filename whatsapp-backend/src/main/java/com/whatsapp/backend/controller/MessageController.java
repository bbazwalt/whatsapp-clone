package com.whatsapp.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.MessageException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.SendMessageRequest;
import com.whatsapp.backend.response.ApiResponse;
import com.whatsapp.backend.service.MessageService;
import com.whatsapp.backend.service.UserService;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

	private MessageService messageService;
	private UserService userService;
	
	public MessageController() {
		super();
	}

	public MessageController(MessageService messageService, UserService userService) {
		super();
		this.messageService = messageService;
		this.userService = userService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User user = userService.findUserProfile(jwt);
		req.setUserId(user.getId());;
		Message message = messageService.sendMessage(req);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable Long chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		User user = userService.findUserProfile(jwt);
		List<Message> messages = messageService.getChatMessages(chatId, user);
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}
	
	@DeleteMapping("/chat/{chatId}")
	public ResponseEntity<ApiResponse> deleteMessagesHandler(@PathVariable Long messageId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException{
		User user = userService.findUserProfile(jwt);
		messageService.deleteMessage(messageId, user);
		ApiResponse res = new ApiResponse("Message deleted successfully",true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}
	
}
