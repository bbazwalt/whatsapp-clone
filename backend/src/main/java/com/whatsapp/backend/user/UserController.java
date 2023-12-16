package com.whatsapp.backend.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.shared.ApiResponse;
import com.whatsapp.backend.user.vm.UserVM;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserVM> getUserProfileHandler(@RequestHeader("Authorization") String token)
			throws UserException {
		User user = userService.findUserProfile(token);
		return new ResponseEntity<UserVM>(new UserVM(user), HttpStatus.ACCEPTED);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserVM>> searchUserHandler(@RequestParam("name") String q) {
		List<User> users = userService.searchUser(q);
		return new ResponseEntity<List<UserVM>>(toUserVM(users), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,
			@RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		ApiResponse res = new ApiResponse("User updated successfully", true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
	}

	private List<UserVM> toUserVM(List<User> users) {
		List<UserVM> list = new ArrayList<>();
		for (User user : users) {
			list.add(new UserVM(user));
		}
		return list;
	}

}
