import static org.junit.Assert.*;

import javax.xml.transform.Source;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:kfc-integration-module-betgenius.xml", "kfc-integration-module-betgenius-test.xml" })
public class BetgeniusWebServiceTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	private MockWebServiceClient mockClient;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWsEndPointTest() throws Exception {
		Source requestPayload = new StringSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?><v1:TestServiceRequest xmlns:v1=\"http://mycompany.com/it/enterprise/contract/TestService/v1\" xmlns:v11=\"http://mycompany.com/it/enterprise/data/v1\"><v11:Document><v11:Id>101</v11:Id><v11:Type>MaterialMaster</v11:Type></v11:Document></v1:TestServiceRequest>");
		Source responsePayload = new StringSource("<testServiceResponseType xmlns=\"http://mycompany.com/it/enterprise/data/v1\" xmlns:ns2=\"http://mycompany.com/it/enterprise/contract/TestService/v1\"><Document><Result>SUCCESS</Result></Document></testServiceResponseType>");
		mockClient.sendRequest(withPayload(requestPayload));//.andExpect(payload(responsePayload));
	}
}
