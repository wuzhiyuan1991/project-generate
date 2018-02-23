package {mainpackage};

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@ImportResource({ "classpath:disconf.xml" })
@org.springframework.context.annotation.Import({com.ueb.core.common.CommonConfig.class })
public class {mainclass} implements CommandLineRunner {

	// private static final Logger logger =
	// LoggerFactory.getLogger({mainclass}.class);
	public static void main(String[] args) {
		SpringApplication.run({mainclass}.class, args);
	}
	
	public void run(String... args) throws Exception {
	}
}
