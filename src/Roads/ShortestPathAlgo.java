package Roads;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
/**
 * @author Dana Mor and Or avital
 * 
 * this class calculate the routs of the pacmans
 */


public class ShortestPathAlgo {

	private Game game;
	private ArrayList<Path> path;
	private String runningTimeS;
	private String TotalResult;
	private double totalWeight;

	/**
	 * 
	 * @param game this function gets a game
	 */
	public ShortestPathAlgo(Game game)
	{
		this.game = game;
		this.path = new ArrayList<Path>();
		this.runningTimeS = "";
		this.totalWeight=0;
		this.TotalResult="";
		calculate();
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * this function calculates the routes of the pacmans
	 */
	private void calculate()
	{
		if(this.game.getFruitArray().size()>0&&this.game.getPacmanArray().size()==0)
		{
			System.out.println("can't calculat a game without Pacmans!");
			return;
		}
		//*********delete old Path********//
		Iterator<Pacman> itPacman = game.getPacmanIterator();
		while(itPacman.hasNext()) 
		{	
			Pacman p = itPacman.next();
			if(p.getPath().getLengthOfPath()!=0)
			{
				p.setPath();
				p.setWeightOfFruit(0);
				p.setNumOfFruits(0);
			}
		}

		//*********creates a deep copy of array list of fruits*************//
		ArrayList<Fruit> fruitArray = new ArrayList<Fruit>(); 
		Iterator<Fruit> it = game.getFruitIterator();
		while(it.hasNext()) {
			Fruit s = new Fruit(it.next());
			fruitArray.add(s);
		}
		//*********create local variables******//
		double currentDis, minTime= Integer.MAX_VALUE,currentTime=0, currentPriority,minPriority=Integer.MAX_VALUE;
		int PacId=0, FruId=0, pacIndex=0, fruitIndex=0;

		MyCoords distance = MyCoords.myCoords();

		//**********calculates the path************//

		while(fruitArray.size()>0)
		{
			Iterator<Pacman> itPac = game.getPacmanIterator();
			//*******iterating pacman array**//
			while(itPac.hasNext()) 
			{	
				Pacman p = itPac.next();

				Iterator<Fruit> itFruit = fruitArray.iterator();
				//*******iterating fruit array**//
				while(itFruit.hasNext()) 
				{
					Fruit f = itFruit.next();
					currentDis = distance.distance3d(p.getPoint(),f.getPoint())-p.getRadius();//distance take into consideration
					if(currentDis<0)//if the fruit is in the pacman eating Radius
						currentDis=0;
					currentTime= currentDis/p.getAttribute();
					currentPriority=currentTime+p.getPath().getTotalTime();
					
					//**save the data of pacman with the higher priority**//
					if(currentPriority<=minPriority)
					{	
							PacId=pacIndex;
							FruId=fruitIndex;
							minTime=currentTime;
							minPriority=currentPriority;
					}
					fruitIndex++;
				}
				fruitIndex=0;
				pacIndex++;	

			}
			
			//****adding the fruit to the path of the pacman with the higher priority****///
			game.getPacmanArray().get(PacId).addToPath(fruitArray.get(FruId),minTime);//set path of the pacman
			game.getPacmanArray().get(PacId).setPoint(fruitArray.get(FruId).getPoint());//set the new point of the pacman
			game.getPacmanArray().get(PacId).addWeightOfFruit(fruitArray.get(FruId).getAttribute());
			fruitArray.remove(FruId);//removes the fruit from the arrayList		
			pacIndex=0;
			PacId=0;
			FruId=0;
			minTime= Integer.MAX_VALUE;
			minPriority=Integer.MAX_VALUE;
		}

		BuildPath();
	}
	/**
	 * this function add path to the path arrayLiast
	 */
	private void BuildPath() {
		double runningTime = Double.MIN_VALUE;
		Iterator<Pacman> itPac = game.getPacmanIterator();

		int i=1;
		while(itPac.hasNext()) 
		{	
			Pacman p = itPac.next();
			if(p.getPath().getLengthOfPath()!=0)
			{
				//adding path
				p.setPoint(p.getPath().getRoad().get(0).getPoint());//sets the pacman back to his original point
				path.add(p.getPath());
				//find max run time
				if(runningTime< p.getPath().getTotalTime())
				{
					runningTime= p.getPath().getTotalTime();
					runningTimeS=p.getPath().getTotalTimeFormat();
				}

				totalWeight= totalWeight+p.getWeightOfFruit();
				TotalResult=TotalResult+"Pacman "+p.getId()+": number of fruits--> "+p.getNumOfFruits()+
						" weight of fruits--> "+p.getWeightOfFruit()+"\n";
			}
			i++;
		}

		System.out.println(TotalResult);
		System.out.println("\nTotal Running Time: "+runningTime);
	}

	public String getTotalResult() {
		return TotalResult;
	}

	public String getRunningTimeS() {
		return runningTimeS;
	}


	public ArrayList<Path> getPath() {
		return path;
	}




}
