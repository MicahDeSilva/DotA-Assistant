package main;

import java.awt.AWTException;
import java.awt.Robot;

public class HeroLevelRecog
{
	public static int getLevel()
	{
		Robot robot;
		try
		{
			robot = new Robot();
			
			int colour = 0;
			
			if(Overlay.screenSize.width == 2560 && Overlay.screenSize.height == 1080)
				colour = robot.getPixelColor(930, 1055).getRGB() + robot.getPixelColor(929, 1055).getRGB() + robot.getPixelColor(930, 1056).getRGB() + robot.getPixelColor(929, 1056).getRGB();
			else
				return -1;
			
			System.out.println(colour);
			
			int level = getNumberFromRGB(colour);
			
			return level;
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	public static int getNumberFromRGB(int colour)
	{
		switch (colour)
		{	
			case(-24781316):
				return 1;
			case(-30254984):
				return 2;
			case(-21086349):
				return 3;
			case(-25544680):
				return 4;
			case(-24853024):
				return 5;
			case(-26247784):
				return 6;
			case(-18512721):
				return 7;
			case(-20239531):
				return 8;
			case(-21029305):
				return 9;
			case(-32166301):
				return 10;
			case(-49514492):
				return 11;
			case(-47807484):
				return 12;
			case(-47281916):
				return 13;
			case(-36643314):
				return 14;
			case(-43210488):
				return 15;
			case(-33885169):
				return 16;
			case(-47347708):
				return 17;
			case(-36051954):
				return 18;
			case(-37431028):
				return 19;
			case(-29145754):
				return 20;
			case(-42290936):
				return 21;
			case(-41043190):
				return 22;
			case(-40780791):
				return 23;
			case(-32702960):
				return 24;
			case(-38022644):
				return 25;
			
			case(-24583146):
				return 1;
			case(-29924703):
				return 2;
			case(-21085832):
				return 3;
			case(-25346236):
				return 4;
			case(-24720390):
				return 5;
			case(-26114893):
				return 6;
			case(-18446925):
				return 7;
			case(-20173220):
				return 8;
			case(-21028529):
				return 9;
			case(-31836027):
				return 10;
			case(-47014072):
				return 12;
			case(-46489016):
				return 13;
			case(-36311996):
				return 14;
			case(-42681017):
				return 15;
			case(-33554364):
				return 16;
			case(-46554809):
				return 17;
			case(-35655099):
				return 18;
			case(-37099964):
				return 19;
			case(-28881530):
				return 20;
			case(-41761466):
				return 21;
			case(-40579769):
				return 22;
			case(-40317371):
				return 23;
			case(-32372156):
				return 24;
			case(-37494458):
				return 25;
			
			default:
				System.out.println("case(" + colour + "):\n\treturn ;");
		}
		return -1;
	}
}
