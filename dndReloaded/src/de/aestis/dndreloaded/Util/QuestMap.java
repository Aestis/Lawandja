package de.aestis.dndreloaded.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.aestis.dndreloaded.Quests.Quest;

public class QuestMap {
	
	private HashMap<String, HashMap<Integer, Quest>> map;

    public QuestMap() {
    	
        map = new HashMap<String, HashMap<Integer, Quest>>();
    }

    public void insertQuest(String UUID, Integer QuestID, Quest Quest){
    	
        if(map.get(UUID) == null) map.put(UUID, new HashMap<Integer, Quest>());
        map.get(UUID).put(QuestID, Quest);
    }
    
    public boolean hasQuests(String UUID) {
    	
    	if (map.containsKey(UUID)) return true;
    	return false;
    }
    
    public List<HashMap<Integer, Quest>> getAllQuestsFromNPC(String UUID) {
    	
    	List<HashMap<Integer, Quest>> list = new ArrayList<HashMap<Integer, Quest>>();
    	
    	for (Integer key : map.get(UUID).keySet()) {
    		HashMap<Integer, Quest> buffer = new HashMap<Integer, Quest>();
    		buffer.put(key, map.get(UUID).get(key));
    		list.add(buffer);
    	}
    	System.out.println("Quest List Size: " + list.size());
    	return list;
    }

    public Quest getQuestByID(String UUID, Integer QuestID) {
    	
        return map.get(UUID).get(QuestID);
    }
}
