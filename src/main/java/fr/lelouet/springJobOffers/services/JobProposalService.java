package fr.lelouet.springJobOffers.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.repositories.JobProposalRepository;

@Service
public class JobProposalService {

	@Autowired
	private JobProposalRepository jobProposalRepository;

	public List<JobProposal> getAll() {
		return jobProposalRepository.findAll();
	}

	public Iterable<JobProposal> listByCode() {
		return jobProposalRepository.findAllByOrderByCodeAsc();
	}

	public Optional<JobProposal> getById(Long id) {
		return jobProposalRepository.findById(id);
	}

	public Optional<JobProposal> getByCode(String code) {
		return jobProposalRepository.findByCode(code);
	}

	public ResponseEntity<JobProposal> createOrUpdate(Long id, JobProposal jobProposal) {
		Optional<JobProposal> existing = jobProposalRepository.findById(id);
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
		Optional<JobProposal> existing = jobProposalRepository.findByCode(code);
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

	public JobProposal save(JobProposal jobProposal) {
		if (jobProposal.getId() == null) {
			jobProposal.setCreatedDate(Instant.now());
		}
		jobProposal.setUpdatedDate(Instant.now());
		return jobProposalRepository.save(jobProposal);
	}

	public void delete(JobProposal jobProposal) {
		jobProposalRepository.delete(jobProposal);
	}

}
