package com.aldogrand.kfc.msg.events.integration;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.connectors.model.Offer;


@JsonTypeName("CANCEL_OFFERS_COMMAND")
public class CancelOffersCommand extends FilterContentCommand<List<Offer>> {

    @JsonCreator
    public CancelOffersCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("content") List<Offer> content) {
        super(type, connection, content);
    }

}
