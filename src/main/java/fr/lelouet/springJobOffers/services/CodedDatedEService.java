package fr.lelouet.springJobOffers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import fr.lelouet.springJobOffers.model.CodedDatedEntity;
import fr.lelouet.springJobOffers.repositories.CodedDatedERepo;
import jakarta.transaction.Transactional;
import lombok.Getter;

@Getter
public abstract class CodedDatedEService<T extends CodedDatedEntity, U extends CodedDatedERepo<T>> {

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

	public List<T> allByCode() {
		return repository.findAllByOrderByCodeAsc();
	}

	public Optional<T> getById(long id) {
		return repository.findById(id);
	}

	public Optional<T> getByCode(String code) {
		return repository.findByCode(code);
	}

	@Transactional
	public void delete(T data) {
		repository.delete(data);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	public void deleteByCode(String code) {
		repository.deleteByCode(code);
	}

	public T save(T data) {
		data.updateDates();
		return getRepository().save(data);
	}

}
