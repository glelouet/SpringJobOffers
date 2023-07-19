package fr.lelouet.springJobOffers.model.messages;

import java.time.Instant;

import fr.lelouet.springJobOffers.model.generic.DatedEntity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message extends DatedEntity {

	private Instant receiveDate;
	private Instant sentDate;

}
