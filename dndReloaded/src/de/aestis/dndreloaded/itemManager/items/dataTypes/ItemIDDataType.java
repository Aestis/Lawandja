package de.aestis.dndreloaded.itemManager.items.dataTypes;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import de.aestis.dndreloaded.itemManager.items.ItemID;

public class ItemIDDataType implements PersistentDataType<String, ItemID> { 

	@Override
	public Class<String> getPrimitiveType() {
		return String.class;
	}

	@Override
	public Class<ItemID> getComplexType() {
		return ItemID.class;
	}

	@Override
	public String toPrimitive(ItemID complex, PersistentDataAdapterContext context) {
		return complex.toString();
	}

	@Override
	public ItemID fromPrimitive(String primitive, PersistentDataAdapterContext context) {
		return ItemID.valueOf(primitive);
	}
}
