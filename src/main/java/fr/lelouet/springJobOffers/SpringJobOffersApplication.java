package fr.lelouet.springJobOffers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.services.JobProposalService;

@SpringBootApplication
public class SpringJobOffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJobOffersApplication.class, args);
	}

	boolean extrastart = false;
	boolean addData = true;

	@Autowired
	private JobProposalService jobProposalService;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			if (extrastart) {
				System.out.println("Let's inspect the beans provided by Spring Boot:");

				String[] beanNames = ctx.getBeanDefinitionNames();
				Arrays.sort(beanNames);
				for (String beanName : beanNames) {
					System.out.println(beanName);
				}
			}
			if (addData) {
				for (int i = 0; i < 100; i++) {
					var jobProposal = new JobProposal();
					jobProposal.setCode("jp" + i);
					jobProposal.setDescription("Job Proposal " + i);
					jobProposalService.save(jobProposal);
				}
			}


		};
	}

}
