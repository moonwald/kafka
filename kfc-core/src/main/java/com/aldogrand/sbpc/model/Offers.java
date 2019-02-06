package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> Offers
 * </p>
 * <p>
 * <b>Description</b> A container for {@link Offer}s that is used to return 
 * {@link Offer}s from submit or cancel operations. It also contains {@link Metric}s
 * about the operation.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@XmlRootElement(name = "offers")
@XmlType(propOrder = {"offers", "metrics"})
public class Offers implements Serializable
{
    /**
     * <p>
     * <b>Title</b> Metrics
     * </p>
     * <p>
     * <b>Description</b> Timing metrics for submit and canel operations.
     * </p>
     * <p>
     * <b>Company</b> AldoGrand Consultancy Ltd
     * </p>
     * <p>
     * <b>Copyright</b> Copyright (c) 2013
     * </p>
     * @author Aldo Grand
     * @version 1.0
     */
    @XmlType(propOrder = {"preConnectorTime", "connectorTime", "postConnectorTime", 
            "totalTime"})
    public static class Metrics implements Serializable
    {
        private static final long serialVersionUID = -5464986676116475467L;

        private Long preConnectorTime;
        private Long connectorTime;
        private Long postConnectorTime;
        private Long totalTime;

        /**
         * @return the preConnectorTime
         */
        @XmlElement(name = "pre-connector-time")
        public Long getPreConnectorTime()
        {
            return preConnectorTime;
        }

        /**
         * @param preConnectorTime the preConnectorTime to set
         */
        public void setPreConnectorTime(Long preConnectorTime)
        {
            this.preConnectorTime = preConnectorTime;
        }

        /**
         * @return the connectorTime
         */
        @XmlElement(name = "connector-time")
        public Long getConnectorTime()
        {
            return connectorTime;
        }

        /**
         * @param connectorTime the connectorTime to set
         */
        public void setConnectorTime(Long connectorTime)
        {
            this.connectorTime = connectorTime;
        }

        /**
         * @return the postConnectorTime
         */
        @XmlElement(name = "post-connector-time")
        public Long getPostConnectorTime()
        {
            return postConnectorTime;
        }

        /**
         * @param postConnectorTime the postConnectorTime to set
         */
        public void setPostConnectorTime(Long postConnectorTime)
        {
            this.postConnectorTime = postConnectorTime;
        }

        /**
         * @return the totalTime
         */
        @XmlElement(name = "total-time")
        public Long getTotalTime()
        {
            return totalTime;
        }

        /**
         * @param totalTime the totalTime to set
         */
        public void setTotalTime(Long totalTime)
        {
            this.totalTime = totalTime;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Metrics [preConnectorTime=");
            builder.append(preConnectorTime);
            builder.append(", connectorTime=");
            builder.append(connectorTime);
            builder.append(", postConnectorTime=");
            builder.append(postConnectorTime);
            builder.append(", totalTime=");
            builder.append(totalTime);
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
                    + ((connectorTime == null) ? 0 : connectorTime.hashCode());
            result = prime
                    * result
                    + ((postConnectorTime == null) ? 0 : postConnectorTime
                            .hashCode());
            result = prime
                    * result
                    + ((preConnectorTime == null) ? 0 : preConnectorTime
                            .hashCode());
            result = prime * result
                    + ((totalTime == null) ? 0 : totalTime.hashCode());
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
            Metrics other = (Metrics) obj;
            if (connectorTime == null)
            {
                if (other.connectorTime != null)
                {
                    return false;
                }
            }
            else if (!connectorTime.equals(other.connectorTime))
            {
                return false;
            }
            if (postConnectorTime == null)
            {
                if (other.postConnectorTime != null)
                {
                    return false;
                }
            }
            else if (!postConnectorTime.equals(other.postConnectorTime))
            {
                return false;
            }
            if (preConnectorTime == null)
            {
                if (other.preConnectorTime != null)
                {
                    return false;
                }
            }
            else if (!preConnectorTime.equals(other.preConnectorTime))
            {
                return false;
            }
            if (totalTime == null)
            {
                if (other.totalTime != null)
                {
                    return false;
                }
            }
            else if (!totalTime.equals(other.totalTime))
            {
                return false;
            }
            return true;
        }
    }
    
    private static final long serialVersionUID = -5970274491591775224L;

    private List<Offer> offers;
    private Metrics metrics;

    /**
     * @return the offers
     */
    @XmlElementWrapper(name = "offers")
    @XmlElement(name = "offer")
    public List<Offer> getOffers()
    {
        return offers;
    }

    /**
     * @param offers the offers to set
     */
    public void setOffers(List<Offer> offers)
    {
        this.offers = offers;
    }

    /**
     * @return the metrics
     */
    @XmlElement(name = "operation-metrics")
    public Metrics getMetrics()
    {
        return metrics;
    }

    /**
     * @param metrics the metrics to set
     */
    public void setMetrics(Metrics metrics)
    {
        this.metrics = metrics;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Offers [offers=");
        builder.append(offers);
        builder.append(", metrics=");
        builder.append(metrics);
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
        result = prime * result + ((metrics == null) ? 0 : metrics.hashCode());
        result = prime * result + ((offers == null) ? 0 : offers.hashCode());
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
        Offers other = (Offers) obj;
        if (metrics == null)
        {
            if (other.metrics != null)
            {
                return false;
            }
        }
        else if (!metrics.equals(other.metrics))
        {
            return false;
        }
        if (offers == null)
        {
            if (other.offers != null)
            {
                return false;
            }
        }
        else if (!offers.equals(other.offers))
        {
            return false;
        }
        return true;
    }
}
