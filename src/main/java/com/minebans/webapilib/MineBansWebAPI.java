package com.minebans.webapilib;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.minebans.webapilib.data.PlayerBan;

/**
 * Represents an API connection for a specific server 
 */
public class MineBansWebAPI {
	
	private String apiKey;
	private JsonParser jsonParser;
	private Gson gson;
	
	/**
	 * @param apiKey The server API key to use, can be null if you only plan on making requests that do not require it
	 */
	public MineBansWebAPI(String apiKey){
		this.apiKey = apiKey;
		this.jsonParser = new JsonParser();
		this.gson = new Gson();
	}
	
	/**
	 * Gets all of the moderators for the server, these are the users that have permission to upload data
	 * 
	 * @return A list of player names that can moderate the server
	 * @throws IOException If the communication with minebans.com fails
	 * @throws IllegalStateException If the server API key was not set
	 */
	public List<String> getServerModerators() throws IOException {
		if (this.apiKey == null){
			throw new IllegalStateException("API key not set");
		}
		
		InputStream input = null;
		
		try{
			URL url = new URL("http://minebans.com/feed/server_moderators.json?api_key=" + this.apiKey);
			
			URLConnection connection = url.openConnection();
			
			connection.setUseCaches(false);
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			
			input = connection.getInputStream();
			
			return Arrays.asList(this.gson.fromJson(new InputStreamReader(input), String[].class));
		}catch (MalformedURLException e){
			e.printStackTrace();
		}finally{
			if (input != null){
				try{
					input.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		
		return Arrays.asList(new String[]{});
	}
	
	/**
	 * Gets all global bans for a player
	 * 
	 * <p>
	 * 	This method does not require the server API key to be set
	 * </p>
	 * 
	 * @param playerName The name of the player lookup
	 * @return A list of {@link PlayerBan} for the player
	 * @throws IOException If the communication with minebans.com fails
	 * @throws IllegalArgumentException If the player name it not valid
	 */
	public List<PlayerBan> getPlayerBans(String playerName) throws IOException {
		if (!playerName.matches("[A-Za-z0-9_]{2,16}")){
			throw new IllegalArgumentException("Invalid player name");
		}
		
		ArrayList<PlayerBan> bans = new ArrayList<PlayerBan>();
		
		InputStream input = null;
		
		try{
			URL url = new URL("http://minebans.com/feed/player_bans.json?player_name=" + playerName);
			
			URLConnection connection = url.openConnection();
			
			connection.setUseCaches(false);
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			
			input = connection.getInputStream();
			
			JsonArray entries = this.jsonParser.parse(new InputStreamReader(input)).getAsJsonArray();
			
			for (JsonElement element : entries){
				bans.add(this.gson.fromJson(element, PlayerBan.class));
			}
		}catch (MalformedURLException e){
			e.printStackTrace();
		}finally{
			if (input != null){
				try{
					input.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		
		return bans;
	}
	
	/**
	 * Gets all global bans for a server
	 * 
	 * @return A list of {@link PlayerBan} uploaded by the server
	 * @throws IOException If the communication with minebans.com fails
	 * @throws IllegalStateException If the server API key was not set
	 */
	public List<PlayerBan> getServerBans() throws IOException {
		if (this.apiKey == null){
			throw new IllegalStateException("API key not set");
		}
		
		ArrayList<PlayerBan> bans = new ArrayList<PlayerBan>();
		
		InputStream input = null;
		
		try{
			URL url = new URL("http://minebans.com/feed/server_bans.json?api_key=" + this.apiKey);
			
			URLConnection connection = url.openConnection();
			
			connection.setUseCaches(false);
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			
			input = connection.getInputStream();
			
			JsonArray entries = this.jsonParser.parse(new InputStreamReader(input)).getAsJsonArray();
			
			for (JsonElement element : entries){
				bans.add(this.gson.fromJson(element, PlayerBan.class));
			}
		}catch (MalformedURLException e){
			e.printStackTrace();
		}finally{
			if (input != null){
				try{
					input.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		
		return bans;
	}
	
}
