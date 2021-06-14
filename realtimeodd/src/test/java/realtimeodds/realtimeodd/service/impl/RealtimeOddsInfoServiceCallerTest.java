package realtimeodds.realtimeodd.service.impl;

import static org.junit.Assert.assertThat;

import java.net.URL;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeodd.common.model.LiveEventResponse;
import com.realtimeodd.service.impl.RealtimeOddsInfoServiceCaller;

/**
 * 
 * @author papireddy.padamati Provides the operations to run the test cases for
 *         Real Time Odds.
 *
 */
public class RealtimeOddsInfoServiceCallerTest {

	private RealtimeOddsInfoServiceCaller realtimeOddsInfoServiceCaller;
	private ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception {

		objectMapper = Mockito.mock(ObjectMapper.class);
		realtimeOddsInfoServiceCaller = new RealtimeOddsInfoServiceCaller(objectMapper);

	}

	/**
	 * Verifies the Kambi API and throws the exception when any issue occurs while
	 * getting the information from the API.
	 * 
	 * @throws Exception throws the exception if any issue occurs.
	 */
	@Test
	public void verifyGetLiveEvent() throws Exception {
		LiveEventResponse expectedLiveEventResponse = new LiveEventResponse();

		Mockito.doReturn(expectedLiveEventResponse).when(objectMapper).readValue(Mockito.any(URL.class),
				Mockito.eq(LiveEventResponse.class));

		LiveEventResponse actualLiveEventResponse = realtimeOddsInfoServiceCaller.getLiveEvent();

		assertThat(actualLiveEventResponse, Is.is(expectedLiveEventResponse));
		Mockito.verify(objectMapper, Mockito.only()).readValue(Mockito.any(URL.class),
				Mockito.eq(LiveEventResponse.class));

	}

}
