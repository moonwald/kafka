package com.aldogrand.kfc.msg.events.integration;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.connectors.model.Offer;


@JsonTypeName("SUBMIT_OFFERS_COMMAND")
public class SubmitOffersCommand extends ContentCommand<List<Offer>> {

    @JsonCreator
    public SubmitOffersCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("content") List<Offer> content) {
        super(type, connection, content);
    }
}
