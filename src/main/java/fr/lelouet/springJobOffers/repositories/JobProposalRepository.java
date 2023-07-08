package fr.lelouet.springJobOffers.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.model.JobProposal;

@Repository
public interface JobProposalRepository extends CodedDatedERepo<JobProposal> {

	List<JobProposal> findAllByProposingCompany(Company c);

}
