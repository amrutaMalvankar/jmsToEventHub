package com.fedex.jms.poc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RESTController {

    @Autowired 
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private AzureEventHubConsumer azureEventHubConsumer;

    @RequestMapping("/send/{to}/{message}")
    public String sendMessage(@PathVariable String to,@PathVariable String message) {
        try {
            jmsTemplate.convertAndSend("ship_details_queue",new ShipDetails(to, "Package Details : " + message));
            return "jms message sent for "+to;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in sending message!";
        }


    }
    
    @RequestMapping("/send/dumpcsv")
    public String sendMessage() {
        try {
        	List<Map<?, ?>> dataList = TestMessageData.covertCSVTestDataToJSON();
        	//dataList.stream().forEach(e ->  jmsTemplate.convertAndSend("ship_details_queue", e));
        	ObjectMapper mapper = new ObjectMapper();
            String out = mapper.writeValueAsString(dataList);
            jmsTemplate.convertAndSend("ship_details_queue",out);
            return "csv file dumped as messages in jms queue";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in sending message!";
        }
    }
        
        @RequestMapping("/receive/dumpcsv")
        public String receiveMessage() {
            try {
            	
            	azureEventHubConsumer.pullMessageFromEventHub();
            	
            	//jmsTemplate.convertAndSend("ship_details_out_queue","Hello ************************** ");
            	
				/*
				 * List<Map<?, ?>> dataList = TestMessageData.covertCSVTestDataToJSON();
				 * //dataList.stream().forEach(e ->
				 * jmsTemplate.convertAndSend("ship_details_queue", e)); ObjectMapper mapper =
				 * new ObjectMapper(); String out = mapper.writeValueAsString(dataList);
				 * jmsTemplate.convertAndSend("ship_details_queue",out);
				 */
                return "Events consumed from event hub :: ";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error in sending message!";
            }


    }
}
