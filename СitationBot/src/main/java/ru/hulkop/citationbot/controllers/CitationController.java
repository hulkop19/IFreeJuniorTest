package ru.hulkop.citationbot.controllers;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hulkop.citationbot.model.MessageInfo;
import ru.hulkop.citationbot.model.UserMessage;
import ru.hulkop.citationbot.service.CitationService;

@RestController
@RequestMapping("/api/v1")
public class CitationController {

  private final String confirmationCode;
  private final CitationService citationService;

  @Autowired
  public CitationController(
      @Value("${vk.api.confirmationCode}") String confirmationCode,
      CitationService citationServer) {
    this.confirmationCode = confirmationCode;
    this.citationService = citationServer;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> execute(@RequestBody @NotNull UserMessage message) {
    String type = message.getType();

    if ("confirmation".equals(type)) {
      return ResponseEntity.ok(confirmationCode);
    }

    if ("message_new".equals(type)) {
      MessageInfo info = message.getInfo();
      citationService.cite(info.getPeerId(), info.getText());
      return ResponseEntity.ok("ok");
    }

    return ResponseEntity.badRequest().body("error");
  }
}