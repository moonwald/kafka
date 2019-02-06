package com.aldogrand.kfc.integrationmodules.betgenius.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.kfc.integrationmodules.betting.msg.interfaces.BettingKeyGenerator;

/**
 * 
 * <p>
 * <b>Title</b> BetgeniusKeyGeneratorImplTest
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/kfc-integration-module-betgenius-test.xml" })
public class BettingKeyGeneratorImplTest {

  private final String KEY_GENERATOR_FILE_SAMPLE = "bgKeyGenerator-sample.xml";  
  private final String KEY_GENERATOR_FILE_SAMPLE_UPDATE_EVENT = "updateEvent-01.xml";
  private final String KEY_GENERATOR_FILE_SAMPLE_UPDATE_MARKET = "updateMarket-01.xml";
  private final String KEY_GENERATOR_FILE_SAMPLE_CREATE_RESULT = "createResult-01.xml";
  private final String TESTCASES_PATH = "/src/test/resources/testcases/";
  private final String BG_CONTENT_TYPE = "BETGENIUS_UPDATEGRAM_RAW";
  
  @Autowired
  private BettingKeyGenerator betgeniusKeyGenerator;
  
  @Test
  public void testKeyGenerationFromFullXML() {
    String filePath = new File("").getAbsolutePath();
    Path path = FileSystems.getDefault().getPath(filePath + TESTCASES_PATH + KEY_GENERATOR_FILE_SAMPLE);
    File file = path.toFile();
    
    String key = null;
    InputStream is;
    try {
      is = new FileInputStream(file);      
      String content = getStringFromInputStream(is);      
      key = betgeniusKeyGenerator.generateKey(content, BG_CONTENT_TYPE);      
    } catch (FileNotFoundException e) {
      fail("File not found");
    }    
    
    assertNotNull(key);
    assertEquals(key, "807491");
  }
  
  @Test
  public void testKeyGenerationUpdateEvent() {
    String filePath = new File("").getAbsolutePath();
    Path path = FileSystems.getDefault().getPath(filePath + TESTCASES_PATH + KEY_GENERATOR_FILE_SAMPLE_UPDATE_EVENT);
    File file = path.toFile();
    
    String key = null;
    InputStream is;
    try {
      is = new FileInputStream(file);      
      String content = getStringFromInputStream(is);      
      key = betgeniusKeyGenerator.generateKey(content, BG_CONTENT_TYPE);      
    } catch (FileNotFoundException e) {
      fail("File not found");
    }    
    
    assertNotNull(key);
    assertEquals(key, "2811461");
  }
  
  @Test
  public void testKeyGenerationUpdateMarket() {
    String filePath = new File("").getAbsolutePath();
    Path path = FileSystems.getDefault().getPath(filePath + TESTCASES_PATH + KEY_GENERATOR_FILE_SAMPLE_UPDATE_MARKET);
    File file = path.toFile();
    
    String key = null;
    InputStream is;
    try {
      is = new FileInputStream(file);      
      String content = getStringFromInputStream(is);      
      key = betgeniusKeyGenerator.generateKey(content, BG_CONTENT_TYPE);      
    } catch (FileNotFoundException e) {
      fail("File not found");
    }    
    
    assertNotNull(key);
    assertEquals(key, "90090220");
  }
  
  @Test
  public void testKeyGenerationCreateResult() {
    String filePath = new File("").getAbsolutePath();
    Path path = FileSystems.getDefault().getPath(filePath + TESTCASES_PATH + KEY_GENERATOR_FILE_SAMPLE_CREATE_RESULT);
    File file = path.toFile();
    
    String key = null;
    InputStream is;
    try {
      is = new FileInputStream(file);      
      String content = getStringFromInputStream(is);      
      key = betgeniusKeyGenerator.generateKey(content, BG_CONTENT_TYPE);      
    } catch (FileNotFoundException e) {
      fail("File not found");
    }    
    
    assertNotNull(key);
    assertEquals(key, "589632");
  }
  
  //convert InputStream to String
  private static String getStringFromInputStream(InputStream is) {

      BufferedReader br = null;
      StringBuilder sb = new StringBuilder();

      String line;
      try {

          br = new BufferedReader(new InputStreamReader(is));
          while ((line = br.readLine()) != null) {
              sb.append(line);
          }

      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          if (br != null) {
              try {
                  br.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }

      return sb.toString();
  }

}
