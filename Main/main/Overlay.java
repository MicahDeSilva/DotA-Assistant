package main;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Overlay
{
	private static JFrame frame;

	private static JPanel radiant;
	private static JPanel radiantInfo;
	private static JPanel dire;
	private static JPanel direInfo;
	private static GridBagConstraints infoConstraints = new GridBagConstraints();

	private Main main;

	public static Dimension screenSize;

	public Overlay(Main main)
	{
		this.main = main;

		// Basic initialisation
		frame = new JFrame("Transparent Window");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setAlwaysOnTop(true);
		frame.setFocusable(false);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(screenSize);
		frame.setLayout(new GridLayout(1, 3));
		frame.setFocusableWindowState(false);

		// Setting up layouts
		infoConstraints.fill = GridBagConstraints.NONE;
		infoConstraints.weightx = 1;

		JLabel lbl = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		JLabel lbl1 = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		JLabel lbl2 = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		JLabel lbl3 = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		JLabel lbl4 = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		JLabel lbl5 = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		JLabel lbl6 = new JLabel(new ImageIcon("lib/liblogo.jpg"));
		lbl.setPreferredSize(screenSize);
		// frame.add(lbl);

		radiant = new JPanel(new GridBagLayout());
		radiantInfo = new JPanel();
		radiantInfo.setLayout(new BoxLayout(radiantInfo, BoxLayout.Y_AXIS));
		radiantInfo.setPreferredSize(screenSize);
		setupPanel(radiantInfo);
		infoConstraints.anchor = GridBagConstraints.WEST;
		radiantInfo.add(createLabel(JLabel.LEFT, true));
		radiant.add(radiantInfo, infoConstraints);
		setupPanel(radiant);
		frame.add(radiant);

		dire = new JPanel(new GridBagLayout());
		direInfo = new JPanel();
		direInfo.setLayout(new BoxLayout(direInfo, BoxLayout.Y_AXIS));
		direInfo.setPreferredSize(screenSize);
		setupPanel(dire);
		infoConstraints.anchor = GridBagConstraints.EAST;
		dire.add(direInfo, infoConstraints);
		direInfo.add(createLabel(JLabel.RIGHT, false));
		setupPanel(direInfo);
		frame.add(dire);

		/**
		 * Without this, the window is draggable from any non transparent point, including points inside textboxes. (May be only necessary for Macs)
		 */
		// frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		new UpdateThread().start();
	}

	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
		// if (visible)
		// frame.repaint();
	}

	public static String popUpUserInput(String title)
	{
		return JOptionPane.showInputDialog(frame, title);
	}

	public static void popUp(String title)
	{
		JOptionPane.showMessageDialog(frame, title);
	}

	public static <T> String popUpOptions(String title, Class<T> enumeration)
	{
		Object[] selectionOptions = enumeration.getEnumConstants();
		Object selection = JOptionPane.showInputDialog(frame, title, title, JOptionPane.PLAIN_MESSAGE, null, selectionOptions, selectionOptions[0]);
		if (selection != null)
			return selection.toString();
		return null;
	}

	/**
	 * Sets up a panel with transparency fixes.
	 * 
	 * @param panel
	 *            The panel being set up.
	 */
	private static void setupPanel(JPanel panel)
	{
		panel.setBackground(new Color(0, 0, 0, 0));
	}

	private boolean initInfo = false;
	private ArrayList<JPanel> heroDataPanels = new ArrayList<JPanel>();
	private ArrayList<JLabel[]> allLabels = new ArrayList<>();

	private void update()
	{
		if (!initInfo)
		{
			radiantInfo.removeAll();
			direInfo.removeAll();
			allLabels.clear();
			for (int i = 0; i < main.heroes.length; i++)
			{
				JLabel[] labels = new JLabel[3];
				JPanel heroInfo = new JPanel(new GridLayout(0, 1));
				int alignment = (i < main.heroes.length / 2) ? JLabel.LEFT : JLabel.RIGHT;
				heroInfo.add(labels[0] = createLabel(JLabel.CENTER, false));
				heroInfo.add(labels[1] = createLabel(alignment, false));
				heroInfo.add(labels[2] = createLabel(alignment, false));
				labels[0].setText("  No current data!  ");
				labels[1].setText("  Press Home to start.  ");
				labels[2].setText("  Good Luck Have Fun ;)  ");
				allLabels.add(labels);
				heroInfo.setBackground(new Color(0, 0, 0, 128));
				heroInfo.setOpaque(true);
				heroDataPanels.add(heroInfo);

				JPanel space = new JPanel();
				space.setPreferredSize(new Dimension(0, 200));
				setupPanel(space);
				if (i < main.heroes.length / 2)
				{
					radiantInfo.add(heroInfo);
					radiantInfo.add(space);
				}
				else
				{
					direInfo.add(heroInfo);
					direInfo.add(space);
				}
			}
			initInfo = true;
			frame.pack();
		}

		if (main.homeKeyPressed)
		{
			for (int i = 0; i < main.heroes.length; i++)
			{
				Hero hero = main.heroes[i];
				JLabel[] labels = allLabels.get(i);
				heroDataPanels.get(i).setVisible(hero.heroNameCorrect);
				if (hero.heroNameCorrect)
				{
					labels[0].setText("  " + i + ": " + main.heroes[i].hero.toString() + " (LU: " + String.format("%1$.1f", (System.currentTimeMillis() - main.heroes[i].lastUpdate) * 1.667E-5) + " mins)  ");
					labels[1].setText("  Lvl: " + main.heroes[i].getLevel() + "    DPS: " + main.heroes[i].displayDPS + "    PhysHP: " + main.heroes[i].displayPHYS_HP + "  ");
					labels[2].setText("  Abilities: " + main.heroes[i].displayABILITIES + " MagHP: " + main.heroes[i].displayMAG_HP + "  ");

					labels[0].setForeground(indexToColour(i));
					labels[1].setForeground(indexToColour(i));
					labels[2].setForeground(indexToColour(i));
				}
			}
		}

		// Make frame visible after previous hiding
	}

	public static JLabel createLabel(int horAlign, boolean left)
	{
		JLabel label = new JLabel("", horAlign);
		label.setBackground(new Color(0, 0, 0, 0));
		label.setOpaque(true);
		label.setForeground(new Color(255, 255, 255, 255));
		label.setAlignmentX(left ? Component.LEFT_ALIGNMENT : Component.RIGHT_ALIGNMENT);
		label.setPreferredSize(screenSize);

		return label;
	}

	private class UpdateThread extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				update();
				frame.repaint(0, screenSize.height / 2, 200, 200);
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private Color indexToColour(int index)
	{
		switch (index)
		{
			case 0:
			case 5:
				return Color.WHITE;
			case 1:
			case 6:
				return Color.CYAN;
			case 2:
			case 7:
				return Color.YELLOW;
			case 3:
			case 8:
				return Color.GREEN;
			case 4:
			case 9:
				return Color.ORANGE;
			default:
				return Color.BLACK;
		}
	}
}