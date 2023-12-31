package fr.lelouet.springJobOffers.controllers.rest;

import java.util.Collections;
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

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.model.dto.CodedEntityDTO;
import fr.lelouet.springJobOffers.model.dto.JobProposalOutDTO;
import fr.lelouet.springJobOffers.services.CompanyService;
import fr.lelouet.springJobOffers.services.JobProposalService;

@RestController
@RequestMapping("/api/jobproposal")
public class JobProposalRest {

	@Autowired
	private JobProposalService service;

	@Autowired
	private CompanyService companyService;

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

	@GetMapping("/bycompany/{code}")
	List<JobProposalOutDTO> byProposingCompanyCode(@PathVariable String code) {
		Optional<Company> opt = companyService.getByCode(code);
		if (opt == null || opt.isEmpty()) {
			return Collections.emptyList();
		}
		ModelMapper map = new ModelMapper();
		map.getConfiguration().setSkipNullEnabled(true);
		return service.findByProposingCompany(opt.get())
				.stream()
				.map(c -> map.map(c, JobProposalOutDTO.class))
				.collect(Collectors.toList());
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

	@GetMapping("/bycode/{code}/proposingcompany")
	ResponseEntity<?> proposingCompaniesByCode(@PathVariable String code) {
		return proposingCompanies(service.getByCode(code));
	}

	ResponseEntity<?> proposingCompanies(Optional<JobProposal> opt) {
		if (opt == null || opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("job proposal not found");
		}
		JobProposal jp = opt.get();
		ModelMapper map = new ModelMapper();
		return ResponseEntity.ok(jp.getProposingCompany()
				.stream()
				.map(cp -> map.map(cp, CodedEntityDTO.class))
				.collect(Collectors.toList()));
	}

	@GetMapping("/bycode/{code}/proposingcontacts")
	ResponseEntity<?> proposingContactsByCode(@PathVariable String code) {
		return proposingContacts(service.getByCode(code));
	}

	ResponseEntity<?> proposingContacts(Optional<JobProposal> opt) {
		if (opt == null || opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("job proposal not found");
		}
		JobProposal jp = opt.get();
		ModelMapper map = new ModelMapper();
		return ResponseEntity.ok(jp.getProposingContact()
				.stream()
				.map(cp -> map.map(cp, CodedEntityDTO.class))
				.collect(Collectors.toList()));
	}

}
