package com.aldogrand.kfc.pollingmanager.model;

import java.util.concurrent.TimeUnit;

import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;

public class EventAttributesMother {
    
    public static final String EVENT_ID = "285028";
    public static final String MARKET_ID = "1065916";
    public static final String RUNNER_ID = "1881858";
    public static final String MATCHBOOK_MODULE_ID = "1";
    public static final String MATCHBOOK_MODULE_NAME = "Matchbook";
    public static final String SESSION_TOKEN = "token";
    public static final String MATCHBOOK_BASE_URL = "https://beta01.xcl.ie/bpapi/rest";
    public static final String THREEET_MODULE_ID = "2";
    public static final String THREEET_MODULE_NAME = "ThreeEt";
    public static final String THREEET_BASE_URL = "http://rose.xcl.ie/mercury/api";

    public static EventAttributes createMatchbookAllEventsAttributes() {
        EventAttributes eventAttributes = createAllEventAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createThreeetAllEventsAttributes() {
        EventAttributes eventAttributes = createAllEventAttributes();
        setThreeetBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createMatchbookOneEventAttributes() {
        EventAttributes eventAttributes = createOneEventEventAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createThreeetOneEventAttributes() {
        EventAttributes eventAttributes = createOneEventEventAttributes();
        setThreeetBaseAttributes(eventAttributes);
        return eventAttributes;
    }

    public static EventAttributes createMatchbookAllMarketsAttributes() {
        EventAttributes eventAttributes = createAllMarketsAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createMatchbookOneMarketAttributes() {
        EventAttributes eventAttributes = createOneMarketEventAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createMatchbookAllRunnersAttributes() {
        EventAttributes eventAttributes = createAllRunnersAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createMatchbookOneRunnerAttributes() {
        EventAttributes eventAttributes = createOneRunnerEventAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }
    
    public static EventAttributes createMatchbookAllPricesAttributes() {
        EventAttributes eventAttributes = createAllPricesAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }

    public static EventAttributes createMatchbookOnePriceAttributes() {
        EventAttributes eventAttributes = createOnePriceEventAttributes();
        setMatchbookBaseAttributes(eventAttributes);
        return eventAttributes;
    }

    public static EventAttributes createMatchbookAllPositionsAttributes() {
        EventAttributes positionAttributes = new EventAttributes();
        Rule allPositionsRule = new Rule(RuleType.ALL, DataType.POSITION, new Duration(1, TimeUnit.HOURS));
        positionAttributes.setRule(allPositionsRule);
        setMatchbookBaseAttributes(positionAttributes);
        return positionAttributes;
    }
    
    private static EventAttributes createAllEventAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule allEventsRule = new Rule(RuleType.ALL, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(allEventsRule);
        return eventAttributes;
    }
    
    private static EventAttributes createOneEventEventAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule oneEventRule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(oneEventRule);
        eventAttributes.setEventId(EVENT_ID);
        return eventAttributes;
    }
    
    private static EventAttributes createAllMarketsAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule allMarketsRule = new Rule(RuleType.ALL, DataType.MARKET, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(allMarketsRule);
        eventAttributes.setEventId(EVENT_ID);
        return eventAttributes;
    }
    
    private static EventAttributes createOneMarketEventAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule oneMarketRule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(oneMarketRule);
        eventAttributes.setEventId(EVENT_ID);
        eventAttributes.setMarketId(MARKET_ID);
        return eventAttributes;
    }
    
    private static EventAttributes createAllRunnersAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule allRunnersRule = new Rule(RuleType.ALL, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(allRunnersRule);
        eventAttributes.setEventId(EVENT_ID);
        eventAttributes.setMarketId(MARKET_ID);
        return eventAttributes;
    }

    private static EventAttributes createOneRunnerEventAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule oneRunnerRule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(oneRunnerRule);
        eventAttributes.setEventId(EVENT_ID);
        eventAttributes.setMarketId(MARKET_ID);
        eventAttributes.setRunnerId(RUNNER_ID);
        return eventAttributes;
    }

    private static EventAttributes createAllPricesAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule allPricesRule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(allPricesRule);
        eventAttributes.setEventId(EVENT_ID);
        eventAttributes.setMarketId(MARKET_ID);
        return eventAttributes;
    }

    private static EventAttributes createOnePriceEventAttributes() {
        EventAttributes eventAttributes = new EventAttributes();
        Rule onePriceRule = new Rule(RuleType.REFRESH, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        eventAttributes.setRule(onePriceRule);
        eventAttributes.setEventId(EVENT_ID);
        eventAttributes.setMarketId(MARKET_ID);
        return eventAttributes;
    }


    private static void setMatchbookBaseAttributes(EventAttributes eventAttributes) {
        eventAttributes.setBaseUrl(MATCHBOOK_BASE_URL);
        eventAttributes.setIntegrationModuleId(MATCHBOOK_MODULE_ID);
        eventAttributes.setIntegrationModuleName(MATCHBOOK_MODULE_NAME);
        eventAttributes.setSessionToken(SESSION_TOKEN);
    }
    
    private static void setThreeetBaseAttributes(EventAttributes eventAttributes) {
        eventAttributes.setBaseUrl(THREEET_BASE_URL);
        eventAttributes.setIntegrationModuleId(THREEET_MODULE_ID);
        eventAttributes.setIntegrationModuleName(THREEET_MODULE_NAME);
        eventAttributes.setSessionToken(SESSION_TOKEN);
    }
}
