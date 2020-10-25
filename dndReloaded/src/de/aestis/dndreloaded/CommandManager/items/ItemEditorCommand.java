package de.aestis.dndreloaded.CommandManager.items;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.creator.CreateItem;
import de.aestis.dndreloaded.itemManager.items.creator.ItemListMenu;
import oxolotel.inventoryMenuManager.InventoryMenuManager;

public class ItemEditorCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = null;
		if (sender instanceof Player) {
			p = (Player) sender;
		}
		String commandHelp = "/ItemEditor <create | open [group] |>";
		if (args.length < 1) {
			sender.sendMessage(commandHelp);
			return true;
		}
		if (args[0].equalsIgnoreCase("open")) {
			if (args.length < 2) {
				InventoryMenuManager.getInstance().openMenu(p, new ItemListMenu());
			} else {
				InventoryMenuManager.getInstance().openMenu(p, new ItemListMenu(ItemGroup.valueOf(String.join("_", Arrays.copyOfRange(args, 1, args.length)).toUpperCase())));
			}
			return true;
		} else if (args[0].equalsIgnoreCase("create")) {
			InventoryMenuManager.getInstance().openMenu(p, new CreateItem());
			return true;
		} else {
			sender.sendMessage(commandHelp);
			return true;
		}
	}

}
