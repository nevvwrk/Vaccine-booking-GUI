package Booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {
	public static Connection getConnection()
	{
		try
		{
			Class.forName(com.mysql.cj.jdbc.Driver.class.getName());
			return DriverManager.getConnection("jdbc:mysql://0.tcp.ap.ngrok.io:10860/vaccinebooking?useUnicode=true&characterEncoding=utf-8",

                    "root","p@ssword");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
