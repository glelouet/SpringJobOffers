package fr.lelouet.springJobOffers.controllers.html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.lelouet.springJobOffers.services.JobProposalService;

@Controller
public class IndexHTML {

	@Autowired
	private JobProposalService jobProposalService;

	@GetMapping("/")
	public ModelAndView index() {
		var modelAndView = new ModelAndView("index");
		modelAndView.addObject("jobProposalItems", jobProposalService.listByCode());
		return modelAndView;
	}

}
