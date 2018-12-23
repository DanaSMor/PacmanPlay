package File_format;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import Gameboard.Game;
import Gameboard.Pacman;
import Roads.PathData;
/**
 * @author Dana Mor and Or avital
 * this class convert game to kml
 */
public class Game2kml {

	private StringBuilder sb;
	private StringBuilder lineB;
	private String nameOfFile;
	
	public Game2kml(Game g, String name)
	{
		nameOfFile=name;
		intialize(g);
	}

	////****creating the kml file******//////
	
	private void intialize (Game g) {
		
		boolean first = true;
		//******writing kml****//
		String fileName = this.nameOfFile+".kml";
		PrintWriter pw = null;	
		try 
		{
			pw = new PrintWriter(new File(fileName));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		lineB= new StringBuilder();	//line builder
		sb = new StringBuilder();	//regular kml builder
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); // Opening of kml file
		sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		sb.append("<Document><Style id=\"pacman\"><IconStyle>\n<Icon>"
				+ "<href>https://cdn1.iconfinder.com/data/icons/game-13/100/__4-512.png</href>\n</Icon>"
				+ "</IconStyle></Style><Style id=\"fruit\"><IconStyle>\n");
		sb.append("<Icon><href>https://cdn2.iconfinder.com/data/icons/vegetable-and-fruit-fill-style/64/strawberry-fruit-tasty-dessert-juicy-512.png</href>"
				+ "</Icon></IconStyle></Style>"
				+ "<Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>\n");

		// For each layer in the project add the correct tags
		Iterator<Pacman> itPacman = g.getPacmanIterator();
		while(itPacman.hasNext()) 
		{			
			//////***adding path of pacman***/////
			Pacman p = itPacman.next();
			Iterator<PathData> itPath = p.getPath().getIterator();	
			
			sb.append("<Folder><name>Pacman "+p.getId()+"</name>\n");			
			lineB.append("<Placemark><name>"+p.getId()+"</name>\n");
			lineB.append("<description>pacman</description>\n");
			lineB.append("<LineString>\n");
			lineB.append("<coordinates>\n");
			
			//System.out.println(pd.getId());
			while(itPath.hasNext()) 
			{	
				PathData pd = itPath.next();
				////****adding pacman and fruits******//////			
				sb.append("<Placemark>\n");	
				
				///***first represent pacman****/////
				if(first)
				{
					sb.append("<name><![CDATA[ Pacman "+p.getId()+"]]>");
				}
				else
					sb.append("<name><![CDATA[]]>");
				
				sb.append("</name>\n");
				sb.append("<description>");
				
				if(first)
				{
					sb.append("<b> radius:"+p.getRadius()+"</b>"+"<b> speed:"+p.getAttribute()+"</b>");
				}
				
				if(first)
				{
					sb.append("</description><styleUrl>#"+"pacman"+"</styleUrl>");
					first = false;
				}
				else
					sb.append("</description><styleUrl>#"+"fruit"+"</styleUrl>");
				sb.append('\n');
				sb.append("<Point>\n<coordinates>"+pd.getPoint().y()+","+pd.getPoint().x()+"</coordinates>\n</Point>");
				sb.append('\n');	
				sb.append("<TimeStamp>\n");
				sb.append("<when>"+pd.getTimeFormat()+"</when>\n");
				sb.append("</TimeStamp>\n");				
				sb.append("</Placemark>");
				sb.append('\n');
				
				lineB.append(pd.getPoint().y()+","+pd.getPoint().x()+"\n");				
			}
			lineB.append("</coordinates>\r\n"+"</LineString>\r\n" +"</Placemark>\n");
			sb.append(lineB);
			lineB.delete(0, lineB.length()-1);
			sb.append("</Folder>");
			first = true;
		}

		////****close the document when we finish to add all paths****//////
		sb.append("\n</Document></kml>"); 
		pw.write(sb.toString());
		pw.close();
	}	
}
