package com.aldogrand.kfc.pollingmanager.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meta_tags")
public class MetaTag {

    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private com.aldogrand.sbpc.connectors.model.MetaTag.Type type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.aldogrand.sbpc.connectors.model.MetaTag.Type getType() {
        return type;
    }

    public void setType(com.aldogrand.sbpc.connectors.model.MetaTag.Type type) {
        this.type = type;
    }

}
