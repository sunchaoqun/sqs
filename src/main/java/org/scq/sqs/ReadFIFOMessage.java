package org.scq.sqs;

import java.util.List;

import org.scq.sqs.common.Constant;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class ReadFIFOMessage {
	public static void main(String[] args) {

		for (int i = 0; i < Constant.READ_CLIENT_NUMBER; i++) {
			new Thread(new Task()).start();
		}

	}
}

class Task implements Runnable {

	@Override
	public void run() {

    	AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(
    			Constant.AK,
    			Constant.SK
    		));
		
		System.out.println(">>>> " + Thread.currentThread());
		
    	AmazonSQS sqs = AmazonSQSClientBuilder.standard()
  			  .withCredentials(awsCredentialsProvider)
  			  .withRegion(Constant.REGION)
  			  .build();

		String fifoQueueUrl = Constant.FIFO_QUEUE_URL;

		System.out.println(fifoQueueUrl);

		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(fifoQueueUrl).withWaitTimeSeconds(Constant.READ_CLIENT_WAIT_TIME_SECONDS)
				.withMaxNumberOfMessages(Constant.READ_CLIENT_MAX_NUMBER_OF_MESSAGES);

		List<Message> sqsMessages = sqs.receiveMessage(receiveMessageRequest).getMessages();

		for (Message msg : sqsMessages) {
//  		System.out.println(msg.getMessageAttributes());
//  		System.out.println(msg);

			System.out.println(msg.getBody() + Thread.currentThread());
//  		System.out.println(msg.getMessageAttributes());

			try {
				Thread.sleep(Constant.READ_CLIENT_SLEEP_TIME_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//  		sqs.deleteMessage(new DeleteMessageRequest()
//  				  .withQueueUrl(fifoQueueUrl)
//  				  .withReceiptHandle(msg.getReceiptHandle()));

		}
	}
}
