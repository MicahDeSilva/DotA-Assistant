package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.ItemDataGetter.ItemName;

public class HeroItemRecog
{
	static List<String> lines = null;
	static ReadWriteTextFile text;
	static String fileName;

	public static Map<Integer, ItemName> map = new HashMap<Integer, ItemName>();

	HeroItemRecog(String fileName)
	{
		HeroItemRecog.fileName = fileName;
		text = new ReadWriteTextFile();

		try
		{
			lines = text.readSmallTextFile(fileName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < lines.size(); i++)
		{
			String[] line = lines.get(i).split(":");
			map.put(Integer.parseInt(line[0]), ItemName.valueOf(line[1]));
		}
	}

	public static ItemName[] getItems()
	{
		ItemName[] items = new ItemName[6];

		Robot robot;
		try
		{
			robot = new Robot();

			for (int i = 0; i < items.length; i++)
			{
				int colour = 0;

				int xMod = (i % 3) * 65;
				int yMod = ((int) (i / 3)) * 48;

				if (Overlay.screenSize.width == 2560 && Overlay.screenSize.height == 1080)
					colour = robot.getPixelColor(1449 + xMod, 957 + yMod).getRGB() + robot.getPixelColor(1471 + xMod, 957 + yMod).getRGB() + robot.getPixelColor(1449 + xMod, 975 + yMod).getRGB() + robot.getPixelColor(1471 + xMod, 975 + yMod).getRGB() + robot.getPixelColor(1457 + xMod, 965 + yMod).getRGB();
				else
					return null;

				if (map.containsKey(colour))
					items[i] = map.get(colour);
				else
				{
					String itemName = Overlay.popUpOptions("Enter name of item in slot: " + (i + 1), ItemName.class);

					if (itemName == null)
					{
						Overlay.popUp("Item will be ignored.");
						items[i] = ItemName.Empty;
						continue;
					}
					try
					{
						addItem(colour, searchEnum(ItemName.class, itemName));
						items[i] = searchEnum(ItemName.class, itemName);
						Overlay.popUp("Item " + items[i] + " registered!");
					}
					catch (IllegalArgumentException e)
					{
						Overlay.popUp("Unrecognised item name given.");
						items[i] = ItemName.Empty;
					}

				}
			}

			return items;
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void addItem(int colorRGB, ItemName itemName)
	{
		if (map.containsKey(colorRGB))
			return;

		map.put(colorRGB, itemName);
		lines.add(colorRGB + ":" + itemName.toString());

		try
		{
			text.writeSmallTextFile(lines, fileName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static <T extends Enum<?>> T searchEnum(Class<T> enumeration, String search)
	{
		for (T each : enumeration.getEnumConstants())
		{
			if (each.name().compareToIgnoreCase(search) == 0)
			{
				return each;
			}
		}
		throw new IllegalArgumentException();
	}
}
