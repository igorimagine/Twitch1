package practice.twitch1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import practice.twitch1.main.TwitchProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = TwitchProperties.class)
public class Twitch1Application {

	public static void main(String[] args) {
		SpringApplication.run(Twitch1Application.class, args);
	}

}
