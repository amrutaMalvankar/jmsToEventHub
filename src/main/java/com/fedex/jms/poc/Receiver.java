package com.fedex.jms.poc;

import java.util.List;
import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
/*
	@JmsListener(destination = "ship_details_queue", containerFactory = "myFactory")
	public void receiveMessage(ShipDetails shipDetails) {
		System.out.println("Received <" + shipDetails + ">");
		//push the message into AZURE Event Hub
		AzureEventHubProducer.pushMessageToEventHub(shipDetails);
	}
	
	@JmsListener(destination = "ship_details_queue", containerFactory = "myFactory")
	public void receiveMessage(List<Map<?, ?>> dataList) {
		System.out.println("Received Data List <" + dataList + ">");
		//push the message into AZURE Event Hub
		dataList.stream().forEach(d -> AzureEventHubProducer.pushMessageToEventHub(d.toString()));
		//AzureEventHubProducer.pushMessageToEventHub(dataList);
	}
*/	
	@JmsListener(destination = "ship_details_queue", containerFactory = "myFactory")
	public void receiveMessage(String message) {
		System.out.println("Received String Data Message <" + message + ">");
		//push the message into AZURE Event Hub
		AzureEventHubProducer.pushMessageToEventHub(message);
		//AzureEventHubProducer.pushMessageToEventHub(dataList);
	}
	
	@JmsListener(destination = "ship_details_out_queue", containerFactory = "myFactory")
	public void receiveEventHubMessage(String message) {
		System.out.println("Received String Data Message from out queue ************ ########## :: <" + message + ">");
		//push the message into AZURE Event Hub
		//AzureEventHubProducer.pushMessageToEventHub(message);
		//AzureEventHubProducer.pushMessageToEventHub(dataList);
	}

}
