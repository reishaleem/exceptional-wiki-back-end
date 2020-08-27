package com.exceptionaloutlining.app.services;

import com.exceptionaloutlining.app.models.ERole;
import com.exceptionaloutlining.app.models.Role;
import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.payload.request.LoginRequest;
import com.exceptionaloutlining.app.payload.request.SignUpRequest;
import com.exceptionaloutlining.app.payload.request.UpdateUserProfileRequest;
import com.exceptionaloutlining.app.payload.request.UpdateUserSecurityRequest;
import com.exceptionaloutlining.app.payload.response.JwtResponse;
import com.exceptionaloutlining.app.payload.response.MessageResponse;
import com.exceptionaloutlining.app.repositories.RoleRepository;
import com.exceptionaloutlining.app.repositories.UserRepository;
import com.exceptionaloutlining.app.security.jwt.JwtUtils;
import com.exceptionaloutlining.app.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
public class UserService {

	private final UserRepository repository;
	// private final UniverseService universeService;
	AuthenticationManager authenticationManager;
	RoleRepository roleRepository;
	PasswordEncoder encoder;
	JwtUtils jwtUtils;

	@Autowired
	public UserService(UserRepository repository, UniverseService universeService,
			AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder encoder,
			JwtUtils jwtUtils) {
		this.repository = repository;
		// this.universeService = universeService;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;

	}

	public List<User> getAllUsers() {
		return repository.findAll();
	}

	public User getUserById(String id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Error: There is no user with ID " + id));
	}

	public ResponseEntity<?> getUniverseList(String id) {
		return null; // we need to create a new UniverseResponse or something, similar to a JWT
						// response...
	}

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getName(), roles));
	}

	public ResponseEntity<?> saveNewUser(SignUpRequest signUpRequest) {

		if (repository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (repository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getName(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getBio());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles); // do we even need roles anymore...i don't think so...

		repository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	public ResponseEntity<?> updateUserProfile(UpdateUserProfileRequest updateRequest, String id) {

		User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: No User with ID " + id));

		// only report if the user has changed their username, since otherwise it will
		// conflict with their current username
		if (!user.getUsername().equals(updateRequest.getUsername())
				&& repository.existsByUsername(updateRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		// only report if the user has changed their email, since otherwise it will
		// conflict with their current email
		if (!user.getEmail().equals(updateRequest.getEmail()) && repository.existsByEmail(updateRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		user.setName(updateRequest.getName());
		user.setUsername(updateRequest.getUsername());
		user.setEmail(updateRequest.getEmail());
		user.setBio(updateRequest.getBio());

		repository.save(user);

		return ResponseEntity.ok(new MessageResponse("Profile updated successfully!"));
	}

	public ResponseEntity<?> updateUserSecurity(UpdateUserSecurityRequest updateRequest, String id) {

		User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: No User with ID " + id));

		// make sure their entered password matches the saved password
		if (!encoder.matches(updateRequest.getOldPassword(), user.getPassword())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Current password is incorrect"));
		}

		String newEncodedPassword = encoder.encode(updateRequest.getNewPassword());
		user.setPassword(newEncodedPassword);

		repository.save(user);

		return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
	}

	public ResponseEntity<?> deleteUser(String id) {

		User user = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Error: There is no user with ID " + id));

		repository.delete(user);

		return ResponseEntity.ok(new MessageResponse("User has been deleted"));
	}
}
