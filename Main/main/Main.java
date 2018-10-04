package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import main.HeroPortraitRecog.HeroName;
import main.ItemDataGetter.ItemName;

public class Main implements NativeKeyListener
{
	public static final int TOTAL_HERO_COUNT = 113;

	private static Robot robot = null;
	public static String hero = "";
	boolean enabled = true;
	public static boolean radiant = true;
	// Transient avoids memory duplication
	public Overlay overlay;
	public Hero[] heroes = new Hero[10];
	public static boolean debug = false;
	public boolean homeKeyPressed = false;

	public Main() throws IOException
	{
		// Disables logging of jnativehook
		LogManager.getLogManager().reset();
		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		ParseHeroStatsAndAbilities();
		ParseItemStats("Resources/ItemColors.txt");
		for (int i = 0; i < heroes.length; i++)
		{
			heroes[i] = new Hero();
		}
		overlay = new Overlay(this);
		robot = initRobotAndHook();
	}

	private static void ParseItemStats(String fileName) throws IOException
	{
		new HeroItemRecog(fileName);
		ItemDataGetter parser = new ItemDataGetter("Resources/ItemStats.txt");
		try
		{
			parser.processLineByLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException
	{
		new Main();
	}

	public void nativeKeyPressed(NativeKeyEvent e)
	{
		// System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		if (enabled)
		{
			// Disable
			if (e.getKeyCode() == NativeKeyEvent.VC_PAGE_DOWN)
			{
				enabled = false;
				overlay.setVisible(false);
			}

			if (e.getKeyCode() == NativeKeyEvent.VC_SCROLL_LOCK)
				debug = !debug;

			// Hero Specific Actions
			heroChecks(e);

			// Check if a level check was initiated
			levelChecks(e);

			// Check Hero Portraits, Register Heroes & Update UI
			if (e.getKeyCode() == NativeKeyEvent.VC_HOME)
			{
				homeKeyPressed = true;
				overlay.setVisible(false);
				ScanHeroes();
				overlay.setVisible(true);
			}

			// Check Items
			if (e.getKeyCode() == NativeKeyEvent.VC_DELETE)
			{
				overlay.setVisible(false);
				System.out.println(Arrays.toString(HeroItemRecog.getItems()));
				overlay.setVisible(true);
			}

			// Check friendly levels, items and update UI
			if (e.getKeyCode() == NativeKeyEvent.VC_END)
			{
				overlay.setVisible(false);
				GetFriendlyLevelsAndItems();
				overlay.setVisible(true);
			}

			// Exit if 0 pressed
			if (e.getKeyCode() == NativeKeyEvent.VC_0 && e.getKeyLocation() != NativeKeyEvent.KEY_LOCATION_NUMPAD)
			{
				try
				{
					GlobalScreen.unregisterNativeHook();
					System.exit(0);
				}
				catch (NativeHookException e1)
				{
					e1.printStackTrace();
				}
			}
		}

		if (e.getKeyCode() == NativeKeyEvent.VC_PAGE_UP)
		{
			enabled = true;
			overlay.setVisible(true);
		}
	}

	private int keyToNum(int keyCode)
	{
		switch (keyCode)
		{
			case NativeKeyEvent.VC_0:
				return 0;
			case NativeKeyEvent.VC_1:
				return 1;
			case NativeKeyEvent.VC_2:
				return 2;
			case NativeKeyEvent.VC_3:
				return 3;
			case NativeKeyEvent.VC_4:
				return 4;
			case NativeKeyEvent.VC_5:
				return 5;
			case NativeKeyEvent.VC_6:
				return 6;
			case NativeKeyEvent.VC_7:
				return 7;
			case NativeKeyEvent.VC_8:
				return 8;
			case NativeKeyEvent.VC_9:
				return 9;
			default:
				return -1;
		}
	}

	private void levelChecks(NativeKeyEvent e)
	{
		if (e.getKeyLocation() == NativeKeyEvent.KEY_LOCATION_NUMPAD)
		{
			int key = keyToNum(e.getKeyCode());
			if (key >= 0)
			{
				overlay.setVisible(false);
				heroes[keyToNum(e.getKeyCode())].setLevel(HeroLevelRecog.getLevel());
			}
		}

	}

	private void GetFriendlyLevelsAndItems()
	{
		// Click on all friendly portraits and perform image recognition on level number
		if (radiant)
		{
			for (int i = 0; i < 5; i++)
			{
				robot.mouseMove(885 + 63 * i, 20);
				robot.delay(20);
				robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
				robot.delay(10);
				robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
				robot.delay(50);
				try
				{
					Thread.sleep(70);
				}
				catch (Exception e)
				{
				}
				heroes[i].setLevel(HeroLevelRecog.getLevel());
				heroes[i].items = HeroItemRecog.getItems();
			}
		}
		else
		{
			for (int i = 0; i < 5; i++)
			{
				robot.mouseMove(1409 + 63 * i, 20);
				robot.delay(20);
				robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
				robot.delay(10);
				robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
				robot.delay(50);
				try
				{
					Thread.sleep(70);
				}
				catch (Exception e)
				{
				}
				heroes[i].setLevel(HeroLevelRecog.getLevel());
				System.out.println(Arrays.toString(HeroItemRecog.getItems()));
			}
		}
	}

	private void ScanHeroes()
	{
		HeroPortraitRecog.HeroName[] heroENUMS = HeroPortraitRecog.heroRecog();

		for (int i = 0; i < heroes.length; i++)
		{
			if (heroENUMS[i] != null)
				heroes[i].setHero(heroENUMS[i]);
			else if (debug)
			{
				heroes[i].setHero(HeroName.values()[new Random().nextInt(HeroName.values().length)]);
			}
		}

	}

	public void nativeKeyReleased(NativeKeyEvent e)
	{
		// System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e)
	{
		// System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
	}

	private Robot initRobotAndHook()
	{
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}

		try
		{
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException e)
		{
			e.printStackTrace();
		}

		GlobalScreen.addNativeKeyListener(this);

		return robot;
	}

	public void heroChecks(NativeKeyEvent e)
	{
		if (e.getKeyCode() == NativeKeyEvent.VC_SEMICOLON)
			RuneSpam();

		if (hero == "sf")
		{
			if (e.getKeyCode() == NativeKeyEvent.VC_T)
				EulsBlink();
		}

		if (hero == "meepo")
		{
			if (e.getKeyCode() == NativeKeyEvent.VC_E)
				BlinkPoof();
		}
	}

	public void EulsBlink()
	{
		robot.delay(10);
		robot.keyPress('V');
		robot.keyRelease('V');
		robot.delay(90);
		robot.keyPress('S');
		robot.delay(10);
		robot.keyRelease('S');
		robot.delay(550);
		robot.keyPress('F');
		robot.delay(10);
		robot.keyRelease('F');
		robot.delay(100);
		robot.keyPress('S');
		robot.delay(10);
		robot.keyRelease('S');
		robot.delay(50);
		robot.keyPress('D');
		robot.keyRelease('D');
		robot.keyPress('R');
		robot.keyRelease('R');
	}

	public void RuneSpam()
	{
		for (int i = 0; i < 100; i++)
		{
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.delay(50);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			robot.delay(50);
		}
	}

	public void BlinkPoof()
	{
		for (int i = 1; i < 5; i++)
		{
			robot.delay(10);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(10);
			robot.keyPress('W');
			robot.keyRelease('W');
		}
		robot.delay(10);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(1320);
		robot.keyPress('F');
		robot.delay(10);
		robot.keyRelease('F');
	}

	private static void ParseHeroStatsAndAbilities()
	{
		HeroStatGetter parser = new HeroStatGetter("Resources/HeroStats.txt");
		try
		{
			parser.processLineByLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		HeroAbilityGetter parser2 = new HeroAbilityGetter("Resources/HeroAbilities.txt");
		try
		{
			parser2.processLineByLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}