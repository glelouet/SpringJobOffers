package fr.lelouet.springJobOffers.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.Company;
import fr.lelouet.springJobOffers.repositories.CompanyRepository;
import fr.lelouet.springJobOffers.services.generic.CodedDatedEService;

@Service
public class CompanyService extends CodedDatedEService<Company, CompanyRepository> {

	public ResponseEntity<?> createOrUpdate(Company data) {
		if (data.getId() != null) {
			Optional<Company> optData = getRepository().findById(data.getId());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			Company existing = optData.get();
			existing.update(data);
			return ResponseEntity.ok(save(existing));
		}
		if (data.getId() == null) {
			data.setCreatedDate(Instant.now());
		}
		data.setUpdatedDate(Instant.now());
		return ResponseEntity.ok(save(data));
	}

	public List<Company> searchName(String str) {
		return getRepository().findByNameContainingIgnoreCase(str);
	}

}
