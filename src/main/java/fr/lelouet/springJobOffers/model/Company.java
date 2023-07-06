package fr.lelouet.springJobOffers.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	@NotNull
	@Size(min = 1, max = 255)
	private String code;

	private String name;

	@ManyToOne
	private Company mother;

	@CreatedDate
	private Instant createdDate;

	@UpdateTimestamp
	private Instant updatedDate;

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
