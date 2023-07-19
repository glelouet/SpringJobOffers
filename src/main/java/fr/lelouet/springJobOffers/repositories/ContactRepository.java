package fr.lelouet.springJobOffers.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.lelouet.springJobOffers.model.Contact;
import fr.lelouet.springJobOffers.repositories.generic.CodedDatedERepo;

@Repository
public interface ContactRepository extends CodedDatedERepo<Contact> {

	List<Contact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

	List<Contact> findByPhoneNumbers(String number);

	List<Contact> findByMailAddresses(String mail);

}
