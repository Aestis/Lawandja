package de.aestis.dndreloaded.itemManager.items;

import java.util.Objects;

import org.bukkit.Material;

public class ItemID implements Comparable<ItemID>{
	
	private ItemGroup group;
	private String itemName;
	private Material material;
	
	public ItemID(ItemGroup group, Material material, String itemName) {
		this.group = group;
		this.itemName = itemName;
		this.material = material;
	}
	
	public static ItemID valueOf(String stringRepresentation) {
		stringRepresentation = stringRepresentation.substring(1, stringRepresentation.length()-1);
		String[] parts = stringRepresentation.split(",");
		return new ItemID(ItemGroup.valueOf(parts[0]),Material.valueOf(parts[1]),parts[2]);
	}

	/**
	 * @return the group of the item
	 */
	public ItemGroup getGroup() {
		return group;
	}

	/**
	 * @return the name of the item. Must not be the same as the Spigot items name
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * @return gets the Material of the Item
	 */
	public Material getMaterial() {
		return material;
	}
	
	@Override
	public String toString() {
		return "[" + group.name() + "," + material.name() + "," + itemName + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof ItemID) {
			ItemID other = (ItemID) obj;
			return group == other.group && itemName.equals(other.itemName) && this.material == other.material;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(group, itemName, material);
	}

	public int compareTo(ItemID other) {
		if (this.group != other.group) {
			return group.name().compareTo(other.group.name());
		} else if (this.material != other.material) {
			return material.name().compareTo(other.material.name());
		} else {
			return this.itemName.compareTo(other.itemName);
		}
	}
}
