package fr.lelouet.springJobOffers.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.services.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyRest {

	@Autowired
	private CompanyService service;

	@GetMapping("")
	List<Company> all(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) Boolean desc) {
		var sort = Sort.by(sortBy);
		sort = desc == Boolean.TRUE ? sort.descending() : sort.ascending();
		if (pageSize != null || pageNo != null) {
			return service.all(PageRequest.of(pageNo == null ? 0 : pageNo, pageSize == null ? 50 : pageSize, sort));
		} else {
			return service.all(sort);
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

}
