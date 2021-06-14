package com.realtimeodd.service;

import java.util.Optional;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeodd.common.model.LiveEvent;
import com.realtimeodd.common.model.LiveEventResponse;
import com.realtimeodd.common.model.Outcome;
import com.realtimeodd.common.util.Constants;
import com.realtimeodd.common.util.FormatUtil;
import com.realtimeodd.service.impl.RealtimeOddsInfoServiceCaller;

/**
 * 
 * @author papireddy.padamati
 * 
 *         Provides the interfaces to call Kambi API and Live events operations.
 * 
 */
public class RealTimeOddTimerTask extends TimerTask {

	private final Logger log = LoggerFactory.getLogger(RealTimeOddTimerTask.class);

	private RealTimeOddInfoService realTimeOddInfoService;
	private String liveEventName;

	private Long id;

	/**
	 * Provides the class and interface for calling the Kambi API to get the Odds
	 * Information.
	 * 
	 * @param id an event Id provided as argument.
	 */
	public RealTimeOddTimerTask(Long id) {
		this.id = id;
		realTimeOddInfoService = new RealtimeOddsInfoServiceCaller(new ObjectMapper());
	}

	/**
	 * This operation takes the id of any event provided and prints the output on 
	 * the console with the event name current time and main bet offer odds of that 
	 * event.
	 */

	@Override
	public void run() {
		excuteLiveEvents(id);

	}

	private void excuteLiveEvents(Long id) {

		try {

			LiveEventResponse liveEventResponse = realTimeOddInfoService.getLiveEvent();

			Optional<LiveEvent> liveEventOptional = getLiveEventbyId(liveEventResponse, id);

			if (liveEventOptional.isPresent()) {
				LiveEvent liveEvent = liveEventOptional.get();
				if (liveEventName == null) {
					liveEventName = liveEvent.getEvent().getName();
					System.out.println("Event: " + liveEventName);
				}

				displayOutcome(liveEvent);
				System.out.print("\n");
			}
		} catch (Exception e) {
			log.error("Exception:", e);
		}

	}

	private Optional<LiveEvent> getLiveEventbyId(LiveEventResponse liveEventResponse, Long id) {

		return liveEventResponse.getLiveEvents().stream()
				.filter(event -> event.getEvent() != null && event.getEvent().getId().equals(id))
				.filter(event -> event.getEvent().getTags() != null
						&& event.getEvent().getTags().contains(Constants.TAGS))
				.findAny();

	}

	private void displayOutcome(LiveEvent liveEvent) {

		if (liveEvent.getMainBetOffer() != null) {
			System.out.print("[" + FormatUtil.getLocalTime() + "]");
			liveEvent.getMainBetOffer().getOutcomes().stream().forEach(this::printOdds);
		}

	}

	private void printOdds(Outcome outcome) {
		double oddValue = outcome.getOdds() / 1000;
		System.out.print(" | " + outcome.getLabel() + ":     " + FormatUtil.getDecimalValue(oddValue) + " | ");
	}

}
