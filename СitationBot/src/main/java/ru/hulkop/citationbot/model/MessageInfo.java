package ru.hulkop.citationbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageInfo {

  @JsonProperty("peer_id")
  private int peerId;
  @JsonProperty("text")
  private String text;

  public int getPeerId() {
    return peerId;
  }

  public String getText() {
    return text;
  }
}
