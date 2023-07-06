package fr.lelouet.springJobOffers.controllers.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.model.dto.JobProposalOutDTO;
import fr.lelouet.springJobOffers.services.JobProposalService;

@RestController
@RequestMapping("/api/jobproposal")
public class JobProposalRest {

	@Autowired
	private JobProposalService service;

	@GetMapping("")
	List<JobProposalOutDTO> all(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) Boolean desc) {
		var sort = Sort.by(sortBy);
		sort = desc == Boolean.TRUE ? sort.descending() : sort.ascending();
		ModelMapper map = new ModelMapper();
		map.getConfiguration().setSkipNullEnabled(true);
		if (pageSize != null || pageNo != null) {
			return service.all(PageRequest.of(pageNo == null ? 0 : pageNo, pageSize == null ? 50 : pageSize, sort))
					.stream().map(c -> map.map(c, JobProposalOutDTO.class))
					.collect(Collectors.toList());
		} else {
			return service.all(sort)
					.stream().map(c -> map.map(c, JobProposalOutDTO.class))
					.collect(Collectors.toList());
		}
	}

	@GetMapping("/byid/{id}")
	Optional<JobProposal> bydId(@PathVariable long id) {
		return service.getById(id);
	}

	@GetMapping("/bycode/{code}")
	Optional<JobProposal> byCode(@PathVariable String code) {
		return service.getByCode(code);
	}

	@PostMapping("")
	ResponseEntity<?> createOrUpdateByCode(@RequestBody JobProposal jobProposal) {
		if (jobProposal.getId() != null) {
			long id = jobProposal.getId();
			return service.createOrUpdate(id, jobProposal);
		} else if (jobProposal.getCode() != null) {
			String code = jobProposal.getCode();
			return service.createOrUpdate(code, jobProposal);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("missing id and code");
		}
	}

	@DeleteMapping("/bycode/{code}")
	void deleteByCode(@PathVariable String code) {
		service.deleteByCode(code);
	}

	@DeleteMapping("/byid/{id}")
	void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}

}
