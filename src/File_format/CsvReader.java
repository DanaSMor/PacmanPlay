package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 
 * @author Dana Mor and Or Avital
 * this class get path to a csv file and transfer the information 
 * into comfortable data stracture
 *
 */
public class CsvReader {
	private static ArrayList<ArrayList<String>> arr;
	private String pathOfFile;

	/**
	 * 
	 * @param str path of the file
	 */
	public CsvReader(String str) 
	{
		pathOfFile=str;
		arr = new ArrayList<ArrayList<String>>();
		initialize();
	}

	/**
	 * intializing the arrayList
	 */
	private void initialize()  {
		String csvFile = this.pathOfFile;
		String line = "";
		String cvsSplitBy = ",";

		int counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
		{

			while ((line = br.readLine()) != null) 
			{
					arr.add(new ArrayList<String>());
					String[] userInfo = line.split(cvsSplitBy);

					for (int j = 0; j <userInfo.length; j++) {
						arr.get(counter).add(userInfo[j]);
					}		
					counter++;
				}

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return the header
	 * 
	 */
	public ArrayList<String>  get_header()
	{
		ArrayList<String> header = new ArrayList<String>();
		for (int i = 0; i <arr.get(0).size(); i++) {
			header.add(arr.get(0).get(i));			
		}
		return header;
	}
	
	/**
	 * 
	 * @return the number of rows
	 */
	public int get_rows()
	{
		return arr.size();
	}

	/**
	 * 
	 * @return copy of the array
	 */
	public ArrayList<ArrayList<String>> get_Array()
	{
		ArrayList<ArrayList<String>> CopyOfArr = new ArrayList<ArrayList<String>>();
		for (int m =0; m < arr.size()-1; m++) {
			CopyOfArr.add(new ArrayList<String>()); //adding array
			for (int j = 0; j < arr.get(m+1).size(); j++) {
				CopyOfArr.get(m).add(arr.get(m+1).get(j));	
			}
		}

		return CopyOfArr;

	}


}
