package de.aestis.dndreloaded.itemManager.items.dataTypes;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import de.aestis.dndreloaded.itemManager.items.ItemSet;

public class SetDataType implements PersistentDataType<String, ItemSet> { 

	@Override
	public Class<String> getPrimitiveType() {
		return String.class;
	}

	@Override
	public Class<ItemSet> getComplexType() {
		return ItemSet.class;
	}

	@Override
	public String toPrimitive(ItemSet complex, PersistentDataAdapterContext context) {
		return complex.getName();
	}

	@Override
	public ItemSet fromPrimitive(String primitive, PersistentDataAdapterContext context) {
		return ItemSet.loadSet(primitive);
	}
}
