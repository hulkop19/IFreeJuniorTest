package ru.hulkop.citationbot.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CitationService {

  private final String rootUrl;
  private final String accessToken;
  private final String apiVersion;
  private final String societyName;

  private final RestTemplate restTemplate;

  @Autowired
  public CitationService(
      @Value("${vk.api.base}") String rootUrl,
      @Value("${vk.api.token}") String token,
      @Value("${vk.api.version}") String apiVersion,
      @Value("${societyName}") String societyName,
      RestTemplateBuilder restTemplateBuilder) {
    this.rootUrl = rootUrl;
    this.accessToken = token;
    this.apiVersion = apiVersion;
    this.societyName = societyName;
    this.restTemplate = restTemplateBuilder.rootUri(rootUrl).build();
  }

  public void cite(int userId, String text) {
    if (text == null) {
      return;
    }

    String message = createCitation(text);
    int randomId = (int) (Math.random() * Integer.MAX_VALUE);

    URI uri = UriComponentsBuilder.fromHttpUrl(rootUrl + "messages.send")
        .queryParam("peer_id", userId)
        .queryParam("random_id", randomId)
        .queryParam("message", message)
        .queryParam("access_token", accessToken)
        .queryParam("v", apiVersion)
        .build().encode()
        .toUri();

    restTemplate.getForObject(uri, Object.class);
  }

  private String createCitation(String message) {
    String citation = message;
    String botCall = "|" + societyName + "]";

    if (citation.contains(botCall)) {
      citation = citation.substring(message.indexOf(botCall) + botCall.length());
    }
    return citation;
  }
}