package fr.lelouet.springJobOffers.model.generic;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class CodedDatedEntity extends DatedEntity {


	@Column(unique = true)
	@NotNull
	@Size(min = 1, max = 255)
	private String code;
}
