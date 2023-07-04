package fr.lelouet.springJobOffers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.services.JobProposalService;

@SpringBootApplication
public class SpringJobOffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJobOffersApplication.class, args);
	}

	boolean addData = true;
	@Bean
	public CommandLineRunner initDB(JobProposalService jobProposalService) {
		return args -> {
			if (addData) {
				for (int i = 0; i < 5; i++) {
					var jobProposal = new JobProposal();
					jobProposal.setCode("jp" + i);
					jobProposal.setDescription("Job Proposal " + i);
					jobProposalService.save(jobProposal);
				}
			}
		};
	}

}
