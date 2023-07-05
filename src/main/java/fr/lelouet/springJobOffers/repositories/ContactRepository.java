package fr.lelouet.springJobOffers.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.lelouet.springJobOffers.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	List<Contact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

	List<Contact> findByPhoneNumbers(String number);

	List<Contact> findByMailAddresses(String mail);

}
