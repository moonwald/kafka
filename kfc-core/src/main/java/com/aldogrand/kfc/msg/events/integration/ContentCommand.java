package com.aldogrand.kfc.msg.events.integration;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentCommand<C> extends Command<C> {

    private final C content;

    @JsonCreator
    public ContentCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("content") C content) {
        super(type, connection);
        this.content = Validate.notNull(content, "Content is required");
    }

    public C getContent() {
        return content;
    }

    @Override
    public <T> void accept(CommandVisitor<C, T> visitor) {
        visitor.addContent(content);
    }

}
