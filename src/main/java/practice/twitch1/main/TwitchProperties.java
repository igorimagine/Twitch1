package practice.twitch1.main;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "twitch")
public record TwitchProperties(String clientId, String clientSecret, String clientCode, String appRedirectUri, String userToken) {
}
