package org.scq.sqs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.scq.sqs.common.Constant;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SingleSendFIFOMessage {
	public static void main(String[] args) {
		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(Constant.AK, Constant.SK));

		AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider)
				.withRegion(Constant.REGION).build();

		String fifoQueueUrl = Constant.FIFO_QUEUE_URL;

		System.out.println(fifoQueueUrl);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < Constant.SEND_CLIENT_MESSAGE_COUNT; i++) {
			Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
			messageAttributes.put("Attribute",
					new MessageAttributeValue().withStringValue(sdf.format(new Date())).withDataType("String"));

			SendMessageRequest sendMessageFifoQueue = new SendMessageRequest().withQueueUrl(fifoQueueUrl)
					.withMessageBody("Body " + i).withMessageGroupId("scq-group")
					.withMessageAttributes(messageAttributes);
			sqs.sendMessage(sendMessageFifoQueue);

			System.out.println(sendMessageFifoQueue.getMessageGroupId() + " Message sent " + i);
		}

	}
}