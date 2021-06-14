package com.realtimeodd.service.impl;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeodd.common.model.LiveEventResponse;
import com.realtimeodd.common.util.Constants;
import com.realtimeodd.service.RealTimeOddInfoService;

/**
 * 
 * @author papireddy.padamati Provides the operation for calling the Kambi API.
 *
 */
public class RealtimeOddsInfoServiceCaller implements RealTimeOddInfoService {
	
	private ObjectMapper objectMapper;
	
	/**
	 * 
	 * @param objectMapper an objectMapper provided as an argument.
	 */
	public RealtimeOddsInfoServiceCaller(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}
	
	/**
	 * Get the odds information from the Kambi API.
	 */
	public  LiveEventResponse getLiveEvent() throws IOException {
		
		URL url = new URL(Constants.URL);
		
		try {
			return objectMapper.readValue(url, LiveEventResponse.class);
		} catch (IOException e) {
			throw new IOException("Failed with following exception:" + e.getMessage());
		}

	}
}
