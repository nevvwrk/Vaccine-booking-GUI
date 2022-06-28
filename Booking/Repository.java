package Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
	public int insertBooking(
			String txtPEPID,
			String txtPEName,
			String txtPEAGE,
			String txtPEADDR,
			String txtPEPhone,
			String bookingDate,
			String _sex,
			String needel,
			String localtion)
	{
		int result = 0;
		
		try
		{


			int max = BookingRuning();
			//int max = 67;
			int xx = UpdateBookingRuning(max);
			String vb_id = "VB" + RPad(String.valueOf(max),8,'0');

			
			int x = insertBookingPEPOLE(txtPEPID,txtPEName,txtPEAGE,txtPEADDR,txtPEPhone,_sex);
			
			
			conn = dbConnect.getConnection();
			String sql = "INSERT INTO VACCINE_BOOKING VALUES(?,?,?,?,?,?)";
			PreparedStatement pre = conn.prepareStatement(sql);	
			pre.setString(1,vb_id);
			pre.setString(2,txtPEPID);
			pre.setString(3,needel);
			pre.setString(4,localtion);
			pre.setString(5,bookingDate);
			pre.setString(6,bookingDate);
			pre.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
			
			result = pre.executeUpdate();
			
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public int insertBookingPEPOLE(
			String txtPEPID,
			String txtPEName,
			String txtPEAGE,
			String txtPEADDR,
			String txtPEPhone,
			String _sex)
	{
		int result = 0;
		
		try
		{
			Pepole p = GetPEOPLE(txtPEPID);
			conn = dbConnect.getConnection();
			
			if(p == null)
			{
				String sql = "INSERT INTO PEOPLE VALUES(?,?,?,?,?,?)";
				PreparedStatement pre = conn.prepareStatement(sql);	
				pre.setString(1,txtPEPID);
				pre.setString(2,txtPEName);
				pre.setInt(3,Integer.parseInt(txtPEAGE));
				pre.setString(4,_sex);
				pre.setString(5,txtPEADDR);
				pre.setString(6,txtPEPhone);
				result = pre.executeUpdate();
			}
			else
			{
				String sql = "update PEOPLE set pe_name=?, "
						+ " pe_age=?, "
						+ " pe_addre=?, "
						+ " pe_phone=? "
						+ " where pe_pid =" + txtPEPID;
				PreparedStatement pre = conn.prepareStatement(sql);	
				pre.setString(1,txtPEName);
				pre.setInt(2,Integer.parseInt(txtPEAGE));
				pre.setString(3,txtPEADDR);
				pre.setString(4,txtPEPhone);
				result = pre.executeUpdate();
			}			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public static String RPad(String str, Integer length, char car) {
		  return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
		}

	public int BookingRuning()
	{
		int result = 1;
		try
		{
			
			conn = dbConnect.getConnection();
			String SQL = "select * from bookingrunning where pro_id = 'vb'";

			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				//System.out.print(rs.getString("running"));
				result = Integer.parseInt(rs.getString("running"))+1;
				row++;
			}
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public int UpdateBookingRuning(int max)
	{
		int result = 0;
		try
		{
			String sql = "";
			conn = dbConnect.getConnection();
			if(max > 1)
			{
				 sql = "update bookingrunning set running=? "
							+ " where pro_id ='vb'";
			}
			else
			{
				 sql = "INSERT INTO bookingrunning VALUES('vb',?)";
			}


			PreparedStatement pre = conn.prepareStatement(sql);	
			pre.setInt(1,max);
			result = pre.executeUpdate();
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public Pepole GetPEOPLE(String pid)
	{
		Pepole result = null;
		try
		{
			
			conn = dbConnect.getConnection();
			String SQL = "select * from PEOPLE where pe_pid = '"+ pid +"'";

			ResultSet rs = conn.createStatement().executeQuery(SQL);


			int row = 0;
			while(rs.next())
			{
				result = new Pepole(rs.getString("pe_pid"),
						rs.getString("pe_name"),
						rs.getString("pe_age"),
						rs.getString("pe_sex"),
						rs.getString("pe_addre"),
						rs.getString("pe_phone"));
				row++;
			}
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public VaccineHis GetVaccidne(String vb_id)
	{
		VaccineHis result = null;
		try
		{
			
			conn = dbConnect.getConnection();
			String SQL = "select v.vb_bookingdate,v.ne_id,v.lo_id,p.pe_addre,p.pe_sex from vaccine_booking as v "
						 + " join people as p on v.pe_pid = p.pe_pid"
						 + " where v.vb_id = '"+ vb_id +"' Limit 1";

			ResultSet rs = conn.createStatement().executeQuery(SQL);


			int row = 0;
			while(rs.next())
			{
				System.out.print(rs.getString("pe_addre"));
				result = new VaccineHis(rs.getString("vb_bookingdate"),rs.getString("ne_id"),rs.getString("lo_id"),rs.getString("pe_addre"),rs.getString("pe_sex"));
				row++;
			}
			rs.close();
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public VaccineHis GetLastDate(String pid)
	{
		VaccineHis result = null;
		try
		{
			
			conn = dbConnect.getConnection();
			String SQL = "select * from vaccine_booking where pe_pid = '"+ pid +"' order by createdate LIMIT 1";

			ResultSet rs = conn.createStatement().executeQuery(SQL);


			int row = 0;
			while(rs.next())
			{
				//System.out.println(rs.getString("vb_bookingdate"));
				result = new VaccineHis(rs.getString("vb_bookingdate"),rs.getString("ne_id"),"","","");
				row++;
			}
			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public boolean CheckNeedleDup(String pid,String ne_id,boolean editTbl,String vb_booking)
	{
		boolean result = false;

		try
		{
			conn = dbConnect.getConnection();
			//String SQL = "select * from vaccine_booking where pe_pid = '"+ pid +"' and ne_id = '"+ ne_id +"'";
			String SQL = "select * from vaccine_booking where pe_pid = '"+ pid +"' and ne_id = '"+ ne_id +"' and vb_id != '"+vb_booking+"'";

			ResultSet rs = conn.createStatement().executeQuery(SQL);

			while(rs.next())
			{
				result = true;
			}

			conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public int DeleteBooking(String vb_id)
	{
		int result = 0;
		try
		{
			conn = dbConnect.getConnection();
			String sql = "delete from vaccine_booking where vb_id = '" + vb_id +"'";
			PreparedStatement pre = conn.prepareStatement(sql);	
			result = pre.executeUpdate();
			conn.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public int UpdateBooking(String vb_id,String ne_id,String lo_id,String vb_bookingDate,String pe_pid,String pe_name,String pe_age,String pe_addre,String pe_phone,String sex)
	{
		int result = 0;
		try
		{
			conn = dbConnect.getConnection();
			String sql = "update vaccine_booking set ne_id=?, "
						+ " lo_id=?, "
						+ " vb_bookingDate=? "
						+ " where vb_id = '" + vb_id + "'";
			PreparedStatement pre = conn.prepareStatement(sql);	
			
			pre.setString(1,ne_id);
			pre.setString(2,lo_id);
			pre.setString(3,vb_bookingDate);
			

			result = pre.executeUpdate();
			
			
			//People
			sql = "update PEOPLE set pe_name=?, "
					+ " pe_age=?, "
					+ " pe_addre=?, "
					+ " pe_phone=?, "
					+ " pe_sex=? "
					+ " where pe_pid = '" + pe_pid + "'";
			PreparedStatement pre1 = conn.prepareStatement(sql);	
			
			pre1.setString(1,pe_name);
			pre1.setInt(2,Integer.parseInt(pe_age));
			pre1.setString(3,pe_addre);
			pre1.setString(4,pe_phone);
			pre1.setString(5,sex);
		

			result = pre1.executeUpdate();
			
			conn.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
