package com.aldogrand.kfc.integrationmodules.betting.msg.interfaces;

import com.aldogrand.kfc.msg.interfaces.KeyGenerator;

/**
 * <p>
 * <b>Title</b> KeyGenerator Interface
 * </p>
 * <p>
 * <b>Description</b> Betgenius partition Key Generator for Kafka Interface.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public interface BettingKeyGenerator extends KeyGenerator {

  /**
   * Generate Key searching the most important value inside raw XML from Betgenius
   * 
   * @param message Raw XML from Betgenius
   * @param contentType Value of content-type header to parse the XML
   * @return Key value
   *
   */
  public String generateKey(String message, String contentType);
}
