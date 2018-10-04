package main;

import main.HeroPortraitRecog.HeroName;
import main.ItemDataGetter.ItemName;

public class ItemData
{
	public final float STR, AGI, INT, HP, MANA, ATK_SPD, DMG, SPELL_AMP, ARMOR, MOV_SPEED, MOV_PCENT, HP_REGEN, EVASION_PERCENT, TRUE_STRIKE,
			ATK_RANGE, MANA_REGEN, MANA_REGEN_PERCENT, LIFE_STEAL, SPELL_LS, MAG_RESIST, AURA_ARMOR, AURA_MAG_RES, AURA_ATK_SPD, AURA_MANA_REGEN,
			AURA_HP_REGEN, AURA_ENEMY_ARMOR_MINUS, AURA_DAMAGE, AURA_LS, AURA_MS, BONUS_CAST_RANGE, CRIT_CHANCE, CRIT_PERCENT;
	public final ItemName itemName;

	public ItemData(ItemName itemName, String string)
	{
		this.itemName = itemName;
		String[] itemSplit = string.split(",");

		STR = Float.parseFloat(itemSplit[0]);
		AGI = Float.parseFloat(itemSplit[1]);
		INT = Float.parseFloat(itemSplit[2]);
		HP = Float.parseFloat(itemSplit[3]);
		MANA = Float.parseFloat(itemSplit[4]);
		ATK_SPD = Float.parseFloat(itemSplit[5]);
		DMG = Float.parseFloat(itemSplit[6]);
		SPELL_AMP = Float.parseFloat(itemSplit[7]);
		ARMOR = Float.parseFloat(itemSplit[8]);
		MOV_SPEED = Float.parseFloat(itemSplit[9]);
		MOV_PCENT = Float.parseFloat(itemSplit[10]);
		HP_REGEN = Float.parseFloat(itemSplit[11]);
		EVASION_PERCENT = Float.parseFloat(itemSplit[12]);
		TRUE_STRIKE = Float.parseFloat(itemSplit[13]);
		ATK_RANGE = Float.parseFloat(itemSplit[14]);
		MANA_REGEN = Float.parseFloat(itemSplit[15]);
		MANA_REGEN_PERCENT = Float.parseFloat(itemSplit[16]);
		LIFE_STEAL = Float.parseFloat(itemSplit[17]);
		SPELL_LS = Float.parseFloat(itemSplit[18]);
		MAG_RESIST = Float.parseFloat(itemSplit[19]);
		AURA_ARMOR = Float.parseFloat(itemSplit[20]);
		AURA_MAG_RES = Float.parseFloat(itemSplit[21]);
		AURA_ATK_SPD = Float.parseFloat(itemSplit[22]);
		AURA_MANA_REGEN = Float.parseFloat(itemSplit[23]);
		AURA_HP_REGEN = Float.parseFloat(itemSplit[24]);
		AURA_ENEMY_ARMOR_MINUS = Float.parseFloat(itemSplit[25]);
		AURA_DAMAGE = Float.parseFloat(itemSplit[26]);
		AURA_LS = Float.parseFloat(itemSplit[27]);
		AURA_MS = Float.parseFloat(itemSplit[28]);
		BONUS_CAST_RANGE = Float.parseFloat(itemSplit[29]);
		CRIT_CHANCE = Float.parseFloat(itemSplit[30]);
		CRIT_PERCENT = Float.parseFloat(itemSplit[31]);

	}

}
