package com.aldogrand.kfc.msg.events.integration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.connectors.model.Offer;


@JsonTypeName("SUBMIT_OFFER_COMMAND")
public class SubmitOfferCommand extends ContentCommand<Offer> {

    @JsonCreator
    public SubmitOfferCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("content") Offer content) {
        super(type, connection, content);
    }

}
