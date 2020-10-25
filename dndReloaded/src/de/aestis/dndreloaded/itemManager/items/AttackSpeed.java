package de.aestis.dndreloaded.itemManager.items;


/**
 *	Values SUPER_FAST and SUPER_SLOW wont be added into item Lore
 */
public enum AttackSpeed {

	SUPER_FAST(0), EXTREMLY_FAST(1), VERY_FAST(5), FAST(9), NORMAL(13), SLOW(19), VERY_SLOW(25), EXTREMELY_SLOW(30), SUPER_SLOW(60);
	
	private int cooldownTicks;
	
	private AttackSpeed(int cooldownTicks) {
		this.cooldownTicks = cooldownTicks;
	}
	
	public int getCooldownTicks() {
		return cooldownTicks;
	}
	
	public int toInt() {
		switch (this) {
		case SUPER_SLOW:
			return -4;
		case EXTREMELY_SLOW:
			return -3;
		case VERY_SLOW:
			return -2;
		case SLOW:
			return -1;
		case NORMAL:
			return 0;
		case FAST:
			return 1;
		case VERY_FAST:
			return 2;
		case EXTREMLY_FAST:
			return 3;
		case SUPER_FAST:
			return 4;
		default:
			throw new IllegalStateException(this.name() + " does not exist!");
		}
	}
	
	public static AttackSpeed fromInt(int speedInt) {
		switch (speedInt) {
		case -4:
			return SUPER_SLOW;
		case -3:
			return EXTREMELY_SLOW;
		case -2:
			return VERY_SLOW;
		case -1:
			return SLOW;
		case 0:
			return NORMAL;
		case 1:
			return FAST;
		case 2:
			return VERY_FAST;
		case 3:
			return EXTREMLY_FAST;
		case 4:
			return SUPER_FAST;
		default:
			throw new IllegalArgumentException(speedInt + " is out of bounds must be -4 to 4");
		}
	}
}
