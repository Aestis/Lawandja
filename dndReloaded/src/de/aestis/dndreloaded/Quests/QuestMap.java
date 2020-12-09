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
    
    public boolean isEmpty() {
    	
    	if (map.isEmpty()) return true;
    	return false;
    }
    
    public void clear() {
    	
    	map.clear();
    }
    
    /**
     * Gets the count of all currently
     * loaded Quests in local storage
     * @return Value
     */
    public Integer size() {
    	
    	if (map.isEmpty()) return -1;
    	
    	Integer size = 0;
    	for (HashMap<Integer, Quest> key : map.values())
    	{
    		size += key.values().size();
    	}
    	
    	return size;
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
     * Returns all Quests that are currently
     * set up in local storage
     * @return List of Quests
     */
    public List<Quest> getAllQuests() {
    	
    	if (!this.isInitialized()) return null;
    	
    	List<Quest> list = new ArrayList<Quest>();
    	
    	for (HashMap<Integer, Quest> key : map.values())
    	{
    		for (Quest q : key.values())
    		{
    			list.add(q);
    		}
    	}
    	System.out.println("Quest List Size: " + list.size());
    	return list;
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
    	
    	for (HashMap<Integer, Quest> m : map.values())
    	{
    		for (Integer e : m.keySet())
    		{
    			if (e == QuestID) return m.get(e);
    		}
    	}
    	
    	return null;
    }
    
    public boolean transferQuest(String UUIDold, String UUIDnew, Integer QuestID) {
    	
    	if (!hasQuests(UUIDold)) return false;
    	
    	Quest q = getQuestByID(QuestID);
    	insertQuest(UUIDnew, QuestID, q);
    	
    	map.get(UUIDold).remove(QuestID);
    	
    	if (map.get(UUIDold).isEmpty()) map.remove(UUIDold);
    	
    	return true;
    }
    
    /**
     * Removes the quest given by the Id from this QuestMap
     * @param questID the id of the quest to remove
     * @return the quest that was registered under this id or null if none present
     */
    public Quest removeQuest(Integer questID) {
    	for (String uuid:map.keySet()) {
    		Quest q = map.get(uuid).remove(questID);
    		if (map.get(uuid).isEmpty()) {
    			map.remove(uuid);
    		}
    		return q;
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
		System.out.println("npc");
    	if (quest.getNpcID() == null) return false;
    	System.out.println("faction");
		if (quest.getFaction() == null) return false;
		System.out.println("title");
		if (quest.getTitle() == null) return false;
		System.out.println("icon");
		if (quest.getIcon() == null) return false;
		System.out.println("description");
		if (quest.getDescription() == null) return false;
		System.out.println("short");
		if (quest.getShort() == null) return false;
		System.out.println("accept");
		if (quest.getMessageAccept() == null) return false;
		System.out.println("decline");
		if (quest.getMessageDecline() == null) return false;
		System.out.println("fail");
		if (quest.getMessageFail() == null) return false;
		System.out.println("success");
		if (quest.getMessageSuccess() == null) return false;
		System.out.println("type");
		if (quest.getType() == null) return false;
		System.out.println("xp");
		if (quest.getRewardXP() == null) return false;
		System.out.println("rep");
		if (quest.getReputationGain() == null) return false;
		System.out.println("complete");
		if (quest.getCompletionText() == null) return false;
		System.out.println("follow");
		if (quest.getDoesFollow() == null) return false;
		System.out.println("creator");
		if (quest.getCreator() == null) return false;
		System.out.println("created");
		if (quest.getCreated() == null) return false;
		System.out.println("active : " + quest.getActive());
		if (quest.getActive() == null || !quest.getActive())
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
