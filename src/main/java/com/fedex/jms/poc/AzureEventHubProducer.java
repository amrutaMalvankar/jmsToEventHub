package com.fedex.jms.poc;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;

public class AzureEventHubProducer {
	
	/*
	 * private final static String connectionString =
	 * "Endpoint=sb://new-event-poc-json.servicebus.windows.net/;SharedAccessKeyName=policy_pkg_delay_inbound_hub;SharedAccessKey=HVA+ACUn1nnMV6Rv4BLsTaxjyDi/W6jKp9niUa1q/Ik=;EntityPath=package_delay_inbound_eventhub";
	 * private final static String eventHubName = "package_delay_inbound_eventhub";
	 */ 
	private final static String connectionString = 
			"Endpoint=sb://new-event-poc-json.servicebus.windows.net/;SharedAccessKeyName=policy_pkg_delay_outbound_hub;SharedAccessKey=EWTcxtrmFwcGAsOlcaetafZrPRU+EIex17WgzyI1A9k=;EntityPath=package_delay_outbound_eventhub";
    private static final String eventHubName = "package_delay_outbound_eventhub";
    
    public static final void pushMessageToEventHub(ShipDetails shipDetails) {
    	// create a producer using the namespace connection string and event hub name
        EventHubProducerClient producer = new EventHubClientBuilder()
            .connectionString(connectionString, eventHubName)
            .buildProducerClient();
        System.out.println("Ship Details Data To Event Hub :" + shipDetails.toString());
        // prepare a batch of events to send to the event hub    
        EventDataBatch batch = producer.createBatch();
        batch.tryAdd(new EventData(shipDetails.toString()));


        // send the batch of events to the event hub
        producer.send(batch);

        // close the producer
        producer.close();
    }
    
    public static final void pushMessageToEventHub(String data) {
    	// create a producer using the namespace connection string and event hub name
        EventHubProducerClient producer = new EventHubClientBuilder()
            .connectionString(connectionString, eventHubName)
            .buildProducerClient();
        System.out.println("CSV Data To Event Hub :" + data);
        // prepare a batch of events to send to the event hub    
        EventDataBatch batch = producer.createBatch();
        batch.tryAdd(new EventData(data));
        
        // send the batch of events to the event hub
        producer.send(batch);

        // close the producer
        producer.close();
    }

}
