package com.whatsapp.backend.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.whatsapp.backend.message.vm.MessageVM;
import com.whatsapp.backend.shared.ApiResponse;
import com.whatsapp.backend.user.User;
import com.whatsapp.backend.user.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("api/v1/messages")
@NoArgsConstructor
@AllArgsConstructor
public class MessageController {

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ResponseEntity<MessageVM> sendMessageHandler(@RequestBody SendMessageRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User user = userService.findUserProfile(jwt);
		req.setUserId(user.getId());
		;
		Message message = messageService.sendMessage(req);
		return new ResponseEntity<MessageVM>(new MessageVM(message), HttpStatus.OK);
	}

	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<MessageVM>> getChatMessagesHandler(@PathVariable Long chatId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User user = userService.findUserProfile(jwt);
		List<Message> messages = messageService.getChatMessages(chatId, user);
		return new ResponseEntity<List<MessageVM>>(toMessageVM(messages), HttpStatus.OK);
	}

	@DeleteMapping("/chat/{chatId}")
	public ResponseEntity<ApiResponse> deleteMessagesHandler(@PathVariable Long messageId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException {
		User user = userService.findUserProfile(jwt);
		messageService.deleteMessage(messageId, user);
		ApiResponse res = new ApiResponse("Message deleted successfully", true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

	private List<MessageVM> toMessageVM(List<Message> messages) {
		List<MessageVM> list = new ArrayList<>();
		for (Message message : messages) {
			list.add(new MessageVM(message));
		}
		return list;
	}

}
