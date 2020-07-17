package de.aestis.dndreloaded.CommandManager.Quests;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Quests.Quest;

public class QuestEditor implements CommandExecutor {

	private final Main Plugin = Main.instance;
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
			
		Player player = (Player) Bukkit.getPlayer(sender.getName());

		if (args.length < 1)
		{
			sender.sendMessage("§cMissing Arguments! Usage: /questedit <mode> [input]");
			sender.sendMessage("§cModes: <npcid>, <required>, <faction>, <minrep>, <title>, <icon>, <description>, <target>, "
								+ "<short>, <msgaccept>, <msgdecline>, <msgrunning>, <msgfail>, <msgsuccess>, <starteritem>, "
								+ "<starteritemamount>, <type>, <variable>, <questitem>, <questitemamount>, <destination>, "
								+ "<mobtype>, <blockmaterial>, <rewardxp>, <repgain>, <bonusrewardtype>, <bonusreward>");
			return true;
		}
		
		/**
		 * Command /questedit select <questid>
		 * is used to select an existing quest
		 * to further edit by the user
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("select"))
		{
			if (Integer.parseInt(args[1]) == -1)
			{
				sender.sendMessage("§cInvalid input! Use a proper QuestID!");
				return true;
			}
			
			Quest quest = Plugin.QuestData.getQuestByID(Integer.parseInt(args[1]));
			
			/*
			 * Check if requested Quest is valid
			 * or not; throws error if not existing
			 */
			
			if (quest != null)
			{
				sender.sendMessage("§6Selected Quest '§2" + quest.getTitle() + "§6' from NPC §7(" + quest.getNpcID() + ")");
				Plugin.SelectedQuest.put(player, quest);
			} else
			{
				sender.sendMessage("§cSelected Quest (" + args[1] + ") is no valid/existing Quest!");
			}
			
			return true;
		}
		
		/**
		 * Simple check afterwards if
		 * user has selected a valid Quest
		 */
		if (!Plugin.SelectedQuest.containsKey(player))
		{
			sender.sendMessage("§cNo Quest selected! Use: /questedit select <questid>");
			return true;
		}
		
		/**
		 * Command /questedit <npcid> [id]
		 * is used to select an existing quest
		 * to further edit by the user
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("npcid"))
		{
			if (args.length == 1)
			{
				/*
				 * If no specific UUID is given,
				 * use the selected NPC
				 */
				Quest quest = Plugin.SelectedQuest.get(player);
				String uuid = Plugin.SelectedNPC.get(player);
				
				Plugin.QuestData.transferQuest(quest.getNpcID(), uuid, quest.getID());
				quest.setNpcID(uuid);
				
				sender.sendMessage("§6Sucessfully transfered Quest (§2" + quest.getID() + "§6) to selected NPC!");
				
				return true;
			} else
			{
				/*
				 * If an specific UUID is given,
				 * check first, if this Entity
				 * exists, then proceed
				 */
				UUID uuid = UUID.fromString(args[1]);
				
				if (Bukkit.getEntity(uuid).isEmpty())
				{
					sender.sendMessage("§cInvalid Entity! Use an existing Entity!");
					return true;
				}
				
				Quest quest = Plugin.SelectedQuest.get(player);
				
				Plugin.QuestData.transferQuest(quest.getNpcID(), uuid.toString(), quest.getID());
				quest.setNpcID(uuid.toString());
				
				sender.sendMessage("§6Sucessfully transfered Quest (§2" + quest.getID() + "§6) to NPC §7(" + uuid + ")§6!");
				
				return true;
			}
		}
		
		/**
		 * Command /questedit <required> <id>
		 * is used to set a required quest before
		 * attempting to accept the current one
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("required"))
		{
			if (Integer.parseInt(args[1]) == -1
				|| Plugin.QuestData.getQuestByID(Integer.parseInt(args[1])) == null)
			{
				sender.sendMessage("§cInvalid input! Use a proper QuestID!");
				return true;
			}
			
			Quest quest = Plugin.SelectedQuest.get(player);
			
			quest.setRequired(Integer.parseInt(args[1]));
			
			sendChangedMessage(player, quest, args, args[1]);
			
			return true;
		}
		
		/**
		 * Command /questedit <required> <faction>
		 * is used to set which of the factions
		 * should be able to accept this quest
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("faction"))
		{
			Quest quest = Plugin.SelectedQuest.get(player);
			String input = "";
			Integer i = 0;
			
			for (String s : args)
			{
				if (i >= 1)
				{				
					if (i + 1 < args.length)
					{
						input += s + " ";
					} else
					{
						input += s;
					}	
				}
				i++;
			}
			
			quest.setFaction(input);
			
			sendChangedMessage(player, quest, args, input);
			
			return true;
		}
		
		/**
		 * Command /questedit <minrep> <reputation>
		 * is used to set how much reputation the
		 * player has to have gained so far to
		 * accept this quest
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("minrep"))
		{
			if (Integer.parseInt(args[1]) == -1)
			{
				sender.sendMessage("§cInvalid input! Use a proper integer value!");
				return true;
			}
		
			Quest quest = Plugin.SelectedQuest.get(player);
			
			quest.setMinReputation(Integer.parseInt(args[1]));
			
			sendChangedMessage(player, quest, args, args[1]);
			
			return true;
		}
		
		/**
		 * Command /questedit <title> <text>
		 * is used to set the title shown when
		 * Quest is listed or shown in selector
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("title"))
		{
			Quest quest = Plugin.SelectedQuest.get(player);
			String input = "";
			Integer i = 0;
			
			for (String s : args)
			{
				if (i >= 1)
				{				
					if (i + 1 < args.length)
					{
						input += s + " ";
					} else
					{
						input += s;
					}	
				}
				i++;
			}
			
			quest.setTitle(input);
			
			sendChangedMessage(player, quest, args, input);
			
			return true;
		}
		
		/**
		 * Command /questedit <icon> [material]
		 * is used to set the Icon e.g. Item
		 * the Quest is listed with
		 */
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("icon"))
		{
			Quest quest = Plugin.SelectedQuest.get(player);
			
			if (args.length < 2)
			{
				/*
				 * Material input via in-hand
				 */
				if (player.getInventory().getItemInMainHand() == null
					|| player.getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					sender.sendMessage("§cInvalid item in Main Hand!");
					return true;
				}
				
				Material mat = player.getInventory().getItemInMainHand().getType();
				ItemStack itm = new ItemStack(mat, 1);
				
				quest.setIcon(itm);
				sendChangedMessage(player, quest, args, itm.getType().name());
				
				return true;
			} else
			{
				/*
				 * Material input via Text/Command
				 */
				if (Material.matchMaterial(args[1]) == null)
				{
					sender.sendMessage("§cInvalid input! Use a proper Minecraft Material!");
					return true;
				}
				
				ItemStack itm = new ItemStack(Material.matchMaterial(args[1]), 1);
				
				quest.setIcon(itm);
				sendChangedMessage(player, quest, args, itm.getType().name());
				
				return true;
			}
		}
		
		return true;
	}
	
	private void sendChangedMessage(Player player, Quest quest, String[] args, String input) {
		
		player.sendMessage("§6Sucessfully set §5<" + args[0] + "> §6for Quest (§2" + quest.getTitle() + "§6) to §a'" + input + "'§6!");
	}
	
}
