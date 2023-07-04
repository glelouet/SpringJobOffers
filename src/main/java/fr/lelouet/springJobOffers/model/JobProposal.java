package fr.lelouet.springJobOffers.model;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Data
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

}
