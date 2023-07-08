package fr.lelouet.springJobOffers.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class JobProposal extends CodedDatedEntity {

	@Size(min = 0, max = 255)
	private String description;

	@Size(min = 0, max = 255)
	private String enterprise;

	public void update(JobProposal source) {
		if (source.getCode() != null) {
			setCode(source.getCode());
		}
		if (source.getDescription() != null) {
			setDescription(source.getDescription());
		}
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Builder.Default
	private List<Company> proposingCompany = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@Builder.Default
	private List<Contact> proposingContact = new ArrayList<>();


}
