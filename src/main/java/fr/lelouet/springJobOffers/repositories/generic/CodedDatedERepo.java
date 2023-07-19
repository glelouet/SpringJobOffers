package fr.lelouet.springJobOffers.repositories.generic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import fr.lelouet.springJobOffers.model.generic.CodedDatedEntity;

@NoRepositoryBean
public interface CodedDatedERepo<T extends CodedDatedEntity> extends JpaRepository<T, Long> {

	Optional<T> findByCode(String code);

	Long deleteByCode(String code);

	List<T> findAllByOrderByCodeAsc();

}
