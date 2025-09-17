package com.fitness.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
		return ResponseEntity.ok(userService.getUserProfile(userId));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> response(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(userService.register(request));
	}
}
