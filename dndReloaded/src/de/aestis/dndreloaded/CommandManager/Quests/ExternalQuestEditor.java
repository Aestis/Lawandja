package de.aestis.dndreloaded.CommandManager.Quests;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestMap;
import de.aestis.dndreloaded.Quests.editor.QuestParser;

public class ExternalQuestEditor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		if(args.length < 1) {
			return false;
		}
		if (args[0].equalsIgnoreCase("dump")) {
			QuestParser.clearQuestFolder();
			for(Quest q : Main.instance.QuestData.getAllQuests()) {
				QuestParser.storeQuest(q);
			}
			sender.sendMessage("Successfully dumped all quests into \"Quests\" folder.");
			return true;
		} else if (args[0].equalsIgnoreCase("import")) {
			QuestMap data = Main.instance.QuestData;
			DatabaseHandler db = Main.instance.getDatabaseHandler();
			
			for (Quest q : QuestParser.loadQuests()) {
				//store quest in RAM
				Quest oldQuest = data.getQuestByID(q.getID());
				data.removeQuest(oldQuest.getID());
				data.insertQuest(q.getNpcID(), q.getID(), q);
				
				//store quest in db
				if (q.getID() != -1) {
					db.saveQuestData(q);
				} else {
					//TODO store quest in DB if quest has no ID yet
				}
			}
			return true;
		}

		return false;
	}

}
