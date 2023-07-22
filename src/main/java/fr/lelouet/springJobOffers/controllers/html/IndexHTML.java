package fr.lelouet.springJobOffers.controllers.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexHTML {

	@GetMapping("/")
	public ModelAndView index() {
		var modelAndView = new ModelAndView("index");
		return modelAndView;
	}

}
