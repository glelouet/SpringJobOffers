package fr.lelouet.springJobOffers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.repositories.JobProposalRepository;
import fr.lelouet.springJobOffers.services.generic.CodedDatedEService;

@Service
public class JobProposalService extends CodedDatedEService<JobProposal, JobProposalRepository> {

	public ResponseEntity<JobProposal> createOrUpdate(Long id, JobProposal jobProposal) {
		Optional<JobProposal> existing = getRepository().findById(id);
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
		Optional<JobProposal> existing = getRepository().findByCode(code);
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

	public List<JobProposal> findByProposingCompany(Company c) {
		return getRepository().findAllByProposingCompany(c);
	}
}
