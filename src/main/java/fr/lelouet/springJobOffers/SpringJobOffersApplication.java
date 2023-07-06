package fr.lelouet.springJobOffers;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.lelouet.springJobOffers.model.Contact;
import fr.lelouet.springJobOffers.model.Contact.Title;
import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.services.ContactService;
import fr.lelouet.springJobOffers.services.JobProposalService;

@SpringBootApplication
public class SpringJobOffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJobOffersApplication.class, args);
	}

	boolean addData = true;
	@Bean
	public CommandLineRunner initDB(JobProposalService jobProposalService,
			ContactService contactService) {
		return args -> {
			if (addData) {
				// add job proposals
				for (int i = 0; i < 5; i++) {
					jobProposalService.save(JobProposal.builder()
							.code("jp" + i)
							.description("Job Proposal " + i)
							.enterprise("e_" + i)
							.build());
				}

				// add contact
				contactService.save(Contact.builder()
						.code("JEAN_DUJEAN")
						.title(Title.Mr)
						.firstName("jean")
						.lastName("dujean")
						.mailAddresses(List.of("jeand@dujean.com"))
						.phoneNumbers(List.of("0199000101"))
						.build());
				contactService.save(Contact.builder()
						.code("MARC_LAVOINE")
						.title(Title.Mr)
						.firstName("Marc")
						.lastName("l'avoine")
						.mailAddresses(List.of("marc@lavoine.com"))
						.phoneNumbers(List.of("0261913712"))
						.build());
				contactService.save(Contact.builder()
						.code("URSU_LA")
						.title(Title.Ms)
						.firstName("Ursulle")
						.lastName("dékøé")
						.mailAddresses(List.of("ursulla@dekoe.com"))
						.phoneNumbers(List.of("0353019885"))
						.build());
			}
		};
	}

}
