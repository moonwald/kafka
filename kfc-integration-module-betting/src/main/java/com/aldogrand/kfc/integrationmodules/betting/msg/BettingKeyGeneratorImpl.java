package com.aldogrand.kfc.integrationmodules.betting.msg;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.messaging.Message;

import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.CreateEventCommand;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.CreateMarket;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.CreateResultCommand;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.UpdateEventCommand;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.UpdateMarket;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.integrationmodules.betting.msg.events.BettingUpdategramReceivedEvent;
import com.aldogrand.kfc.integrationmodules.betting.msg.interfaces.BettingKeyGenerator;
import com.aldogrand.kfc.integrationmodules.betting.services.XMLToKFCEventTransformer;

/**
 * 
 * <p>
 * <b>Title</b> BetgeniusKeyGeneratorImpl
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 *
 */
public class BettingKeyGeneratorImpl implements BettingKeyGenerator {
    
  private Logger logger = LogManager.getLogger(getClass());
  
  private Properties keyDefinitions;
  
  @Autowired
  private XMLToKFCEventTransformer xmlToKFCEventTransformer;

  private ExpressionParser parser = new SpelExpressionParser();
    
  public BettingKeyGeneratorImpl() {
    ApplicationContext context = new ClassPathXmlApplicationContext("kafka-generator-context.xml");
    this.keyDefinitions = (Properties) context.getBean("keyDefinitions");
  }

  public BettingKeyGeneratorImpl(Properties property) {
    this.keyDefinitions = property;
  }

  /**
   * Generates the partition Key<br/>
   * 
   * @param message
   * @return null
   */
  @Override
  public String generateKey(Message message) throws ParseException, EvaluationException,
      NumberFormatException, RuntimeException {
    // Calculate the partition
    String key = "";

    // Read and Process the regular expression
    EvaluationContext context = new StandardEvaluationContext();
    context.setVariable("message", message);
    String contentType = message.getHeaders().get("contentType").toString();
    Expression exp = parser.parseExpression(keyDefinitions.getProperty(contentType));
    key = String.valueOf(exp.getValue(context));

    return key;
  }


  /*
   * (non-Javadoc)
   * @see com.aldogrand.kfc.integrationmodules.betgenius.msg.interfaces.BetgeniusKeyGenerator#generateKey(java.lang.String, java.lang.String)
   */
  @Override
  public String generateKey(String message, String contentType) throws ParseException, EvaluationException,
      NumberFormatException, RuntimeException {
    BettingUpdategramReceivedEvent bgUpdategram = xmlToKFCEventTransformer.transform(message, contentType);
    
    if (bgUpdategram != null) {
      Updategram updategram = bgUpdategram.getUpdategram();
      
      CreateEventCommand createEventCommand = updategram.getCreateEventCommand();
      if (createEventCommand != null) {
        return createEventCommand.getEvent().getId().toString();
      }
      
      UpdateEventCommand updateEventCommand = updategram.getUpdateEventCommand();
      if (updateEventCommand != null) {
        return updateEventCommand.getEvent().getId().toString();
      }
      
      List<CreateMarket> createMarkets = updategram.getCreateMarkets();
      if (createMarkets != null && !createMarkets.isEmpty()) {
        CreateMarket market = createMarkets.get(0);
        return market.getMarket().getEventId().toString();
      }
      
      List<UpdateMarket> updateMarkets = updategram.getUpdateMarkets();
      if (updateMarkets != null && !updateMarkets.isEmpty()) {
        UpdateMarket market = updateMarkets.get(0);
        return market.getMarket().getEventId().toString();
      }
      
      CreateResultCommand resultCommand = updategram.getCreateResultCommand();
      if (resultCommand != null) {
        return resultCommand.getMarket().getEventId().toString();
      }
      
    }
    logger.debug("No Key generated from XML content: " + message);
    
    return null;
  }
  
  public Properties getKeyDefinitions() {
    return keyDefinitions;
  }

  public void setKeyDefinitions(Properties keyDefinitions) {
    this.keyDefinitions = keyDefinitions;
  }

  public XMLToKFCEventTransformer getXmlToKFCEventTransformer() {
    return xmlToKFCEventTransformer;
  }

  public void setXmlToKFCEventTransformer(XMLToKFCEventTransformer xmlToKFCEventTransformer) {
    this.xmlToKFCEventTransformer = xmlToKFCEventTransformer;
  }

}
