package de.aestis.dndreloaded.Util;

import java.util.HashMap;

import de.aestis.dndreloaded.Quests.Quest;

public class QuestMap {
	
	private HashMap<String, HashMap<Integer, Quest>> map;

    public QuestMap() { 
        map = new HashMap<String, HashMap<Integer, Quest>>();
    }

    public void insertQuest(String UUID, Integer QuestID, Quest Quest){
        if(map.get(UUID) == null) { 
            map.put(UUID, new HashMap<Integer, Quest>());
        }
        map.get(UUID).put(QuestID, Quest);
    }
    
    public HashMap<Integer, Quest> getAllQuests(String UUID) {
    	return map.get(UUID);
    }

    public Quest getQuestByID(String UUID, Integer QuestID) {
        return map.get(UUID).get(QuestID);
    }
}
