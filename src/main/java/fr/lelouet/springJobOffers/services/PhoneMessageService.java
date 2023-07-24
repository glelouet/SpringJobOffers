package fr.lelouet.springJobOffers.services;

import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.messages.PhoneMessage;
import fr.lelouet.springJobOffers.repositories.PhoneMessageRepository;
import fr.lelouet.springJobOffers.services.generic.DatedEService;

@Service
public class PhoneMessageService extends DatedEService<PhoneMessage, PhoneMessageRepository> {

}
