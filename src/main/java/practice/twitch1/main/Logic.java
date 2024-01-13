package practice.twitch1.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class Logic {

    private final TwitchProperties properties;
    private final ObjectMapper objectMapper;

    public Logic(TwitchProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    void logic() {
        log.info("--- START ---");
        log.info("ClientID: {}", properties.clientId());
        //postForToken2();
        getUser();

        final var userId = "93098598";
        //getFollowedChannels(userId);
        log.info("--- END ---");
    }

    @PostConstruct
    void logic2() {
        log.info("--- START ---");
        
        log.info("--- END ---");
    }

//    private TwitchToken postForToken1() {
//        final var rest = RestClient.create();
//        final var uri = "https://id.twitch.tv/oauth2/token";
//        final var body = String.format("client_id=%s&client_secret=%s&grant_type=client_credentials", properties.clientId(), properties.clientSecret());
//        final var post = rest.post().uri(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).body(body).retrieve().body(TwitchToken.class);
//        log.info("post: {}", post);
//        return post;
//    }

    private void postForToken2() {
        final var rest = RestClient.create();
        final var uri = "https://id.twitch.tv/oauth2/token";
        final var body = String.format("client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s", properties.clientId(), properties.clientSecret(), properties.clientCode(), properties.appRedirectUri());
        final var post = rest.post().uri(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).body(body).retrieve().body(String.class);
        log.info("post: {}", post);
    }

    private void getUser() {
        final var rest = RestClient.create();
        //final var uri = "https://api.twitch.tv/helix/users?login=twitchdev";
        final var uri = "https://api.twitch.tv/helix/users?login=igorimagine2";
        final var user = rest.get().uri(uri).header("Authorization", "Bearer " + properties.userToken()).header("Client-Id", properties.clientId()).accept(MediaType.APPLICATION_JSON).retrieve().toEntity(String.class);
        log.info("user: {}", user);
    }

//    private void getFollowedChannels(String userId) {
//        final var rest = RestClient.create();
//        final var uri = "https://api.twitch.tv/helix/channels/followed?user_id=" + userId;
//        final var followed = rest.get().uri(uri).header("Authorization", "Bearer " + properties.userToken()).header("Client-Id", properties.clientId()).accept(MediaType.APPLICATION_JSON).retrieve().body(String.class);
//        log.info(followed);
//    }

    private void getFollowedChannels(String userId) {
        final var rest = RestClient.create();
        final var after = "";
        final var uri = "https://api.twitch.tv/helix/channels/followed?first=100&user_id=" + userId + "&after=" + after;
        final var followed = rest.get().uri(uri).header("Authorization", "Bearer " + properties.userToken()).header("Client-Id", properties.clientId())
                .accept(MediaType.APPLICATION_JSON).retrieve().body(Followed.class);
        final var filename = (LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".json").replace(":", "_").replace("+", ":");
        try (var pw = new PrintWriter(filename)) {
            final var followedJson = objectMapper.writeValueAsString(followed);
            log.info("followedJson: {}", followedJson);
            pw.println(followedJson);
            pw.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
