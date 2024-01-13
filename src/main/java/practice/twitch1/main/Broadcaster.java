package practice.twitch1.main;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Broadcaster(String broadcasterId, String broadcasterLogin, String broadcasterName, String followedAt) {
}
