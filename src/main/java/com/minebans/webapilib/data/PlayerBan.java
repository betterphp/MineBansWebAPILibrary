package com.minebans.webapilib.data;

/**
 * A ban that was made against a player
 */
public class PlayerBan {
	
	private String player_name;
	private String issued_by;
	private String server_name;
	private int time;
	private String reason;
	private String long_reason;
	
	/**
	 * Gets the name of the player that the ban was issued against.
	 * 
	 * @return The name of the player
	 */
	public String getPlayerName(){
		return this.player_name;
	}
	
	/**
	 * Gets the name of the player that issued the ban.
	 * 
	 * @return The name of the player
	 */
	public String getModeratorName(){
		return this.issued_by;
	}
	
	/**
	 * Gets the name of the server that issued the ban.
	 * 
	 * @return The name of the server
	 */
	public String getServerName(){
		return this.server_name;
	}
	
	/**
	 * Gets the time that the ban was issued as a Unix timestamp.
	 * 
	 * @return The time
	 */
	public int getTime(){
		return this.time;
	}
	
	/**
	 * Gets the reason for the ban
	 * 
	 * @return The reason
	 */
	public String getReason(){
		return this.reason;
	}
	
	/**
	 * Gets the long description that the ban was issued.
	 * 
	 * @return The long description of the ban reason
	 */
	public String getLongReason(){
		return this.long_reason;
	}
	
}
