package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>
 * <b>Title</b> GFeedPrice
 * </p>
 * <p>
 * <b>Description</b> An object containing the GFeed prices along with all of
 * the other prices which contributed to the resulting G Feed prices.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * 
 * @author Cillian Kelly
 * @version 1.0
 */
public class GFeedPrice implements Serializable {

	private static final long serialVersionUID = -2552205685399360794L;

	/**
	 * <p>
	 * <b>Title</b> GFeedPriceBreakdown
	 * </p>
	 * <p>
	 * <b>Description</b> An object containing the breakdown of all prices
	 * involved in a GFeed price other prices which contributed to the resulting
	 * G Feed prices.
	 * </p>
	 * <p>
	 * <b>Company</b> AldoGrand Consultancy Ltd
	 * </p>
	 * <p>
	 * <b>Copyright</b> Copyright (c) 2013
	 * </p>
	 * 
	 * @author Cillian Kelly
	 * @version 1.0
	 */
	private GFeedPriceBreakdown gFeedPriceBreakdown;
	
	
	/**
	 * @return the gFeedPriceBreakdown
	 */
	@XmlElement(name = "gfeed-price")
	public GFeedPriceBreakdown getgFeedPriceBreakdown() {
		return gFeedPriceBreakdown;
	}

	/**
	 * @param gFeedPriceBreakdown the gFeedPriceBreakdown to set
	 */
	public void setgFeedPriceBreakdown(GFeedPriceBreakdown gFeedPriceBreakdown) {
		this.gFeedPriceBreakdown = gFeedPriceBreakdown;
	}

	public static class GFeedPriceBreakdown implements Serializable {
		private static final long serialVersionUID = -2335577960043832508L;

		private String eventName;
		private HandicapMarket source1handicapMarket1;
		private HandicapMarket source1handicapMarket2;
		private HandicapMarket source2handicapMarket1;
		private HandicapMarket source2handicapMarket2;
		private ThreeWayMarket asian1x2market;
		private ThreeWayMarket betfairMarket;
		private ThreeWayMarket gfeedMarket;
		/**
		 * @return the eventName
		 */
		@XmlElement(name = "event")
		public String getEventName() {
			return eventName;
		}
		/**
		 * @param eventName the eventName to set
		 */
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
		/**
		 * @return the source1handicapMarket1
		 */
		@XmlElement(name = "source1-handicap1")
		public HandicapMarket getSource1handicapMarket1() {
			return source1handicapMarket1;
		}
		/**
		 * @param source1handicapMarket1 the source1handicapMarket1 to set
		 */
		public void setSource1handicapMarket1(HandicapMarket source1handicapMarket1) {
			this.source1handicapMarket1 = source1handicapMarket1;
		}
		/**
		 * @return the source1handicapMarket2
		 */
		@XmlElement(name = "source1-handicap2")
		public HandicapMarket getSource1handicapMarket2() {
			return source1handicapMarket2;
		}
		/**
		 * @param source1handicapMarket2 the source1handicapMarket2 to set
		 */
		public void setSource1handicapMarket2(HandicapMarket source1handicapMarket2) {
			this.source1handicapMarket2 = source1handicapMarket2;
		}
		/**
		 * @return the source2handicapMarket1
		 */
		@XmlElement(name = "source2-handicap1")
		public HandicapMarket getSource2handicapMarket1() {
			return source2handicapMarket1;
		}
		/**
		 * @param source2handicapMarket1 the source2handicapMarket1 to set
		 */
		public void setSource2handicapMarket1(HandicapMarket source2handicapMarket1) {
			this.source2handicapMarket1 = source2handicapMarket1;
		}
		/**
		 * @return the source2handicapMarket2
		 */
		@XmlElement(name = "source2-handicap2")
		public HandicapMarket getSource2handicapMarket2() {
			return source2handicapMarket2;
		}
		/**
		 * @param source2handicapMarket2 the source2handicapMarket2 to set
		 */
		public void setSource2handicapMarket2(HandicapMarket source2handicapMarket2) {
			this.source2handicapMarket2 = source2handicapMarket2;
		}
		/**
		 * @return the asian1x2market
		 */
		@XmlElement(name = "asian-threeway")
		public ThreeWayMarket getAsian1x2market() {
			return asian1x2market;
		}
		/**
		 * @param asian1x2market the asian1x2market to set
		 */
		public void setAsian1x2market(ThreeWayMarket asian1x2market) {
			this.asian1x2market = asian1x2market;
		}
		/**
		 * @return the betfairMarket
		 */
		@XmlElement(name = "betfair-threeway")
		public ThreeWayMarket getBetfairMarket() {
			return betfairMarket;
		}
		/**
		 * @param betfairMarket the betfairMarket to set
		 */
		public void setBetfairMarket(ThreeWayMarket betfairMarket) {
			this.betfairMarket = betfairMarket;
		}
		/**
		 * @return the gfeedMarket
		 */
		@XmlElement(name = "gfeed-threeway")
		public ThreeWayMarket getGfeedMarket() {
			return gfeedMarket;
		}
		/**
		 * @param gfeedMarket the gfeedMarket to set
		 */
		public void setGfeedMarket(ThreeWayMarket gfeedMarket) {
			this.gfeedMarket = gfeedMarket;
		}
		
		

	}
	
	
	/**
	 * <p>
	 * <b>Title</b> HandicapMarket
	 * </p>
	 * <p>
	 * <b>Description</b> An object containing the handicap prices which contribute to a G Feed price
	 * </p>
	 * <p>
	 * <b>Company</b> AldoGrand Consultancy Ltd
	 * </p>
	 * <p>
	 * <b>Copyright</b> Copyright (c) 2013
	 * </p>
	 * 
	 * @author Cillian Kelly
	 * @version 1.0
	 */
	public static class HandicapMarket implements Serializable {

		private static final long serialVersionUID = 5612968061794536159L;
		
		private BigDecimal homePrice;
		private BigDecimal awayPrice;
		private String marketName;
		
		
		/**
		 * @return the marketName
		 */
		@XmlElement(name = "market")
		public String getMarketName() {
			return marketName;
		}
		/**
		 * @param marketName the marketName to set
		 */
		public void setMarketName(String marketName) {
			this.marketName = marketName;
		}
		/**
		 * @return the homePrice
		 */
		@XmlElement(name = "home")
		public BigDecimal getHomePrice() {
			return homePrice;
		}
		/**
		 * @param homePrice the homePrice to set
		 */
		public void setHomePrice(BigDecimal homePrice) {
			this.homePrice = homePrice;
		}
		/**
		 * @return the awayPrice
		 */
		@XmlElement(name = "away")
		public BigDecimal getAwayPrice() {
			return awayPrice;
		}
		/**
		 * @param awayPrice the awayPrice to set
		 */
		public void setAwayPrice(BigDecimal awayPrice) {
			this.awayPrice = awayPrice;
		}
		
	}
	
	/**
	 * <p>
	 * <b>Title</b> ThreeWayMarket
	 * </p>
	 * <p>
	 * <b>Description</b> An object containing the three way prices relevant to a G Feed price
	 * </p>
	 * <p>
	 * <b>Company</b> AldoGrand Consultancy Ltd
	 * </p>
	 * <p>
	 * <b>Copyright</b> Copyright (c) 2013
	 * </p>
	 * 
	 * @author Cillian Kelly
	 * @version 1.0
	 */
	public static class ThreeWayMarket implements Serializable {

		private static final long serialVersionUID = -8864645537818394810L;
		
		private String marketName;
		private BigDecimal homePrice;
		private BigDecimal drawPrice;
		private BigDecimal awayPrice;
		/**
		 * @return the marketName
		 */
		@XmlElement(name = "market")
		public String getMarketName() {
			return marketName;
		}
		/**
		 * @param marketName the marketName to set
		 */
		public void setMarketName(String marketName) {
			this.marketName = marketName;
		}
		/**
		 * @return the homePrice
		 */
		@XmlElement(name = "home")
		public BigDecimal getHomePrice() {
			return homePrice;
		}
		/**
		 * @param homePrice the homePrice to set
		 */
		public void setHomePrice(BigDecimal homePrice) {
			this.homePrice = homePrice;
		}
		/**
		 * @return the drawPrice
		 */
		@XmlElement(name = "draw")
		public BigDecimal getDrawPrice() {
			return drawPrice;
		}
		/**
		 * @param drawPrice the drawPrice to set
		 */
		public void setDrawPrice(BigDecimal drawPrice) {
			this.drawPrice = drawPrice;
		}
		/**
		 * @return the awayPrice
		 */
		@XmlElement(name = "away")
		public BigDecimal getAwayPrice() {
			return awayPrice;
		}
		/**
		 * @param awayPrice the awayPrice to set
		 */
		public void setAwayPrice(BigDecimal awayPrice) {
			this.awayPrice = awayPrice;
		}
		
	}

}
