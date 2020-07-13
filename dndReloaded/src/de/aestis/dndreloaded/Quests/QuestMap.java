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

    /**
     * Simple check if there's any Quests
     * loaded currently into this QuestMap
     * @return true/false
     */
    private boolean isInitialized() {
    	
    	if (map == null ||
			map.size() == 0)
    	{
    		return false;
    	} else
    	{
    		return true;
    	}
    }
    
    public void clear() {
    	
    	map.clear();
    }
    
    public Integer size() {
    	
    	if (map.isEmpty()) return -1;
    	
    	return map.size();
    }
    
    /**
     * Inserts Quest into the locally stored
     * QuestMap and automatically places them 
     * into an own list for the specified NPC
     * @param UUID (Entities UUID)
     * @param QuestID (Index of the Quest)
     * @param Quest (Quest data itself)
     */
    public void insertQuest(String UUID, Integer QuestID, Quest Quest){
    	
        if(map.get(UUID) == null) map.put(UUID, new HashMap<Integer, Quest>());
        map.get(UUID).put(QuestID, Quest);
    }
    
    /**
     * Checks if the NPC/Entity currently
     * has some Quests stored in local storage
     * @param UUID (Entities UUID)
     * @return true/false
     */
    public boolean hasQuests(String UUID) {
    	
    	if (!this.isInitialized()) return false;
    	
    	if (map.containsKey(UUID)) return true;
    	return false;
    }
    
    /**
     * Returns a complete List of all currently
     * available Quests assigned to this NPC/Entity
     * @param UUID (Entities UUID)
     * @return List of Quests
     */
    public List<HashMap<Integer, Quest>> getAllQuestsFromNPC(String UUID) {
    	
    	if (!this.isInitialized()) return null;
    	
    	List<HashMap<Integer, Quest>> list = new ArrayList<HashMap<Integer, Quest>>();
    	
    	for (Integer key : map.get(UUID).keySet())
    	{
    		HashMap<Integer, Quest> buffer = new HashMap<Integer, Quest>();
    		
    		/*
    		 * Checks for incomplete Quests to
    		 * prevent errors on further handling!
    		 */
    		Quest q = map.get(UUID).get(key);
    		
    		if (isCompleteQuest(q))
    		{
    			Main.instance.getLogger().fine("Skipped incomplete Quest {" + q.getID() + "}!");
    			
    			buffer.put(key, q);
        		list.add(buffer);
    		} else
    		{
    			/*
    			 * Quest is incomplete and therefore gets
    			 * excluded from this list (can still be
    			 * accessed via /questinfo)
    			 */
    			Main.instance.getLogger().warning("Skipped incomplete Quest {" + q.getID() + "}!");
    		}
    	}
    	System.out.println("Quest List Size: " + list.size());
    	return list;
    }

    /**
     * Checks for a specific Quest by ID from
     * a specific NPC/Entity by ID
     * @param UUID (Entites UUID)
     * @param QuestID (Index of Quest)
     * @return Quest
     */
    public Quest getNpcQuestByID(String UUID, Integer QuestID) {
    	
    	if (!this.isInitialized()) return null;
    	
        return map.get(UUID).get(QuestID);
    }
    
    /**
     * Checks for a specific Quest by ID from
     * all currently available NPCs
     * @param QuestID (Index of Quest)
     * @return Quest
     */
    public Quest getQuestByID(Integer QuestID) {

    	if (!this.isInitialized()) return null;
    	
    	for (HashMap<Integer, Quest> m : map.values()) {
    		for (Integer e : m.keySet()) {
    			if (e == QuestID) return m.get(e);
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Little helper to specify if a Quest truly is
     * complete or not set up properly (right now)
     * @param quest (Quest to check)
     * @return true/false
     */
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
		if (quest.getRewardXP() == null) return false;
		if (quest.getReputationGain() == null) return false;
		if (quest.getCompletionText() == null) return false;
		if (quest.getDoesFollow() == null) return false;
		if (quest.getCreator() == null) return false;
		if (quest.getCreated() == null) return false;
		if (quest.getActive() == false ||
			quest.getActive() == null)
		{
			return false;
		}
		
		/*
		 * If everything seems to be fine this far,
		 * Quest should be set up properly!
		 */
		return true;
	}
}
