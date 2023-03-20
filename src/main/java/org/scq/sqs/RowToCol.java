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

public class RowToCol {

	public static void main(String[] args) {
		
		int row = 3;
		int col = 500;
		
		int[][] arr1 = new int[row][col];
		int[][] arr2 = new int[col][row];
		int temp = 0;
		//二维数组赋值
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				temp++;
				arr1[i][j] = temp;
			}
		}
		//循环打印二维数组
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				System.out.print(arr1[i][j] + "   ");
			}
			System.out.println();
		}
		System.out.println("======================================");
		//M行N列的矩阵交换行和列
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				arr2[j][i] = arr1[i][j];
			}
		}
		//打印交换后的矩阵
		for(int i=0;i<col;i++){
			for(int j=0;j<row;j++){
				System.out.print("("+ i +")" + arr2[i][j] + "   ");
				
				sendMessage(String.valueOf(arr2[i][j]),String.valueOf(arr2[i][j]));
				
				
			}
			System.out.println();
		}
	}

	public static void sendMessage(String msg ,String groudId) {
		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(Constant.AK, Constant.SK));

		AmazonSQS sqs = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider)
				.withRegion(Constant.REGION).build();

		String fifoQueueUrl = Constant.FIFO_QUEUE_URL;

		System.out.println(fifoQueueUrl);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put("Attribute",
				new MessageAttributeValue().withStringValue(sdf.format(new Date())).withDataType("String"));

		SendMessageRequest sendMessageFifoQueue = new SendMessageRequest().withQueueUrl(fifoQueueUrl)
				.withMessageBody("Body " + msg).withMessageGroupId("scq-group" + groudId)
				.withMessageAttributes(messageAttributes);
		sqs.sendMessage(sendMessageFifoQueue);

		System.out.println(sendMessageFifoQueue.getMessageGroupId() + " Message sent " + msg);
	}
}


