package fr.lelouet.springJobOffers.model;

import java.util.ArrayList;
import java.util.List;

import fr.lelouet.springJobOffers.model.generic.CodedDatedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Company extends CodedDatedEntity {

	private String name;

	@ManyToOne
	private Company mother;

	@ManyToMany(mappedBy = "proposingCompany", fetch = FetchType.EAGER)
	@Builder.Default
	private List<JobProposal> proposes = new ArrayList<>();

	public void update(Company data) {
		if (data.mother != null) {
			mother = data.mother;
		}
		if (data.name != null) {
			name = data.name;
		}
	}

}
