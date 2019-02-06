package com.aldogrand.kfc.pollingmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "source_markets")
public class SourceMarket {
    // UNIQUE KEY uq_connector_source_id_market (connector,source_id,market),

    @Id
    private Long id;

    @ManyToOne
    private Connector connector;

    @ManyToOne
    @JoinColumn(name = "source_event", nullable = false)
    private SourceEvent sourceEvent;

    @Column(name = "source_id")
    private String sourceId;
    @Column(name = "source_name")
    private String sourceName;
    private String type;
    private String period;
    private Double handicap;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getHandicap() {
        return handicap;
    }

    public void setHandicap(Double handicap) {
        this.handicap = handicap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SourceEvent getSourceEvent() {
        return sourceEvent;
    }

    public void setSourceEvent(SourceEvent sourceEvent) {
        this.sourceEvent = sourceEvent;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
