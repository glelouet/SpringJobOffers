package fr.lelouet.springJobOffers.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.lelouet.springJobOffers.model.JobProposal;
import fr.lelouet.springJobOffers.services.JobProposalService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/jobproposal")
public class JobProposalController {

	@Autowired
	private JobProposalService jobProposalService;

	@GetMapping("create")
	public String create(JobProposal jobProposal) {
		return "jobproposal/creation";
	}

	@PostMapping("create")
	public String create(@Valid JobProposal proposal, BindingResult result, Model model) {
		JobProposal creation = new JobProposal();
		creation.setDescription(proposal.getDescription());
		creation.setCode(proposal.getCode());
		jobProposalService.save(creation);
		return "redirect:/";
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable Long id, Model model) {
		var existing = jobProposalService.getById(id).orElse(null);
		if (existing != null) {
			jobProposalService.delete(existing);
		}
		return "redirect:/";
	}

	@GetMapping("{id}")
	public String edit(@PathVariable Long id, Model model) {
		var existing = jobProposalService.getById(id).orElse(null);
		if (existing != null) {
			model.addAttribute("jobProposal", existing);
			return "jobproposal/edition";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("{id}")
	public String edit(@PathVariable Long id, @Valid JobProposal jobProposal) {
		var existing = jobProposalService.getById(id).orElse(null);
		if (existing != null) {
			existing.setCode(jobProposal.getCode());
			existing.setDescription(jobProposal.getDescription());
			existing.setUpdatedDate(Instant.now());
			jobProposalService.save(existing);
		}
		return "redirect:/";
	}

}
