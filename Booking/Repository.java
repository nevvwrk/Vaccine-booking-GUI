package Booking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Repository {
	Connection conn;
	
	public Repository()
	{
		conn = dbConnect.getConnection();
	}
	public ArrayList<ComboItem> Needles()
	{
		 ArrayList<ComboItem> arr = new ArrayList<ComboItem>();
		try
		{
			conn = dbConnect.getConnection();
			String SQL = "select * from needle where active = 'Y'";

			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				arr.add(new ComboItem(rs.getString("ne_id"), rs.getString("ne_desc")));			
				row++;
			}
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return arr;
	}
	public ArrayList<ComboItem> Localtions()
	{
		 ArrayList<ComboItem> arr = new ArrayList<ComboItem>();
		try
		{
			
			conn = dbConnect.getConnection();
			String SQL = "select * from location where active = 'Y'";

			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				arr.add(new ComboItem(rs.getString("lo_id"), rs.getString("lo_desc")));
				row++;
			}
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return arr;
	}
}
