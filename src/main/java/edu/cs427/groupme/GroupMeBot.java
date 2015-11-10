package edu.cs427.groupme;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.*;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * This version is a simple implementation of the groupme bot which can send messages.
 * This version needs json library. We didn't modify the pom.xml probably need to fix the dependency issue.
 * @author blessin2 admathu2
 * @edits pzhao12 fricken2
 *
 */
public final class GroupMeBot {
	//Base URL used for all GroupMe interactions
	public static String botName;
	public static String getBotName() {
		return botName;
	}

	public static String accessToken;
	public static String groupId;
	public static String botId;
	public static IGroupMeBotConnection connection; 
	
	public static void init(String botName,String accessToken, String groupId, IGroupMeBotConnection connection) 
	{
		GroupMeBot.botName = botName;
		GroupMeBot.accessToken = accessToken;
		GroupMeBot.groupId = groupId;
		GroupMeBot.connection = connection;
		botId = "";
	}
	
	
	public static boolean register()
	{
		JSONObject obj = connection.register(botName, groupId, accessToken);
		
		if(obj==null)
			return false;
		
		extractBotId(obj);
	
		return true;
	}


	public static void extractBotId(JSONObject obj) {
		if (obj == null){
			botId = "";
			return;
		}
		String botIdString = "";
		try
		{
			botIdString = obj.getJSONObject("response").getJSONObject("bot").getString("bot_id");
		}
		catch (JSONException e)
		{
			botIdString = "";
		}
		botId = botIdString;
	}


	public static boolean sendTextMessage(String message)
	{
		int resp = connection.sendMessage(botId, message);
		if (resp == 202)
			return true;
		return false;
	}

	public static boolean isUnregistered()
	{
		if (botId == null)
			botId = "";
		return botId.equals("");
	}
	
//	public void sendImage(String text, String imageURL)
//	{
//		try
//		{
//			String urlParameters = "{\"bot_id\":\"" + botId + "\",\"text\":\"" + text
//					+ "\",\"attachments\":[{\"type\":\"image\",\"url\":\"" + imageURL + "\"}]}";
//			String request = "https://api.groupme.com/v3/bots/post";
//			URL url = new URL(request);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setInstanceFollowRedirects(false);
//			connection.setRequestMethod("POST");
//			connection.setUseCaches(false);
//
//			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//			wr.writeBytes(urlParameters);
//			wr.flush();
//			wr.close();
//			connection.disconnect();
//
//			int responseCode = connection.getResponseCode();
//			if (responseCode != 202)
//				System.out.println(responseCode + " error has occured while sending the image: " + imageURL);
//		} catch (MalformedURLException e)
//		{
//			System.out.println("Error occured while establishing a connection");
//			e.printStackTrace();
//		} catch (IOException e)
//		{
//			System.out.println("Error occured while sending data");
//			e.printStackTrace();
//		}
//	}
		
//	public void sendLocation(String text, String locationName, double longitude, double latitude)
//	{
//		try
//		{
//			String urlParameters = "{\"bot_id\":\"" + botId + "\",\"text\":\"" + text +"\",\"attachments\":[{\"type\":\"location\",\"lng\":\"" 
//					+ longitude +"\",\"lat\":\"" + latitude + "\",\"name\":\"" + locationName +"\"}]}";
//			String request = "https://api.groupme.com/v3/bots/post";
//			URL url = new URL(request);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setInstanceFollowRedirects(false);
//			connection.setRequestMethod("POST");
//			connection.setUseCaches(false);
//
//			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//			wr.writeBytes(urlParameters);
//			wr.flush();
//			wr.close();
//			connection.disconnect();
//
//			int responseCode = connection.getResponseCode();
//			if (responseCode != 202)
//				System.out.println(responseCode + " error has occured while sending the location: " + latitude + " " + longitude);
//		} catch (MalformedURLException e)
//		{
//			System.out.println("Error occured while establishing a connection");
//			e.printStackTrace();
//		} catch (IOException e)
//		{
//			System.out.println("Error occured while sending data");
//			e.printStackTrace();
//		}
//	}
	/*
		public static void main(String args[]){
			GroupmeBot bot = new GroupmeBot("test_bot_pzhao12","40bL6d4xsBRLt0b3zBrbiXr6v6Fp46Snuu6ybZro","17407658","");
			bot.register();
			//bot.sendTextMessage("local test with bot id "+bot.botId);
			//bot.sendImage("test with bot id"+bot.botId, "");
			//bot.sendLocation("local test with bot id "+ bot.botId, "grainger",  -88.22,40.11);
		}
	 */
}