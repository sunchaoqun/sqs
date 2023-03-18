package org.scq.sqs;

import java.util.HashMap;
import java.util.Map;

import org.scq.sqs.common.Constant;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

public class CreateFIFOQueue 
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

    	Map<String, String> queueAttributes = new HashMap<>();
    	queueAttributes.put("FifoQueue", "true");
    	queueAttributes.put("ContentBasedDeduplication", "true");
    	
    	CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest(
    	  Constant.FIFO_QUEUE_NAME).withAttributes(queueAttributes);
    	String fifoQueueUrl = sqs.createQueue(createFifoQueueRequest)
    	  .getQueueUrl();
    	
    	
    	System.out.println(fifoQueueUrl);

    }
}
