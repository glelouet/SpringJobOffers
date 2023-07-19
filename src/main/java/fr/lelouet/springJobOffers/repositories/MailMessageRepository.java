package fr.lelouet.springJobOffers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.lelouet.springJobOffers.model.messages.MailMessage;

public interface MailMessageRepository extends JpaRepository<MailMessage, Long> {

}
