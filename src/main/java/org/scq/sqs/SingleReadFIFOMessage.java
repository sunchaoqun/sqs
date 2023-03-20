package org.scq.sqs;


import java.util.ArrayList;
import java.util.List;

import org.scq.sqs.common.Constant;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequest;
import com.amazonaws.services.sqs.model.DeleteMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class SingleReadFIFOMessage 
{
    public static void main( String[] args )
    {

    	
    	AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(
    			Constant.AK,
    			Constant.SK
    		));
		
    	AmazonSQS sqs = AmazonSQSClientBuilder.standard()
  			  .withCredentials(awsCredentialsProvider)
  			  .withRegion(Constant.REGION)
  			  .build();
  	
  	
    String fifoQueueUrl = Constant.FIFO_QUEUE_URL;
  	
  	System.out.println(fifoQueueUrl);
	
  	while(true) {
  		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(fifoQueueUrl).withWaitTimeSeconds(0)
  				.withMaxNumberOfMessages(10);

  	  	List<Message> sqsMessages = sqs.receiveMessage(receiveMessageRequest).getMessages();
  	  	
  	  List<DeleteMessageBatchRequestEntry> deleteMessageBatchRequestEntries = new ArrayList<DeleteMessageBatchRequestEntry>();
  	  	
  	  	for(Message msg: sqsMessages) {
//  	  		System.out.println(msg.getMessageAttributes());
//  	  		System.out.println(msg);
  	  		
  	  		
  	  		
  	  	System.out.println(msg.getBody()+" " +  System.currentTimeMillis() + " " + Thread.currentThread());
//  	  		System.out.println(msg.getMessageAttributes());
		DeleteMessageBatchRequestEntry entry = new DeleteMessageBatchRequestEntry(
				msg.getMessageId(), msg.getReceiptHandle());
        deleteMessageBatchRequestEntries.add(entry);	
  	  		
//  	  		sqs.deleteMessage(new DeleteMessageRequest()
//  	  				  .withQueueUrl(fifoQueueUrl)
//  	  				  .withReceiptHandle(msg.getReceiptHandle()));

  	  	}
  	  	
		if(deleteMessageBatchRequestEntries.size()>0) {
			sqs.deleteMessageBatch(new DeleteMessageBatchRequest(fifoQueueUrl,deleteMessageBatchRequestEntries));
		}
  	}

        
    }
}
