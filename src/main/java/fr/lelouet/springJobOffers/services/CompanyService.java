package fr.lelouet.springJobOffers.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.repositories.CompanyRepository;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repository;

	public List<Company> all() {
		return repository.findAll();
	}

	public List<Company> all(Pageable paging) {
		return paging == null ? all() : repository.findAll(paging).getContent();
	}

	public List<Company> all(Sort sort) {
		return sort == null ? all() : repository.findAll(sort);
	}

	public Optional<Company> getById(long id) {
		return repository.findById(id);
	}

	@Transactional
	public void delete(Company data) {
		repository.delete(data);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public ResponseEntity<?> createOrUpdate(Company data) {
		if (data.getId() != null) {
			Optional<Company> optData = repository.findById(data.getId());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			Company existing = optData.get();
			existing.update(data);
			return ResponseEntity.ok(save(existing));
		}
		if (data.getId() == null) {
			data.setCreatedDate(Instant.now());
		}
		data.setUpdatedDate(Instant.now());
		return ResponseEntity.ok(save(data));
	}

	public Company save(Company data) {
		if (data.getCode() == null) {
			data.setCode(data.getName().replaceAll("\\s+", "_").toUpperCase());
		}
		return repository.save(data);
	}

	public List<Company> searchName(String str) {
		return repository.findByNameContainingIgnoreCase(str);
	}

}
