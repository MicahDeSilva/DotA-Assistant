package pull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import main.HeroPortraitRecog;
import main.HeroPortraitRecog.HeroName;

public class DownloadPage
{

	public static void main(String[] args) throws IOException
	{
		for (HeroName hero : HeroName.values())
		{
			String currentHero = hero.toString().toLowerCase().replace("_", "-");
			
			try
			{
				// We pull data from a cached dotabuff page (cached to it is just text we are dealing with)
				URL url = new URL("http://webcache.googleusercontent.com/search?q=cache:https://www.dotabuff.com/heroes/" + currentHero + "&num=1&hl=en&gl=uk&strip=1&vwsrc=0");

				// Get the input stream through URL Connection
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.addRequestProperty("User-Agent", "Mozilla/4.76");
				InputStream is = con.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String line = null;

				System.out.print("\n" + hero.toString() + ":");
				
				// read each line and write to System.out
				while ((line = br.readLine()) != null)
				{
					if (line.contains("Most Popular Ability Build"))
					{
						String[] l = line.split("<a href=\"/heroes/" + currentHero + "/abilities\">");
						for (int i = l.length - 5; i < l.length - 1; i++)
						{
							String[] k = l[i].split("entry choice");
							for (int j = 1; j < k.length; j++)
							{
								if (k[j].split(">.<").length != 1)
								{
									int begin = k[j].split(">.<")[0].length() + 1;
									System.out.print(k[j].substring(begin, begin + 1) + ",");
								}
								if (k[j].split(">..<").length != 1)
								{
									int begin = k[j].split(">..<")[0].length() + 1;
									System.out.print(k[j].substring(begin, begin + 2) + ",");
								}
							}
						}
					}

				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Could not pull data for hero " + currentHero);
			}
		}
	}
}