package fr.lelouet.springJobOffers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.lelouet.springJobOffers.model.JobProposal;

@Repository
public interface JobProposalRepository extends JpaRepository<JobProposal, Long> {

	List<JobProposal> findAllByOrderByCodeAsc();

	Optional<JobProposal> findByCode(String code);

	List<JobProposal> deleteByCode(String code);

}
