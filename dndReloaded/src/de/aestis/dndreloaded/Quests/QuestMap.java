package de.aestis.dndreloaded.Quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.aestis.dndreloaded.Main;

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
    	
    	for (Integer key : map.get(UUID).keySet())
    	{
    		HashMap<Integer, Quest> buffer = new HashMap<Integer, Quest>();
    		
    		/*
    		 * Check for incomplete
    		 * Quests first!
    		 */
    		
    		Quest q = map.get(UUID).get(key);
    		
    		if (isCompleteQuest(q))
    		{
    			Main.instance.getLogger().fine("Skipped incomplete Quest {" + q.getID() + "}!");
    			
    			buffer.put(key, q);
        		list.add(buffer);
    		} else
    		{
    			Main.instance.getLogger().warning("Skipped incomplete Quest {" + q.getID() + "}!");
    		}
    	}
    	System.out.println("Quest List Size: " + list.size());
    	return list;
    }

    public Quest getNpcQuestByID(String UUID, Integer QuestID) {
    	
        return map.get(UUID).get(QuestID);
    }
    
    public Quest getQuestByID(Integer QuestID) {

    	for (HashMap<Integer, Quest> m : map.values()) {
    		for (Integer e : m.keySet()) {
    			if (e == QuestID) return m.get(e);
    		}
    	}
    	
    	return null;
    }
    
    private boolean isCompleteQuest(Quest quest) {
		
		if (quest.getNpcID() == null) return false;
		if (quest.getFaction() == null) return false;
		if (quest.getTitle() == null) return false;
		if (quest.getIcon() == null) return false;
		if (quest.getDescription() == null) return false;
		if (quest.getShort() == null) return false;
		if (quest.getMessageAccept() == null) return false;
		if (quest.getMessageDecline() == null) return false;
		if (quest.getMessageFail() == null) return false;
		if (quest.getMessageSuccess() == null) return false;
		if (quest.getType() == null) return false;
		
		return true;
	}
}
