package com.fitness.activityservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {

	private final ActivityRepository repo;
	private final UserValidationService userValidationService;
	
	public ActivityResponse trackActivity(ActivityRequest request) {
		
		boolean isValidUser = userValidationService.validateUser(request.getUserId());
		if (!isValidUser) throw new RuntimeException("Invalid User: " + request.getUserId());
		
		Activity activity = Activity.builder()
				.userId(request.getUserId())
				.type(request.getType())
				.duration(request.getDuration())
				.caloriesBurned(request.getCaloriesBurned())
				.startTime(request.getStartTime())
				.additionalMetrics(request.getAdditionalMetrics())
				.build();
		Activity savedActivity = repo.save(activity);
		return mapToResponse(savedActivity);
	}
	
	private ActivityResponse mapToResponse(Activity activity) {
		ActivityResponse response = new ActivityResponse();
		response.setId(activity.getId());
		response.setUserId(activity.getUserId());
		response.setType(activity.getType());
		response.setDuration(activity.getDuration());
		response.setCaloriesBurned(activity.getCaloriesBurned());
		response.setStartTime(activity.getStartTime());
		response.setAdditionalMetrics(activity.getAdditionalMetrics());
		response.setCreatedAt(activity.getCreatedAt());
		response.setUpdatedAt(activity.getUpdatedAt());
		return response;
	}

	public List<ActivityResponse> getUserActivities(String userId) {
		List<Activity> activities = repo.findByUserId(userId);
		return activities.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public ActivityResponse getActivityById(String activityId) {
		return repo.findById(activityId)
				.map(this::mapToResponse)
				.orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));
	}
	
}
