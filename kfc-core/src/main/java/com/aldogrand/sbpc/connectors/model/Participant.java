package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;

/**
 * Base class for sport participant types
 * 
 * @author aldogrand
 *
 */
public abstract class Participant implements Serializable {

	private static final long serialVersionUID = 5294171485070848861L;

	private String id;
	private String name;
	
	
	/**
	 * Retrieves the id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Retrieves the name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


	@Override
    public boolean equals(Object obj)
    {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Participant other = (Participant) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
    }
	
	
}
