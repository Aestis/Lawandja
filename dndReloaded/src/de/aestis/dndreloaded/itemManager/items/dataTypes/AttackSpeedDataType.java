package de.aestis.dndreloaded.itemManager.items.dataTypes;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import de.aestis.dndreloaded.itemManager.items.AttackSpeed;

public class AttackSpeedDataType implements PersistentDataType<Byte, AttackSpeed> {

	@Override
	public Class<Byte> getPrimitiveType() {
		return Byte.class;
	}

	@Override
	public Class<AttackSpeed> getComplexType() {
		return AttackSpeed.class;
	}

	@Override
	public Byte toPrimitive(AttackSpeed complex, PersistentDataAdapterContext context) {
		return (byte) complex.toInt();
	}

	@Override
	public AttackSpeed fromPrimitive(Byte primitive, PersistentDataAdapterContext context) {
		return AttackSpeed.fromInt(primitive);
	}
}
