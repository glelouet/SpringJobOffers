package fr.lelouet.springJobOffers.controllers.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.lelouet.springJobOffers.model.dto.messages.MailMessageOutDTO;
import fr.lelouet.springJobOffers.services.MailMessageService;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/mail")
public class MailMessageRest {

	@Autowired
	MailMessageService service;

	@PostMapping("/eml")
	ResponseEntity<String> addEML(@NotNull @RequestBody String eml) {
		String error = service.addEML(eml);
		if (error != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		} else {
			return ResponseEntity.ok(null);
		}
	}

	@GetMapping("")
	List<MailMessageOutDTO> listAll(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false)	Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) Boolean desc){
		var sort = Sort.by(sortBy);
		sort = desc == Boolean.TRUE ? sort.descending() : sort.ascending();
		ModelMapper map = new ModelMapper();
		map.getConfiguration().setSkipNullEnabled(true);
		if (pageSize != null || pageNo != null) {
			return service.all(PageRequest.of(pageNo == null ? 0 : pageNo, pageSize == null ? 50 : pageSize, sort))
					.stream().map(c -> map.map(c, MailMessageOutDTO.class))
					.collect(Collectors.toList());
		} else {
			return service.all(sort)
					.stream().map(c -> map.map(c, MailMessageOutDTO.class))
					.collect(Collectors.toList());
		}
	}

}
