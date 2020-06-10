package de.aestis.dndreloaded.Players.Professions.Recipes;

import org.bukkit.inventory.ItemStack;

public class CustomRecipe {

	private final Integer ID;
	
	private ItemStack Slot1;
	private ItemStack Slot2;
	private ItemStack Slot3;
	private ItemStack Slot4;
	private ItemStack Slot5;
	private ItemStack Slot6;
	private ItemStack Slot7;
	private ItemStack Slot8;
	private ItemStack Slot9;
	private ItemStack Output;

	public CustomRecipe (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public ItemStack getSlot1() {return this.Slot1;}
	public void setSlot1(ItemStack Slot1) {this.Slot1 = Slot1;}
	
	public ItemStack getSlot2() {return this.Slot2;}
	public void setSlot2(ItemStack Slot2) {this.Slot2 = Slot2;}
	
	public ItemStack getSlot3() {return this.Slot3;}
	public void setSlot3(ItemStack Slot3) {this.Slot3 = Slot3;}
	
	public ItemStack getSlot4() {return this.Slot4;}
	public void setSlot4(ItemStack Slot4) {this.Slot4 = Slot4;}
	
	public ItemStack getSlot5() {return this.Slot5;}
	public void setSlot5(ItemStack Slot5) {this.Slot5 = Slot5;}
	
	public ItemStack getSlot6() {return this.Slot6;}
	public void setSlot6(ItemStack Slot6) {this.Slot6 = Slot6;}
	
	public ItemStack getSlot7() {return this.Slot7;}
	public void setSlot7(ItemStack Slot7) {this.Slot7 = Slot7;}
	
	public ItemStack getSlot8() {return this.Slot8;}
	public void setSlot8(ItemStack Slot8) {this.Slot8 = Slot8;}
	
	public ItemStack getSlot9() {return this.Slot9;}
	public void setSlot9(ItemStack Slot9) {this.Slot9 = Slot9;}
	
	public ItemStack getOutput() {return this.Output;}
	public void setOutput(ItemStack Output) {this.Output = Output;}
	
}
