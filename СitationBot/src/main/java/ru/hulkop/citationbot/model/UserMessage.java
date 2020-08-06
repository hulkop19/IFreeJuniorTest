package ru.hulkop.citationbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMessage {

  @JsonProperty("type")
  private String type;
  @JsonProperty("object")
  private MessageInfo info;

  public String getType() {
    return type;
  }

  public MessageInfo getInfo() {
    return info;
  }
}