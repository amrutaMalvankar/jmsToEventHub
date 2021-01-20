package com.fedex.jms.poc;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AzureEventHubConsumer {
	
	 @Autowired 
	 private JmsTemplate jmsTemplate;
	
	private final static String EH_NAMESPACE_CONNECTION_STRING = 
			"Endpoint=sb://new-event-poc-json.servicebus.windows.net/;SharedAccessKeyName=policy_pkg_delay_outbound_hub;SharedAccessKey=EWTcxtrmFwcGAsOlcaetafZrPRU+EIex17WgzyI1A9k=;EntityPath=package_delay_outbound_eventhub";
    //rivate static final String EH_NAMESPACE_CONNECTION_STRING = "new-event-poc-json";
    private static final String eventHubName = "package_delay_outbound_eventhub";
    private static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=eventhubjmssa;AccountKey=zFGk6rfDl3/y2KxKi/NWMYtGoC4NfYss+FRcXJJ5jbtbK3JGkO2B5gA4G3823nn7G00sCVZvb7lmpErRN1sdJA==;EndpointSuffix=core.windows.net";
    private static final String STORAGE_CONTAINER_NAME = "tojms";
    
    public final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
        System.out.printf("Processing event from partition %s with sequence number %d with body: %s %n", 
                eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(), eventContext.getEventData().getBodyAsString());

		
		  ObjectMapper mapper = new ObjectMapper(); String out;
		  if(eventContext.getEventData().getBodyAsString()!=null) { try { out =
		  mapper.writeValueAsString(eventContext.getEventData().getBodyAsString());
		  
		  if(out !=null) { 
			  jmsTemplate.convertAndSend("ship_details_out_queue",out);
		  }
		  
		 } catch (JsonProcessingException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); } }
		
        
       if (eventContext.getEventData().getSequenceNumber() % 10 == 0) {
           eventContext.updateCheckpoint();
       }
       
      // return eventContext.getEventData().getBodyAsString();
   };

   public final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
       System.out.printf("Error occurred in partition processor for partition %s, %s.%n",
           errorContext.getPartitionContext().getPartitionId(),
           errorContext.getThrowable());
   };    
   
   public void pullMessageFromEventHub1() throws Exception {
		/*
		 * EventHubConsumerClient consumer = new EventHubClientBuilder()
		 * .connectionString(EH_NAMESPACE_CONNECTION_STRING)
		 * .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
		 * .buildConsumerClient();
		 * 
		 * String partitionId = "0"; System.out.println("******************* Help ");
		 * 
		 * // Get the first 15 events in the stream, or as many events as can be
		 * received within 40 seconds. IterableStream<PartitionEvent> events =
		 * consumer.receiveFromPartition(partitionId, 15, EventPosition.earliest(),
		 * Duration.ofSeconds(40)); for (PartitionEvent event : events) {
		 * System.out.println("Event: " + event.getData().getBodyAsString()); }
		 */
		
		EventHubConsumerAsyncClient consumer = new EventHubClientBuilder()
			    .connectionString(EH_NAMESPACE_CONNECTION_STRING)
			    .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
			    .buildAsyncConsumerClient();
		

	consumer.receiveFromPartition("0", EventPosition.latest()).subscribe(event -> {
		jmsTemplate.convertAndSend("ship_details_out_queue",event.getData().getBodyAsString());
	   System.out.println("Hello Async ##################### " +event.getData().getBodyAsString());
	   
	});
	
	jmsTemplate.convertAndSend("ship_details_out_queue","************* hjehflfsed");
	
	
   }
   public void pullMessageFromEventHub() throws Exception {
       BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
           .connectionString(STORAGE_CONNECTION_STRING)
           .containerName(STORAGE_CONTAINER_NAME)
           .buildAsyncClient();

       EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
           .connectionString(EH_NAMESPACE_CONNECTION_STRING, eventHubName)
           .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
           .processEvent(PARTITION_PROCESSOR)
           .processError(ERROR_HANDLER)
           .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient));

       //eventProcessorClientBuilder.processEvent(PARTITION_PROCESSOR);
       EventProcessorClient eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();

       System.out.println("Starting event processor");
       eventProcessorClient.start();

       System.out.println("Press enter to stop.");
       System.in.read();

       System.out.println("Stopping event processor");
       eventProcessorClient.stop();
       System.out.println("Event processor stopped.");

       System.out.println("Exiting process");
   }
    
	/*
	 * public static final void pushMessageToEventHub(ShipDetails shipDetails) { //
	 * create a producer using the namespace connection string and event hub name
	 * EventHubProducerClient producer = new EventHubClientBuilder()
	 * .connectionString(connectionString, eventHubName) .buildProducerClient();
	 * System.out.println("Ship Details Data To Event Hub :" +
	 * shipDetails.toString()); // prepare a batch of events to send to the event
	 * hub EventDataBatch batch = producer.createBatch(); batch.tryAdd(new
	 * EventData(shipDetails.toString()));
	 * 
	 * 
	 * // send the batch of events to the event hub producer.send(batch);
	 * 
	 * // close the producer producer.close(); }
	 * 
	 * public static final void pushMessageToEventHub(String data) { // create a
	 * producer using the namespace connection string and event hub name
	 * EventHubProducerClient producer = new EventHubClientBuilder()
	 * .connectionString(connectionString, eventHubName) .buildProducerClient();
	 * System.out.println("CSV Data To Event Hub :" + data); // prepare a batch of
	 * events to send to the event hub EventDataBatch batch =
	 * producer.createBatch(); batch.tryAdd(new EventData(data));
	 * 
	 * // send the batch of events to the event hub producer.send(batch);
	 * 
	 * // close the producer producer.close(); }
	 */
}
