package fr.lelouet.springJobOffers.services.generic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.lelouet.springJobOffers.model.generic.DatedEntity;
import jakarta.transaction.Transactional;
import lombok.Getter;

@Getter
public class DatedEService<T extends DatedEntity, U extends JpaRepository<T, Long>> {

	@Autowired
	private U repository;

	public List<T> all() {
		return repository.findAll();
	}

	public List<T> all(Pageable paging) {
		return paging == null ? all() : repository.findAll(paging).getContent();
	}

	public List<T> all(Sort sort) {
		return sort == null ? all() : repository.findAll(sort);
	}

	public Optional<T> getById(long id) {
		return repository.findById(id);
	}

	@Transactional
	public void delete(T data) {
		repository.delete(data);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public T save(T data) {
		data.updateDates();
		return getRepository().save(data);
	}

}
