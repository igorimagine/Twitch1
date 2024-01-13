package practice.twitch1.main;

import java.util.List;

public record Followed(Long total, List<Broadcaster> data, Pagination pagination) {
}
