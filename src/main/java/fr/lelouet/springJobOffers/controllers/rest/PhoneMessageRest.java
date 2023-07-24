package fr.lelouet.springJobOffers.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lelouet.springJobOffers.model.dto.messages.PhoneMessageInDTO;
import fr.lelouet.springJobOffers.model.dto.messages.PhoneMessageOutDTO;
import fr.lelouet.springJobOffers.model.messages.PhoneMessage;
import fr.lelouet.springJobOffers.model.messages.PhoneMessage.PhoneMessageBuilder;
import fr.lelouet.springJobOffers.services.PhoneMessageService;
import fr.lelouet.springJobOffers.tools.PhoneNumberTools;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/phonemessage")
public class PhoneMessageRest {

	@Autowired
	PhoneMessageService service;

	@PostMapping("/list")
	ResponseEntity<?> addList(@NotNull @RequestBody List<PhoneMessageInDTO> messages) {
		List<PhoneMessageOutDTO> response = new ArrayList<>();
		ModelMapper map = new ModelMapper();
		for (PhoneMessageInDTO m : messages) {
			PhoneMessageBuilder<?, ?> builder = PhoneMessage.builder();
			if (m.getContent() != null) {
				builder.content(m.getContent());
			}
			if (m.getDate() != null) {
				builder.sentDate(m.getDate().toInstant());
			}
			if (m.getNumber() != null) {
				builder.number(PhoneNumberTools.standardizePhoneNumber(m.getNumber()));
			}
			PhoneMessage created = builder.build();
			service.save(created);
			response.add(map.map(created, PhoneMessageOutDTO.class));
		}
		return ResponseEntity.ok(response);
	}
}
