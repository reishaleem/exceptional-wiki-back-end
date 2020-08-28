package com.exceptionaloutlining.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {

	private String message;

	private String idOfCreatedDocument;

	// Configuring this so that we can choose whether to send the ID of the newly
	// created Document that this Response would be triggered by, or just the
	// message, if we don't want to send an ID. We can maybe create separator DTOs
	// later...
	public MessageResponse(String message) {
		this.message = message;
	}
}