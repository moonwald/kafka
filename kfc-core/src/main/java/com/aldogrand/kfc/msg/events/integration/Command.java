package com.aldogrand.kfc.msg.events.integration;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.aldogrand.kfc.msg.events.KFCEvent;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type", defaultImpl = FilterCommand.class, visible = true)
@JsonSubTypes({ @Type(LoginCommand.class), @Type(SubmitOfferCommand.class), @Type(SubmitOffersCommand.class),
                @Type(CancelOfferCommand.class), @Type(CancelOffersCommand.class) })
@JsonInclude(Include.NON_NULL)
public abstract class Command<C> extends KFCEvent {

    private final CommandType type;
    private final Connection connection;
    private Context context; // TODO: make Context final, so the context of the new builder's ( from CommandBuilder.builder() ) doesn't
                             // point to the same context object

    @JsonProperty("session-token")
    private String sessionToken;

    Command(CommandType type, Connection connection) {
        super();
        this.type = Validate.notNull(type, "A commandType is required");
        this.connection = Validate.notNull(connection, "A connection is required");
    }

    Command(CommandType type, Connection connection, String sessionToken) {
        this(type, connection);
        this.setSessionToken(Validate.notBlank(sessionToken, "A sessionToken is required"));
    }

    public CommandBuilder builder() {
        return CommandBuilder.builder().integrationModuleId(getIntegrationModuleId()).integrationModuleName(getIntegrationModuleName())
                        .connection(connection).context(context).sessionToken(sessionToken);
    }

    public CommandType getType() {
        return type;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public abstract <T> void accept(CommandVisitor<C, T> visitor);

}
