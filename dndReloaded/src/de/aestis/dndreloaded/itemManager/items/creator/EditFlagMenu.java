package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.utils.DoubleWrapper;

public class EditFlagMenu extends CustomMenu implements Closeable, Refreshable {

	private SimpleItem item;
	private ItemFlag<?> flag;
	private int intValue;
	private long longValue;
	private String stringValue;

	public EditFlagMenu(SimpleItem item, ItemFlag<?> flag) {
		super(9);
		this.item = item;
		this.flag = flag;
		setTitle("Change Flag " + flag.getName());
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		returnMap.put(8, createReturnItem(p, "Return to Flag Select"));
		
		if (flag == ItemFlag.COOLDOWN || flag == ItemFlag.ATTACK_SPEED) {
			DoubleWrapper<ItemStack, Runnable> item = createGuiItem(() -> {}, Material.BLUE_CONCRETE, "Current Setting", "Current value of the Flag", "" +intValue);
			returnMap.put(3, item);
			item = createGuiItem(() -> intValue++, Material.GREEN_CONCRETE, "Increase Flag Setting", "Increase Flag Setting by 1");
			returnMap.put(4, item);
			item = createGuiItem(() -> intValue+=10, Material.GREEN_CONCRETE, "Increase Flag Setting", "Increase Flag Setting by 10");
			returnMap.put(5, item);
			item = createGuiItem(() -> intValue+=100, Material.GREEN_CONCRETE, "Increase Flag Setting", "Increase Flag Setting by 100");
			returnMap.put(6, item);
			item = createGuiItem(() -> intValue--, Material.RED_CONCRETE, "Decrease Flag Setting", "Decrease Flag Setting by 1");
			returnMap.put(2, item);
			item = createGuiItem(() -> intValue-=10, Material.RED_CONCRETE, "Decrease Flag Setting", "Decrease Flag Setting by 10");
			returnMap.put(1, item);
			item = createGuiItem(() -> intValue-=100, Material.RED_CONCRETE, "Decrease Flag Setting", "Decrease Flag Setting by 100");
			returnMap.put(0, item);	
		}
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

	@SuppressWarnings("unchecked")
	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			if (Integer.class.isInstance(flag.getValueType())) {
				if (flag == ItemFlag.ATTACK_SPEED) {
					intValue = Math.max(-4, Math.min(intValue, 4));
				}
				item.setItemFlag((ItemFlag<Integer>) flag, intValue);
			} else if (Long.class.isInstance(flag.getValueType())) {
				item.setItemFlag((ItemFlag<Long>) flag, longValue);
			} else if (String.class.isInstance(flag.getValueType())) {
				item.setItemFlag((ItemFlag<String>) flag, stringValue);
			} else {
				throw new UnsupportedOperationException("The Flag type " + flag.getValueType() + " is unsupported!");
			}
			
		}
		
	}

}
