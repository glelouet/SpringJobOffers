package fr.lelouet.springJobOffers.model;

import java.io.Serializable;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class JobProposal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	@NotNull
	@Size(min = 1, max = 255)
	private String code;

	@Size(min = 0, max = 255)
	private String description;

	@Size(min = 0, max = 255)
	private String enterprise;

	@CreatedDate
	private Instant createdDate;

	@UpdateTimestamp
	private Instant updatedDate;

	public void update(JobProposal source) {
		if (source.getCode() != null) {
			code = source.getCode();
		}
		if (source.getDescription() != null) {
			description = source.getDescription();
		}
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Builder.Default
	private List<Company> proposingCompany = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@Builder.Default
	private List<Contact> proposingContact = new ArrayList<>();


}
