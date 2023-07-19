package fr.lelouet.springJobOffers.model.generic;

import java.time.Instant;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class DatedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@CreatedDate
	private Instant createdDate;

	@UpdateTimestamp
	private Instant updatedDate;

	public void updateDates() {
		if (id == null) {
			createdDate = Instant.now();
		}
		updatedDate = Instant.now();
	}

}
