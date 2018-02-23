
package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.config.LoginConfig;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	LoginConfig loginConfig;

	@RequestMapping(method = RequestMethod.GET)
	public String login(String username, String password)
	{
		if (!loginConfig.getUsername().equals(username) || !loginConfig.getPassword().equals(password))
		{
			return "failure";
		}

		return "success";
	}
}
