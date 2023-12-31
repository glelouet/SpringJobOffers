package fr.lelouet.springJobOffers.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.repositories.generic.CodedDatedERepo;

@Repository
public interface CompanyRepository extends CodedDatedERepo<Company> {

	List<Company> findByNameContainingIgnoreCase(String name);

}
