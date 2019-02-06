package com.aldogrand.kfc.pollingmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "source_runners")
public class SourceRunner {

    @Id
    private Long id;

    @ManyToOne
    private Connector connector;

    @ManyToOne
    @JoinColumn(name = "source_market", nullable = false)
    private SourceMarket sourceMarket;

    @Column(name = "source_id")
    private String sourceId;
    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "runner_status")
    private String status;
    private String type;
    private String side;
    private Double handicap;

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

    public SourceMarket getSourceMarket() {
        return sourceMarket;
    }

    public void setSourceMarket(SourceMarket sourceMarket) {
        this.sourceMarket = sourceMarket;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Double getHandicap() {
        return handicap;
    }

    public void setHandicap(Double handicap) {
        this.handicap = handicap;
    }
}
