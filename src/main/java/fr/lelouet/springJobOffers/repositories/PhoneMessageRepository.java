package fr.lelouet.springJobOffers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.lelouet.springJobOffers.model.messages.PhoneMessage;

public interface PhoneMessageRepository extends JpaRepository<PhoneMessage, Long> {

}
