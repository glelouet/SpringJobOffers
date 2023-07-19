package fr.lelouet.springJobOffers.model.dto;

import java.util.List;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CompanyOutDTO extends CodedEntityDTO {

	private String name;

	@ManyToOne
	private CodedEntityDTO mother;

	private List<CodedEntityDTO> proposes;

}
