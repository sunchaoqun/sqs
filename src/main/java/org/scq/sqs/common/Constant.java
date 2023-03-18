package org.scq.sqs.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;

public class Constant {
	public static String REGION = null;
	public static String AK = null;
	public static String SK = null;
	public static String ACCOUNT_ID = null;
	public static String FIFO_QUEUE_URL = null;
	public static String FIFO_QUEUE_NAME = null;

	public static int SEND_CLIENT_NUMBER = 4000;
	public static int SEND_CLIENT_MESSAGE_COUNT = 4000;

	public static int READ_CLIENT_NUMBER = 4000;
	public static int READ_CLIENT_MAX_NUMBER_OF_MESSAGES = 1;
	public static int READ_CLIENT_WAIT_TIME_SECONDS = 10;
	
	public static int READ_CLIENT_SLEEP_TIME_MS = 500;
	static {
		Properties prop = new Properties();
		try {

			File file = new File("config.properties");

			System.out.println(file.getAbsolutePath());

			prop.load(new FileInputStream(file));

			REGION = prop.getProperty("REGION");

			AK = prop.getProperty("AK");
			SK = prop.getProperty("SK");

			AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
					new BasicAWSCredentials(Constant.AK, Constant.SK));

			AmazonIdentityManagement iam = null;
			try {
				iam = AmazonIdentityManagementClientBuilder.standard().withCredentials(awsCredentialsProvider)
						.withRegion(Constant.REGION).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ACCOUNT_ID = iam.getUser().getUser().getArn().split(":")[4];
			FIFO_QUEUE_NAME = prop.getProperty("FIFO_QUEUE_NAME");
			FIFO_QUEUE_URL = "https://sqs." + REGION + ".amazonaws.com/" + ACCOUNT_ID + "/" + FIFO_QUEUE_NAME;

			System.out.println(FIFO_QUEUE_URL);
			
			READ_CLIENT_SLEEP_TIME_MS = Integer.parseInt(prop.getProperty("READ_CLIENT_SLEEP_TIME_MS"));

			SEND_CLIENT_NUMBER = Integer.parseInt(prop.getProperty("SEND_CLIENT_NUMBER"));
			SEND_CLIENT_MESSAGE_COUNT = Integer.parseInt(prop.getProperty("SEND_CLIENT_MESSAGE_COUNT"));

			READ_CLIENT_NUMBER = Integer.parseInt(prop.getProperty("READ_CLIENT_NUMBER"));
			READ_CLIENT_MAX_NUMBER_OF_MESSAGES = Integer
					.parseInt(prop.getProperty("READ_CLIENT_MAX_NUMBER_OF_MESSAGES"));
			READ_CLIENT_WAIT_TIME_SECONDS = Integer.parseInt(prop.getProperty("READ_CLIENT_WAIT_TIME_SECONDS"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}
}
