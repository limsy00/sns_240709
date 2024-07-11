package com.sns.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping("/sign-up-view")
	public String signUpView() {
		return "layout/defaultLayout";
	}

}
