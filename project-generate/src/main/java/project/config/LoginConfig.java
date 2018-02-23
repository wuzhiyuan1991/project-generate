
package project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Configuration
@Service
@Scope("singleton")
public class LoginConfig {

	@Value("${security.user.name}")
	private String username;

	@Value("${security.user.password}")
	private String password;

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

}
