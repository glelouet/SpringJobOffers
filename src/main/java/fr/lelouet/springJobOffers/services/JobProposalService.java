package fr.lelouet.springJobOffers.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.repositories.JobProposalRepository;

@Service
public class JobProposalService {

	@Autowired
	private JobProposalRepository jobProposalRepository;

	public Iterable<JobProposal> getAll() {
		return jobProposalRepository.findAll();
	}

	public Iterable<JobProposal> listByCode() {
		return jobProposalRepository.findAllByOrderByCodeAsc();
	}

	public Optional<JobProposal> getById(Long id) {
		return jobProposalRepository.findById(id);
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
