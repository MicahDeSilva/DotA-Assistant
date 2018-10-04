package main;

import java.util.Arrays;

import main.HeroPortraitRecog.HeroName;
import main.ItemDataGetter.ItemName;

public class Hero
{
	private int LVL = 1;
	float DPS, STR, AGI, INT, HP, PHYS_EHP, MAG_EHP, itemStr, itemAgi, itemInt, itemHP, itemMANA, itemATK_SPD, itemDMG, itemSPELL_AMP, itemARMOR,
			itemMOV_SPEED, itemMOV_PCENT, itemHP_REGEN, itemEVASION_PERCENT, itemTRUE_STRIKE, itemATK_RANGE, itemMANA_REGEN, itemMANA_REGEN_PERCENT,
			itemLIFE_STEAL, itemSPELL_LS, itemMAG_RESIST, itemAURA_ARMOR, itemAURA_MAG_RES, itemAURA_ATK_SPD, itemAURA_MANA_REGEN, itemAURA_HP_REGEN,
			itemAURA_ENEMY_ARMOR_MINUS, itemAURA_DAMAGE, itemAURA_LS, itemAURA_MS, itemBONUS_CAST_RANGE, itemCRIT_CHANCE, itemCRIT_PERCENT;
	public int displayDPS, displayPHYS_HP, displayMAG_HP;
	byte[] ABILITIES;
	HeroData.Attribute primaryAttribute;
	boolean heroNameCorrect = false;
	HeroName hero = HeroName.Abaddon;
	int heroLookup = HeroStatGetter.HeroNameToLookup(hero);
	public String displayABILITIES;
	public long lastUpdate = System.currentTimeMillis();
	ItemName[] items = new ItemName[6];

	public Hero()
	{
		for (int i = 0; i < 6; i++)
		{
			items[i] = ItemName.Empty;
		}
	}

	private void update()
	{
		resetItemStats();
		for (ItemName item : items)
		{
			if (item == null)
				item = ItemName.Empty;
			ItemData itemData = ItemDataGetter.itemDataPool[ItemDataGetter.ItemNameToLookup(item)];
			itemStr += itemData.STR;
			itemAgi += itemData.AGI;
			itemInt += itemData.INT;
			itemHP += itemData.HP;
			itemMANA += itemData.MANA;
			itemATK_SPD += itemData.ATK_SPD;
			itemDMG += itemData.DMG;
			itemSPELL_AMP += itemData.SPELL_AMP;
			itemARMOR += itemData.ARMOR;
			itemMOV_SPEED += itemData.MOV_SPEED;
			itemMOV_PCENT += itemData.MOV_PCENT;
			itemHP_REGEN += itemData.HP_REGEN;
			itemEVASION_PERCENT += itemData.EVASION_PERCENT;
			itemTRUE_STRIKE += itemData.TRUE_STRIKE;
			itemATK_RANGE += itemData.ATK_RANGE;
			itemMANA_REGEN += itemData.MANA_REGEN;
			itemMANA_REGEN_PERCENT += itemData.MANA_REGEN_PERCENT;
			itemLIFE_STEAL += itemData.LIFE_STEAL;
			itemSPELL_LS += itemData.SPELL_LS;
			itemMAG_RESIST += itemData.MAG_RESIST;
			itemAURA_ARMOR += itemData.AURA_ARMOR;
			itemAURA_MAG_RES += itemData.AURA_MAG_RES;
			itemAURA_ATK_SPD += itemData.AURA_ATK_SPD;
			itemAURA_MANA_REGEN += itemData.AURA_MANA_REGEN;
			itemAURA_HP_REGEN += itemData.AURA_HP_REGEN;
			itemAURA_ENEMY_ARMOR_MINUS += itemData.AURA_ENEMY_ARMOR_MINUS;
			itemAURA_DAMAGE += itemData.AURA_DAMAGE;
			itemAURA_LS += itemData.AURA_LS;
			itemAURA_MS += itemData.AURA_MS;
			itemBONUS_CAST_RANGE += itemData.BONUS_CAST_RANGE;
			itemCRIT_CHANCE += itemData.CRIT_CHANCE;
			itemCRIT_PERCENT += itemData.CRIT_PERCENT;
		}
		STR = HeroStatGetter.bigHeroDataPool[heroLookup].STRENGTH + HeroStatGetter.bigHeroDataPool[heroLookup].STRENGTH_GAIN * (LVL - 1) + itemStr;
		AGI = HeroStatGetter.bigHeroDataPool[heroLookup].AGILITY + HeroStatGetter.bigHeroDataPool[heroLookup].AGILITY_GAIN * (LVL - 1) + itemAgi;
		INT = HeroStatGetter.bigHeroDataPool[heroLookup].INTELLIGENCE + HeroStatGetter.bigHeroDataPool[heroLookup].INTELLIGENCE_GAIN * (LVL - 1) + itemInt;
		primaryAttribute = HeroStatGetter.bigHeroDataPool[heroLookup].primaryAttribute;
		DPS = getDamage() * getAttacksPerSecond();
		HP = 200 + STR * 20 + itemHP;
		PHYS_EHP = HP * (1 + (HeroStatGetter.bigHeroDataPool[heroLookup].BASE_ARMOR + (AGI / 7) + itemARMOR + itemAURA_ARMOR) * 0.06f);
		MAG_EHP = HP / (1 - HeroStatGetter.bigHeroDataPool[heroLookup].MAG_RESIST);

		ABILITIES = HeroStatGetter.bigHeroDataPool[heroLookup].getAbilityLevels(LVL);

		lastUpdate = System.currentTimeMillis();

		displayPHYS_HP = Math.round(PHYS_EHP);
		displayMAG_HP = Math.round(MAG_EHP);
		displayDPS = Math.round(getDamage());
		displayABILITIES = Arrays.toString(ABILITIES);
	}

	private float getDamage()
	{
		switch (primaryAttribute)
		{
			case STRENGTH:
				return HeroStatGetter.bigHeroDataPool[heroLookup].BASE_ATK_DAMAGE + STR + itemDMG;
			case AGILITY:
				return HeroStatGetter.bigHeroDataPool[heroLookup].BASE_ATK_DAMAGE + AGI + itemDMG;
			default:
				return HeroStatGetter.bigHeroDataPool[heroLookup].BASE_ATK_DAMAGE + INT + itemDMG;

		}
	}

	private float getAttacksPerSecond()
	{
		return ((100 + AGI + itemATK_SPD + itemAURA_ATK_SPD) * 0.01f) / HeroStatGetter.bigHeroDataPool[heroLookup].BASE_ATK_TIME;
	}

	public void setHero(HeroName hero)
	{
		heroNameCorrect = true;
		this.hero = hero;
		heroLookup = HeroStatGetter.HeroNameToLookup(hero);
		System.out.println(hero);
		update();
	}

	public void setItems(ItemName[] items)
	{
		this.items = items;
		update();
	}

	public void setLevel(int lvl)
	{
		if (lvl > 0)
			LVL = lvl;
		update();
	}

	public int getLevel()
	{
		return LVL;
	}

	void resetItemStats()
	{
		itemStr = 0;
		itemAgi = 0;
		itemInt = 0;
		itemHP = 0;
		itemMANA = 0;
		itemATK_SPD = 0;
		itemDMG = 0;
		itemSPELL_AMP = 0;
		itemARMOR = 0;
		itemMOV_SPEED = 0;
		itemMOV_PCENT = 0;
		itemHP_REGEN = 0;
		itemEVASION_PERCENT = 0;
		itemTRUE_STRIKE = 0;
		itemATK_RANGE = 0;
		itemMANA_REGEN = 0;
		itemMANA_REGEN_PERCENT = 0;
		itemLIFE_STEAL = 0;
		itemSPELL_LS = 0;
		itemMAG_RESIST = 0;
		itemAURA_ARMOR = 0;
		itemAURA_MAG_RES = 0;
		itemAURA_ATK_SPD = 0;
		itemAURA_MANA_REGEN = 0;
		itemAURA_HP_REGEN = 0;
		itemAURA_ENEMY_ARMOR_MINUS = 0;
		itemAURA_DAMAGE = 0;
		itemAURA_LS = 0;
		itemAURA_MS = 0;
		itemBONUS_CAST_RANGE = 0;
		itemCRIT_CHANCE = 0;
		itemCRIT_PERCENT = 0;
	}
}
