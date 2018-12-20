package Gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.Map;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import File_format.Csv2Elem;
import File_format.Game2csv;
import File_format.Game2kml;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;
import Roads.PathData;
import Roads.ShortestPathAlgo;

/**
 * This class is responsible for the graphical
 *  representation of the game and user experience
 *
 */
public class MyFrame extends JFrame implements ActionListener, Serializable  {
	private static final long serialVersionUID = 2344533353395219312L;

	private JLabel displayCoord; // Label for hover mouse (show current pixles)
	private JMenuBar menuBar;
	private JMenu File, GameMenu;
	private JMenuItem open,savekml,savecsv,clear,play,Exit;
	private Game game; // Current game
	private Map map; // Our image & converts
	private BufferedImage pacmanImg,fruitImg;
	private BackGroundPanel panel; // Our Panel where all the game is displayed
	private Cursor Fruit,Pacman; // Change icon mouse accord selection
	private JRadioButton PacmanRadio, FruitRadio, mouseRadio; // Radio button to switch between Pacman, Fruit and Mouse
	private boolean running; // If is in animation progress avoid to do another commands
	private AliveThread AT; 

	public static void main(String[] args) {
		new  MyFrame();
	}

	public MyFrame() {
		running = false; // We start with no progress(game running animation)
		AT = new AliveThread(this);

		displayCoord = new JLabel();
		displayCoord.setFont(new Font("Tahoma", Font.PLAIN, 15));
		displayCoord.setForeground(Color.white);
		add(displayCoord);

		map = Map.map();
		panel = new BackGroundPanel();
		add(panel);

		game = null;
		menuBar = new JMenuBar();
		File = new JMenu("File");
		open = new JMenuItem("Open File...",new ImageIcon("Icon\\open.png"));
		open.addActionListener(this);
		savekml = new JMenuItem("Save As KML",new ImageIcon("Icon\\savek.png"));
		savekml.addActionListener(this);
		savecsv = new JMenuItem("Save As csv",new ImageIcon("Icon\\savec.png"));
		savecsv.addActionListener(this);
		Exit = new JMenuItem("Exit");
		Exit.addActionListener(this);

		File.setMnemonic(KeyEvent.VK_S);
		File.add(open);
		File.add(savekml);
		File.add(savecsv);
		File.add(Exit);

		menuBar.add(File);

		open.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		GameMenu = new JMenu("Game");
		play = new JMenuItem("Play", new ImageIcon("Icon\\play.png")); // Invoke image icon
		play.addActionListener(this);
		clear = new JMenuItem("Clear", new ImageIcon("Icon\\clear.png"));
		clear.addActionListener(this);

		GameMenu.add(play);
		GameMenu.add(clear);
		GameMenu.addActionListener(this);;
		menuBar.add(GameMenu);

		JLabel mouse = new JLabel(new ImageIcon("Icon\\mouse.png"));
		mouseRadio = new JRadioButton("Pointer");
		mouseRadio.setSelected(true);
		mouseRadio.addActionListener(this);
		menuBar.add(mouse);
		menuBar.add(mouseRadio);

		JLabel pacman = new JLabel(new ImageIcon("Icon\\pacman.png"));
		PacmanRadio = new JRadioButton("Pacman");
		PacmanRadio.addActionListener(this);
		menuBar.add(pacman);
		menuBar.add(PacmanRadio);

		JLabel Fruit = new JLabel(new ImageIcon("Icon\\Fruit.png"));
		FruitRadio = new JRadioButton("Fruit");
		FruitRadio.addActionListener(this);
		menuBar.add(Fruit);
		menuBar.add(FruitRadio);

		setJMenuBar(menuBar);
		ButtonGroup group = new ButtonGroup(); // Create a groupButton To synchronize the buttons
		group.add(mouseRadio);
		group.add(PacmanRadio);
		group.add(FruitRadio);

		start();

		setSize(1200, 800);
		setLocation(300, 50);
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void start() {
		try {
			fruitImg = ImageIO.read(new File("Icon\\Fruit.png"));
			pacmanImg = ImageIO.read(new File("Icon\\pacman.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		Fruit = Toolkit.getDefaultToolkit().createCustomCursor(fruitImg, new Point(0, 0), "Fruit");
		Pacman = Toolkit.getDefaultToolkit().createCustomCursor(pacmanImg, new Point(0, 0), "Pacman");
	}

	/**
	 * Didn't find if repaint is a synchronized method
	 * so for double check this method ensures this
	 */
	public synchronized void update() {
		repaint();
	}

	/**
	 * Responsible to send a message, when the game finish
	 * @param TotalTime
	 * @param fruitWeight
	 * @param TotalResult
	 */
	public void Result(String TotalTime,String fruitWeight, String TotalResult) {
		running = false; // Change running to false that we finish right now the current game
		// content of the message
		JOptionPane.showMessageDialog(this,
				"Total running Time: "+TotalTime+"\nFruit weight: "+fruitWeight+"\nGame result:\n"+TotalResult,
				"Game result:\n ", 
				JOptionPane.INFORMATION_MESSAGE); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o==Exit)
			System.exit(2);
		// reset the game for new one
		if(o==clear) {
			game = null;
			AT.clear = false;
			running = false; // If you click Clean at run time
			repaint(); 
			return;
		}

		if(o==open) {
			JFileChooser fc = new JFileChooser(); 
			fc.setDialogTitle("Select a csv file only");
			// Only csv file accepted
			FileNameExtensionFilter restrict = new FileNameExtensionFilter(".csv", "csv"); 
			fc.setFileFilter(restrict);
			int s = fc.showOpenDialog(null); // Open a file Chooser dialog
			if(s == JFileChooser.APPROVE_OPTION) { // The user select a csv file if doesn't do nothing
				File f = fc.getSelectedFile();
				Csv2Elem cs = new Csv2Elem(f); 
				game = cs.MakeElements(); // Translate a csv file into a new game
				repaint();
			}
			return;
		}

		if(o==savecsv || o==savekml) {
			String name = JOptionPane.showInputDialog("Enter File name","Game"); // Ask user for file name
			if(game!=null && name!=null) {
				if(o==savecsv) new Game2csv(game,name); // csv commend so call to game2csv to create that
				else new Game2kml(game,name); // else is a kml command
				// Send a successful message
				JOptionPane.showMessageDialog(this,
						"File '"+name+"' saved in source folder",
						"Saved successfully", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(game==null && name!=null) // if try save a empty game send a error
				JOptionPane.showMessageDialog(this,
						"Error: Unable save an empty game",
						"Error while saving",
						JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(o==PacmanRadio) { // Pacman choose, change cursor
			setCursor(Pacman);
			displayCoord.setVisible(false);
			return;
		}
		if(o==FruitRadio) { // Fruit choose, change cursor
			setCursor(Fruit);
			displayCoord.setVisible(false);
			return;
		}
		if(o==mouseRadio) { // Mouse choose, change cursor
			setCursor(null);
			return;
		}

		if(o==play && !running) {
			setCursor(null);
			mouseRadio.setSelected(true); // return to mouse selection

			if(game == null) // if try to play a empty game send error
				JOptionPane.showMessageDialog(this,
						"Error: Unable play an empty game",
						"Error while Playing - please enter Pacmans and Fruits",
						JOptionPane.ERROR_MESSAGE);

			// If try to play a game with no pacmans send a error
			else if(game.getFruitArray().size()>0&&game.getPacmanArray().size()==0)
				JOptionPane.showMessageDialog(this,
						"Error: Unable play game with no pacmans",
						"Error while Playing - please enter Pacmans ",
						JOptionPane.ERROR_MESSAGE);

			else if(game != null) { // Ok let's start the game
				running = true; // we are in progress
				ShortestPathAlgo shp = new ShortestPathAlgo(game); // Calculate how much we could do for the shortest routes
				String TotalTime = shp.getRunningTimeS();
				String totalWeightOfFruit = ""+shp.getTotalWeight();
				String TotalResult = shp.getTotalResult();

				repaint();

				String[] speedGame = { "RealTime", "Fast X16", "Faster X32","Cool!(recommend) X64" };
				int selected =  JOptionPane.showOptionDialog(this,
						"Just select the game speed you prefer (real time \n"
								+ "may be slow and exhausting)",
								"Game speed",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null,
								speedGame, speedGame[0]);

				switch(selected) {
				case 1 : selected = 6;
				break;

				case 2 : selected = 3;
				break;

				case 3 : selected = 1;
				break;
				
				default : selected = 100;
				}

				AT.setNewThreads(game, TotalTime,totalWeightOfFruit,TotalResult); // Create a new "Watch Thread"
				for(Pacman pac : game.getPacmanArray()) { // Open a new thread for each pacman
					new Animate(this,pac,AT,selected).start();
				}
			}

		}
	}

	/**
	 * This class responsible to paint all our elements and listen to user 
	 * for each command such as Clicks, drag and etc
	 *
	 */
	private class BackGroundPanel extends JPanel implements MouseInputListener, Serializable {
		private static final long serialVersionUID = -3626966327917598406L;

		public BackGroundPanel() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Draw our map as background
			g.drawImage(map.getMap(), 0, 0, getWidth(), getHeight(), this);

			if(game != null) {
				Graphics2D g2d = (Graphics2D) g; // we want to paint the paths with path java object, and control the stroke
				// Get the current game elements arrays
				ArrayList<Pacman> pacArr = game.getPacmanArray();
				ArrayList<Fruit> fruitArr =  game.getFruitArray();
				Point3D p;
				// Ratio for scaling the Pacmans and fruit animation
				double ratioH = getHeight()/600.0;
				double ratioW = getWidth()/800.0;

				for(Fruit fruit : fruitArr) { // Move all fruit array and draw them
					if(fruit.destroyed) { // if eaten don't draw
						p = map.coord2pixel(fruit.getPoint(), getWidth(), getHeight());	// Fruit point saved geograpic coord, so convert it
						g.drawImage(fruitImg, (int)p.x(), (int)p.y(), (int)(17*ratioW), (int)(17*ratioH), this);
					}
				}

				for(Pacman pacman : pacArr) { // Move all pacman array and draw the lines points if exists for each
					Iterator<PathData> it = pacman.getPath().getIterator();
					PathData pd =it.next();
					p = pd.getPoint();
					p = map.coord2pixel(p, getWidth(), getHeight()); // get the current path point and convert to pixel

					GeneralPath path = new GeneralPath(); 
					path.moveTo(p.x(),p.y()); // Start point initialization

					while(it.hasNext()) { // Move all the points path for that pacman
						pd = it.next();
						p =pd.getPoint();
						p = map.coord2pixel(p, getWidth(), getHeight()); // convert to pixels
						path.lineTo(p.x(), p.y()); // Add it to path deawing
					}
					g2d.setColor(pd.getColor());
					g2d.setStroke(new BasicStroke(2.5f));
					g2d.draw(path); // Finally draw path object
				}

				for(Pacman pacman : pacArr) { // Move all pacman array and draw them
					p = map.coord2pixel(pacman.getPoint(), getWidth(), getHeight());
					g.drawImage(pacmanImg, (int)p.x(), (int)p.y(), (int)(20*ratioW), (int)(20*ratioH), this);
				}
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			System.out.println("Clicks: ("+x+", "+y+')'); // Print coords for each user clicks
			Point3D p = new Point3D(x,y);
			p = map.pixel2coord(p, getWidth(), getHeight());
			System.out.println("Geograpichã coords: ("+p+')'); // Print geo corrds as well
			if(!mouseRadio.isSelected() && !running) { // If in progress don't let to add more Character
				if(game == null) // If null create a new game
					game = new Game(); 

				if(PacmanRadio.isSelected()) { // If user choose pacman, add it
					int pacmanId = game.getMaxIdPacman()+1;
					game.addPacman(new Pacman(p, pacmanId,1 , 1));
				}
				else  { // else user choose fruit, add it
					int fruitId = game.getMaxIdFruit()+1;
					game.addFruit(new Fruit(p, fruitId, 1));
				}
				repaint();
			}
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			if(mouseRadio.isSelected()) // If mouse selected & entert into frame turn on show coords near mouse
				displayCoord.setVisible(true);
		}

		@Override
		public void mouseExited(MouseEvent e) { // if mouse exit frame hide it
			displayCoord.setVisible(false);
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) { // Accept drag drawing, so when drag add it as character
			mouseClicked(e);
			try {
				Thread.sleep(40);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(mouseRadio.isSelected()) { // If mouse moved print near mouse current coords
				displayCoord.setBounds(e.getX(), e.getY(), 80, 55);
				displayCoord.setText(e.getX()+", "+e.getY());
			}
		}

	}
}

