package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <b>Title</b> MappingResponse
 * </p>
 * <p>
 * <b>Description</b> An object used to provide information about how particular
 * SBPC data maps to data from a {@link BettingPlatform}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@XmlRootElement(name = "mapping")
public class MappingResponse implements Serializable
{
    protected static abstract class Mapping implements Serializable
    {
        private static final long serialVersionUID = -8599750941598555376L;

        protected Long id;
        protected String connectorsId;

        /**
         * @return the id
         */
        @XmlAttribute
        public Long getId()
        {
            return id;
        }
        
        /**
         * @param id the id to set
         */
        public void setId(Long id)
        {
            this.id = id;
        }

        /**
         * @return the connectorsId
         */
        @XmlAttribute(name = "connectors-id")
        public String getConnectorsId()
        {
            return connectorsId;
        }

        /**
         * @param connectorsId the connectorsId to set
         */
        public void setConnectorsId(String connectorsId)
        {
            this.connectorsId = connectorsId;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((connectorsId == null) ? 0 : connectorsId.hashCode());
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            Mapping other = (Mapping) obj;
            if (connectorsId == null)
            {
                if (other.connectorsId != null)
                {
                    return false;
                }
            }
            else if (!connectorsId.equals(other.connectorsId))
            {
                return false;
            }
            if (id == null)
            {
                if (other.id != null)
                {
                    return false;
                }
            }
            else if (!id.equals(other.id))
            {
                return false;
            }
            return true;
        }
    }

    public static class EventMapping extends Mapping
    {
        private static final long serialVersionUID = -8784855524438189797L;

        private List<MarketMapping> markets = new ArrayList<MarketMapping>();

        /**
         * @return the markets
         */
        @XmlElementWrapper(name = "markets")
        @XmlElement(name = "market")
        public List<MarketMapping> getMarkets()
        {
            return markets;
        }

        /**
         * @param markets the markets to set
         */
        public void setMarkets(List<MarketMapping> markets)
        {
            this.markets = markets;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("EventMapping [id=");
            builder.append(id);
            builder.append(", connectorsId=");
            builder.append(connectorsId);
            builder.append("]");
            return builder.toString();
        }
    }
    
    public static class MarketMapping extends Mapping
    {
        private static final long serialVersionUID = 6750605837009755785L;

        private List<RunnerMapping> runners = new ArrayList<RunnerMapping>();

        /**
         * @return the runners
         */
        @XmlElementWrapper(name = "runners")
        @XmlElement(name = "runner")
        public List<RunnerMapping> getRunners()
        {
            return runners;
        }

        /**
         * @param runners the runners to set
         */
        public void setRunners(List<RunnerMapping> runners)
        {
            this.runners = runners;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("MarketMapping [id=");
            builder.append(id);
            builder.append(", connectorsId=");
            builder.append(connectorsId);
            builder.append("]");
            return builder.toString();
        }
    }
    
    public static class RunnerMapping extends Mapping
    {
        private static final long serialVersionUID = -3249474878901376534L;

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("RunnerMapping [id=");
            builder.append(id);
            builder.append(", connectorsId=");
            builder.append(connectorsId);
            builder.append("]");
            return builder.toString();
        }
    }
    
    private static final long serialVersionUID = 8340174420867483234L;

    private Long connectorId;
    private String connectorName;
    private List<EventMapping> events = new ArrayList<EventMapping>();
    
    /**
     * @return the connectorId
     */
    @XmlElement(name = "connector-id")
    public Long getConnectorId()
    {
        return connectorId;
    }

    /**
     * @param connectorId the connectorId to set
     */
    public void setConnectorId(Long connectorId)
    {
        this.connectorId = connectorId;
    }

    /**
     * @return the connectorName
     */
    @XmlElement(name = "connector-name")
    public String getConnectorName()
    {
        return connectorName;
    }

    /**
     * @param connectorName the connectorName to set
     */
    public void setConnectorName(String connectorName)
    {
        this.connectorName = connectorName;
    }

    /**
     * @return the events
     */
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    public List<EventMapping> getEvents()
    {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<EventMapping> events)
    {
        this.events = events;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MappingResponse [connectorId=");
        builder.append(connectorId);
        builder.append(", connectorName=");
        builder.append(connectorName);
        builder.append(", events=");
        builder.append(events);
        builder.append("]");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((connectorId == null) ? 0 : connectorId.hashCode());
        result = prime * result
                + ((connectorName == null) ? 0 : connectorName.hashCode());
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        MappingResponse other = (MappingResponse) obj;
        if (connectorId == null)
        {
            if (other.connectorId != null)
            {
                return false;
            }
        }
        else if (!connectorId.equals(other.connectorId))
        {
            return false;
        }
        if (connectorName == null)
        {
            if (other.connectorName != null)
            {
                return false;
            }
        }
        else if (!connectorName.equals(other.connectorName))
        {
            return false;
        }
        if (events == null)
        {
            if (other.events != null)
            {
                return false;
            }
        }
        else if (!events.equals(other.events))
        {
            return false;
        }
        return true;
    }
}
