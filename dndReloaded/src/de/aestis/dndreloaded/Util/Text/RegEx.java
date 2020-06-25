package de.aestis.dndreloaded.Util.Text;

import de.aestis.dndreloaded.Players.PlayerData;

enum PlayerValues {
	PLAYER_ID,
	PLAYER_NAME,
	PLAYER_FACTION,
	PLAYER_REPUTATION,
	PLAYER_TITLE,
	/*
	 * ^
	 * I
	 * v
	 */
	PLAYER_KILLS_FRIENDLY,
	PLAYER_KILLS_ENEMY
}

public class RegEx {
	
	public String insertPlayerData(String input, PlayerData pd) {
		
		String output = null;
		
		if (pd != null)
		{
			for (PlayerValues value : PlayerValues.values())
			{
				switch (value)
				{
					case PLAYER_ID:
						input = input.replaceAll("{" + value + "}", pd.getID().toString());
						break;
					
					case PLAYER_NAME:
						input = input.replaceAll("{" + value + "}", pd.getName());
						break;
						
					case PLAYER_FACTION:
						input = input.replaceAll("{" + value + "}", pd.getFaction());
						break;
						
					case PLAYER_REPUTATION:
						input = input.replaceAll("{" + value + "}", pd.getReputation().toString());
						break;
						
					case PLAYER_TITLE:
						input = input.replaceAll("{" + value + "}", pd.getTitle());
						break;
						
					default:
						break;
					
				}
			}
		}
		
		return output;
	}
	
}
