package fr.lelouet.springJobOffers.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.repositories.JobProposalRepository;
import jakarta.transaction.Transactional;

@Service
public class JobProposalService {

	@Autowired
	private JobProposalRepository repository;

	public List<JobProposal> all(Pageable paging) {
		return paging == null ? repository.findAll() : repository.findAll(paging).getContent();
	}

	public Iterable<JobProposal> listByCode() {
		return repository.findAllByOrderByCodeAsc();
	}

	public Optional<JobProposal> getById(Long id) {
		return repository.findById(id);
	}

	public Optional<JobProposal> getByCode(String code) {
		return repository.findByCode(code);
	}

	public ResponseEntity<JobProposal> createOrUpdate(Long id, JobProposal jobProposal) {
		Optional<JobProposal> existing = repository.findById(id);
		if (existing == null || existing.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			JobProposal updated = existing.get();
			updated.update(jobProposal);
			save(updated);
			return ResponseEntity.ok(updated);
		}
	}

	public ResponseEntity<JobProposal> createOrUpdate(String code, JobProposal jobProposal) {
		Optional<JobProposal> existing = repository.findByCode(code);
		if (existing == null || existing.isEmpty()) {
			save(jobProposal);
			return ResponseEntity.ok(jobProposal);
		} else {
			JobProposal updated = existing.get();
			updated.update(jobProposal);
			save(updated);
			return ResponseEntity.ok(updated);
		}
	}

	public JobProposal save(JobProposal data) {
		if (data.getId() == null) {
			data.setCreatedDate(Instant.now());
		}
		data.setUpdatedDate(Instant.now());
		return repository.save(data);
	}

	@Transactional
	public void delete(JobProposal data) {
		repository.delete(data);
	}

	@Transactional
	public void deleteByCode(String code) {
		repository.deleteByCode(code);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
