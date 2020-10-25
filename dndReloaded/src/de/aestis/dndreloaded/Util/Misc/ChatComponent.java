package de.aestis.dndreloaded.Util.Misc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class ChatComponent {

	@SuppressWarnings("deprecation")
	public static TextComponent ComponentBuilder(String message, Boolean isBold, ChatColor messageColor, String textOnHover, String runCommandOnClick) {
		
		TextComponent cmp = new TextComponent();
		
		cmp.setText(message);
		cmp.setBold(isBold);
		cmp.setColor(messageColor);
		
		if (textOnHover.length() > 0) cmp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(textOnHover).create()));
		if (runCommandOnClick.length() > 0) cmp.setClickEvent(new ClickEvent(Action.RUN_COMMAND, runCommandOnClick));
		
		return cmp;
	}
}
