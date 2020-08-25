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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
public class UserService {

	private final UserRepository repository;
	// private SequenceGeneratorService sequenceGenerator;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	public UserService(UserRepository repository, SequenceGeneratorService sequenceGenerator) {
		this.repository = repository;
		// this.sequenceGenerator = sequenceGenerator;
	}

	public List<User> getAllUsers() {
		return repository.findAll();
	}

	public Optional<User> getUserById() {
		return repository.findById("2");
	}

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), userDetails.getName(), userDetails.getBio(), roles));
	}

	public ResponseEntity<?> deleteUser(String id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Error: There is no user with ID " + id));
		repository.delete(user);
		return ResponseEntity.ok(new MessageResponse("User has been deleted"));
	}

	public ResponseEntity<?> saveNewUser(SignUpRequest signUpRequest) {
		// repository.save(user); // event listener will ensure that the ID is already
		// set before saving rn
		// return user;
		if (repository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (repository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		System.out.println(signUpRequest.getBio());
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getName(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getBio());
		System.out.println("Bio " + user.getBio());
		System.out.println("Username " + user.getUsername());
		System.out.println("Name: " + user.getName());
		System.out.println("Password " + user.getPassword());
		System.out.println("Email: " + user.getEmail());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						System.out.println("WE ARE HERE");
						System.out.println(roleRepository.findAll());
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		repository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	public ResponseEntity<?> updateUserProfile(UpdateUserProfileRequest updateRequest) {
		// repository.save(user); // event listener will ensure that the ID is already
		// set before saving rn
		// return user;

		System.out.println("Update Request");
		System.out.println("ID " + updateRequest.getId());
		System.out.println("Name " + updateRequest.getName());
		System.out.println("Username " + updateRequest.getUsername());
		System.out.println("Email " + updateRequest.getEmail());
		System.out.println("Bio " + updateRequest.getBio());

		User user = repository.findById(updateRequest.getId())
				.orElseThrow(() -> new RuntimeException("Error: No User with ID " + updateRequest.getId()));

		// only report if the user has changed their username
		if (!user.getUsername().equals(updateRequest.getUsername())
				&& repository.existsByUsername(updateRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		// only report if the user has changed their email
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

	public ResponseEntity<?> updateUserSecurity(UpdateUserSecurityRequest updateRequest) {
		// repository.save(user); // event listener will ensure that the ID is already
		// set before saving rn
		// return user;

		User user = repository.findById(updateRequest.getId())
				.orElseThrow(() -> new RuntimeException("Error: No User with ID " + updateRequest.getId()));

		if (!encoder.matches(updateRequest.getOldPassword(), user.getPassword())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Current password is incorrect"));
		}
		String newEncodedPassword = encoder.encode(updateRequest.getNewPassword());

		user.setPassword(newEncodedPassword);

		repository.save(user);

		return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
	}
}
