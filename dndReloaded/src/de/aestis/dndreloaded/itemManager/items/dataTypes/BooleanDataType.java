package de.aestis.dndreloaded.itemManager.items.dataTypes;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class BooleanDataType implements PersistentDataType<Byte, Boolean> {

	
	@Override
	public Class<Byte> getPrimitiveType() {
		return Byte.class;
	}

	@Override
	public Class<Boolean> getComplexType() {
		return Boolean.class;
	}

	@Override
	public Byte toPrimitive(Boolean complex, PersistentDataAdapterContext context) {
		if (complex == null) {
			return (byte) -1;
		} else {
			if (complex) {
				return (byte) 1;
			} else {
				return (byte) 0;
			}
		}
	}

	@Override
	public Boolean fromPrimitive(Byte primitive, PersistentDataAdapterContext context) {
		if (primitive == -1) {
			return null;
		} else {
			if (primitive == 0) {
				return Boolean.FALSE;
			} else if (primitive == 1){
				return Boolean.TRUE;
			} else {
				return null;
			}
		}
	}
}
