package fr.lelouet.springJobOffers.controllers.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginHTML {

	@GetMapping("")
	public String loginHTML() {
		return "login/basic";
	}
}
