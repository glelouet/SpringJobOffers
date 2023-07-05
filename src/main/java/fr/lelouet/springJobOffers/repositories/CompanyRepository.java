package fr.lelouet.springJobOffers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.lelouet.springJobOffers.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByNameContainingIgnoreCase(String name);

	Optional<Company> findByCode(String code);

	void deleteByCode(String code);

}
