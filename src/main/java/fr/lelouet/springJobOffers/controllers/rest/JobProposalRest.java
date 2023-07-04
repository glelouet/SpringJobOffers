package fr.lelouet.springJobOffers.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.services.JobProposalService;

@RestController
@RequestMapping("/api/jobproposal")
public class JobProposalRest {

	@Autowired
	private JobProposalService jobProposalService;

	@GetMapping("")
	List<JobProposal> all() {
		return jobProposalService.getAll();
	}

	@GetMapping("/byid/{id}")
	Optional<JobProposal> bydId(@PathVariable long id) {
		return jobProposalService.getById(id);
	}

	@GetMapping("/bycode/{code}")
	Optional<JobProposal> byCode(@PathVariable String code) {
		return jobProposalService.getByCode(code);
	}

	@PostMapping("")
	ResponseEntity<?> createOrUpdateByCode(@RequestBody JobProposal jobProposal) {
		if (jobProposal.getId() != null) {
			long id = jobProposal.getId();
			return jobProposalService.createOrUpdate(id, jobProposal);
		} else if (jobProposal.getCode() != null) {
			String code = jobProposal.getCode();
			return jobProposalService.createOrUpdate(code, jobProposal);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("missing id and code");
		}
	}

}
