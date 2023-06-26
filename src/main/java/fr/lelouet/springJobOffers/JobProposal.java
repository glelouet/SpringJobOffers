package fr.lelouet.springJobOffers;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class JobProposal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String code;

	@CreatedDate
	private Instant createdDate;

}
