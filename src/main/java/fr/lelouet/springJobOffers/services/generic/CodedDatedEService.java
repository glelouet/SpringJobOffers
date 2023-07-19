package fr.lelouet.springJobOffers.services.generic;

import java.util.List;
import java.util.Optional;

import fr.lelouet.springJobOffers.model.generic.CodedDatedEntity;
import fr.lelouet.springJobOffers.repositories.generic.CodedDatedERepo;
import jakarta.transaction.Transactional;
import lombok.Getter;

@Getter
public abstract class CodedDatedEService<T extends CodedDatedEntity, U extends CodedDatedERepo<T>>
extends DatedEService<T, U> {

	public List<T> allByCode() {
		return getRepository().findAllByOrderByCodeAsc();
	}
	public Optional<T> getByCode(String code) {
		return getRepository().findByCode(code);
	}

	@Transactional
	public void deleteByCode(String code) {
		getRepository().deleteByCode(code);
	}

}
