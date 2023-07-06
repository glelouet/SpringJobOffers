package fr.lelouet.springJobOffers.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import fr.lelouet.springJobOffers.model.dto.CompanyOutDTO;
import fr.lelouet.springJobOffers.services.CompanyService;
import fr.lelouet.springJobOffers.services.JobProposalService;

@RestController
@RequestMapping("/api/company")
public class CompanyRest {

	@Autowired
	private CompanyService service;

	@Autowired
	private JobProposalService jobProposalService;

	@GetMapping("")
	List<CompanyOutDTO> all(
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
					.stream().map(c -> map.map(c, CompanyOutDTO.class))
					.collect(Collectors.toList());
		} else {
			return service.all(sort)
					.stream().map(c -> map.map(c, CompanyOutDTO.class))
					.collect(Collectors.toList());
		}
	}

	@GetMapping("/byid/{id}")
	Optional<Company> bydId(@PathVariable long id) {
		return service.getById(id);
	}

	@GetMapping("/search/name/{str}")
	List<Company> search(@PathVariable String str) {
		return service.searchName(str);
	}

	@GetMapping("/bycode/{code}/proposes")
	ResponseEntity<?> getProposesByCode(@PathVariable String code) {
		var opt = service.getByCode(code);
		if (opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("company not found");
		}
		var c = opt.get();
		ModelMapper map = new ModelMapper();
		return ResponseEntity.ok(c.getProposes()
				.stream().map(j -> map.map(j, CodedEntityDTO.class))
				.collect(Collectors.toList()));
	}

	@PostMapping("/bycode/{code}/proposes/{jobcode}")
	ResponseEntity<?> addProposalByCode(@PathVariable String code, @PathVariable String jobcode) {
		return addProposal(service.getByCode(code), jobProposalService.getByCode(jobcode));
	}

	ResponseEntity<?> addProposal(Optional<Company> opt1, Optional<JobProposal> opt2) {
		if (opt1.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("company not found");
		}
		if (opt2.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("job not found");
		}
		var cp = opt1.get();
		var jp = opt2.get();
		var prp = cp.getProposes();
		if (prp == null) {
			prp = new ArrayList<>();
			cp.setProposes(prp);
		}
		if (!prp.contains(jp)) {
			prp.add(jp);
			service.save(cp);
		}
		ModelMapper map = new ModelMapper();
		return ResponseEntity.ok(cp.getProposes()
				.stream().map(c -> map.map(c, CodedEntityDTO.class))
				.collect(Collectors.toList()));
	}

	@PostMapping("")
	ResponseEntity<?> createOrUpdate(@RequestBody Company data) {
		return service.createOrUpdate(data);
	}
}
