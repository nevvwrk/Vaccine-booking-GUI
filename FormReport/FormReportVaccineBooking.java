package FormReport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDayChooser;

import Booking.ComboItem;
import Booking.dbConnect;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormReportVaccineBooking extends JFrame{
	private JPanel contentPane;
	private JTextField txtSearch;
	private JTable tableCus;
	private DefaultTableModel modelCus;
	
	Connection Conn = dbConnect.getConnection();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private JDateChooser dcsStartDate;
	private JDateChooser dcsEndDate;
	private JComboBox cbbVenue;
	private JComboBox cbbDose;
	private JComboBox cbbFilter;
	public FormReportVaccineBooking()
	{
		SetLanguage();

		//-----------------Conn---------------
				SetLanguage();
				if(Conn!=null) {
					System.out.println("Database Connected");
				}else {
					System.out.println("Fail to Connect Database");
				}
				//--------------------------------
				
				
				//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 1605, 733);
				contentPane = new JPanel();
				contentPane.setBackground(new Color(245, 245, 245));
				contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				
				//---------------- J COMPONENT ---------------------------
				JLabel lblHeader = new JLabel("รายงานผู้ลงทะเบียนจองวัคซีน");
				lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
				lblHeader.setForeground(new Color(210, 105, 30));
				lblHeader.setBackground(new Color(255, 255, 255));
				lblHeader.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 22));

				lblHeader.setBounds(97, 24, 472, 30);
				contentPane.add(lblHeader);
				dcsStartDate = new JDateChooser();
				dcsStartDate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				dcsStartDate.setBounds(133, 79, 122, 30);
				dcsStartDate.setDate(new java.util.Date() );
				dcsStartDate.setBackground(Color.WHITE);
				dcsStartDate.setForeground(Color.cyan);
				contentPane.add(dcsStartDate);
				
				JLabel lblStartDate = new JLabel("วันเริ่มต้น");
				lblStartDate.setHorizontalAlignment(SwingConstants.RIGHT);
				lblStartDate.setBounds(49, 85, 74, 20);
				contentPane.add(lblStartDate);
				
				dcsEndDate = new JDateChooser();
				dcsEndDate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				dcsEndDate.setBounds(133, 120, 122, 30);
				dcsEndDate.setDate(new java.util.Date() );
				dcsEndDate.setBackground(Color.WHITE);
				dcsEndDate.setForeground(Color.RED);
				contentPane.add(dcsEndDate);
				
				JLabel lblEndDate = new JLabel("วันสิ้นสุด");
				lblEndDate.setHorizontalAlignment(SwingConstants.RIGHT);
				lblEndDate.setBounds(57, 125, 66, 20);
				contentPane.add(lblEndDate);
				
				JLabel lblDose = new JLabel("เข็มที่");
				lblDose.setHorizontalAlignment(SwingConstants.RIGHT);
				lblDose.setBounds(56, 168, 66, 20);
				contentPane.add(lblDose);
				
				cbbDose = new JComboBox(Needles().toArray());
				cbbDose.setBounds(133, 161, 150, 30);
				cbbDose.insertItemAt(new ComboItem("", "--- All ---"),0);
				cbbDose.setBackground(Color.WHITE);
				cbbDose.setSelectedIndex(0);
				
				contentPane.add(cbbDose);
				
				JLabel lblDose_1 = new JLabel("ศูนย์ฉีด");
				lblDose_1.setHorizontalAlignment(SwingConstants.RIGHT);
				lblDose_1.setBounds(29, 208, 94, 20);
				contentPane.add(lblDose_1);
				
				cbbVenue = new JComboBox(Localtions().toArray());
				cbbVenue.setSelectedIndex(0);
				cbbVenue.setBounds(132, 201, 310, 30);
				cbbVenue.insertItemAt(new ComboItem("", "--- All ---"),0);
				cbbVenue.setBackground(Color.WHITE);
				contentPane.add(cbbVenue);
				
				cbbFilter = new JComboBox(getFilterList().toArray());
				cbbFilter.setSelectedIndex(0);
				cbbFilter.setBounds(467, 79, 134, 30);
				cbbFilter.setBackground(Color.WHITE);
				contentPane.add(cbbFilter);
				
				JLabel lblStartDate_1 = new JLabel("ต้องการค้นหา");
				lblStartDate_1.setHorizontalAlignment(SwingConstants.RIGHT);
				lblStartDate_1.setBounds(348, 85, 109, 20);
				contentPane.add(lblStartDate_1);
				
				JLabel lblStartDate_2 = new JLabel("ที่มีว่า");
				lblStartDate_2.setHorizontalAlignment(SwingConstants.RIGHT);
				lblStartDate_2.setBounds(391, 125, 66, 20);
				contentPane.add(lblStartDate_2);
				
				txtSearch = new JTextField();
				txtSearch.setBounds(466, 120, 135, 30);
				contentPane.add(txtSearch);
				txtSearch.setColumns(10);
				
				JButton btnSearch = new JButton("ค้นหา");
				btnSearch.setBackground(Color.ORANGE);
				btnSearch.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showData();
					}
				});
				btnSearch.setBounds(512, 207, 89, 23);
				contentPane.add(btnSearch);
					
				//---------------- J COMPONENT ---------------------------
				
				
				//********add table************
				
				Object data[][] = {
						{null,null,null,null,null,null,null,null,null,null},
						{null,null,null,null,null,null,null,null,null,null}};
						
				String header[] = {"รหัสใบจอง","รหัสบัตรประชาขน","ชื่อ-นามสกุล","เพศ","อายุ","เบอร์โทร","เข็มที่","ศูยน์ฉีด","วันที่ฉีด","ว้นที่ทำรายการ"};
				modelCus=new DefaultTableModel(data,header) {
					public boolean isCellEditable(int row, int columns) {
						return false;
					}
				};
				tableCus = new JTable();
				tableCus.setEnabled(false);
				tableCus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				tableCus.setRowHeight(30);
				tableCus.setModel(new DefaultTableModel(
					new Object[][] {
						{null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null},
					},
					new String[] {
						"\u0E23\u0E2B\u0E31\u0E2A\u0E43\u0E1A\u0E08\u0E2D\u0E07", "\u0E23\u0E2B\u0E31\u0E2A\u0E1A\u0E31\u0E15\u0E23\u0E1B\u0E23\u0E30\u0E0A\u0E32\u0E02\u0E19", "\u0E0A\u0E37\u0E48\u0E2D-\u0E19\u0E32\u0E21\u0E2A\u0E01\u0E38\u0E25", "\u0E40\u0E1E\u0E28", "\u0E2D\u0E32\u0E22\u0E38", "\u0E40\u0E1A\u0E2D\u0E23\u0E4C\u0E42\u0E17\u0E23", "\u0E40\u0E02\u0E47\u0E21\u0E17\u0E35\u0E48", "Location", "\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E09\u0E35\u0E14", "\u0E27\u0E49\u0E19\u0E17\u0E35\u0E48\u0E17\u0E33\u0E23\u0E32\u0E22\u0E01\u0E32\u0E23"
					}
				));
				tableCus.getTableHeader().setBackground(Color.ORANGE);
				JScrollPane scrollTable = new JScrollPane();
				scrollTable.setViewportView(tableCus);
				scrollTable.setBounds(49, 316, 1400, 330);
				contentPane.add(scrollTable);
				
		
		
	}
	public void SetLanguage()
	{
		UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("TitledBorder.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, 16)));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("RadioButton.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 16));
	}
	public void showData(){

		try {
			//--------------------Delete all of data in Table if there is any-----------------------
			int totalRow = tableCus.getRowCount()-1;
			while(totalRow > -1) {
				modelCus.removeRow(totalRow--);
			}
			

			//String search = txtSearch.getText().trim();
			String strStartDate = dateFormat.format(dcsStartDate.getDate()); 
			String strEndDate = dateFormat.format(dcsEndDate.getDate()); 
			System.out.println(strStartDate );
			System.out.println(strEndDate );
			
			//------------------SQL statement------------------------------			
			Conn = dbConnect.getConnection();
			ComboItem needel = (ComboItem) cbbDose.getSelectedItem();
			ComboItem localtion = (ComboItem) cbbVenue.getSelectedItem();
			ComboItem filter = (ComboItem) cbbFilter.getSelectedItem();
			String search = txtSearch.getText().trim();

			
//			String SQL = "SELECT v.VB_ID, p.PE_PID, p.PE_NAME, g.GE_DESC, p.PE_AGE, p.PE_PHONE, n.NE_DESC, l.LO_DESC, v.VB_BookingDate, v.CreateDate"
//					+ " FROM Vaccine_booking AS v" 
//					+  " JOIN people AS p ON v.PE_PID = p.PE_PID "
//					+  " JOIN needle AS n ON v.NE_ID = n.NE_ID "
//					+  " JOIN location AS l ON v.LO_ID = l.LO_ID "
//					+  " JOIN GENDER AS g ON p.PE_SEX = g.GE_ID "
//					+ "WHERE v.vb_bookingDate BETWEEN  '" +strStartDate+ "'  AND  '" +strEndDate;
//			
//			System.out.print(filter.getValue());
//			if(needel.getValue() != "") SQL +=  "' AND v.NE_ID = '"+ needel.getValue();
//			if(localtion.getValue() != "") SQL += "' AND v.LO_ID = '"+ localtion.getValue();
//			if(filter.getValue() != "ALL") SQL += "' AND "+filter.getValue()+" LIKE '%" +search+"%' ";
//					SQL += "ORDER BY v.VB_BookingDate DESC";
			
			
			//Pie

			String SQL = "SELECT v.VB_ID, p.PE_PID, p.PE_NAME, g.GE_DESC, p.PE_AGE, p.PE_PHONE, n.NE_DESC, l.LO_DESC, v.VB_BookingDate, v.CreateDate"
		            + " FROM Vaccine_booking AS v"
		            +  " JOIN people AS p ON v.PE_PID = p.PE_PID "
		            +  " JOIN needle AS n ON v.NE_ID = n.NE_ID "
		            +  " JOIN location AS l ON v.LO_ID = l.LO_ID "
		            +  " JOIN GENDER AS g ON p.PE_SEX = g.GE_ID "
		            + "WHERE v.vb_bookingDate BETWEEN  '" +strStartDate+ "'  AND  '" +strEndDate+"'";

		            if (!(needel.getValue().equals(""))) SQL += " AND v.NE_ID = '"+ needel.getValue() +"'";      
		            if (!(localtion.getValue().equals(""))) SQL += " AND v.LO_ID = '"+ localtion.getValue()+"'";
		            SQL += " AND "+filter.getValue()+" LIKE '%" +search+"%' ORDER BY v.VB_BookingDate DESC";

			
			//SQL statement >> resultset
			ResultSet rs = Conn.createStatement().executeQuery(SQL);
			//add the resultset to table model
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int row=0;
			while(rs.next()) {
				modelCus.addRow(new Object[0]);

				for(int i = 1; i <=rsMetaData.getColumnCount();i++) {
					modelCus.setValueAt(rs.getString(rsMetaData.getColumnName(i)), row, i-1);
				}
				row++;
			}
			//add Data from model to Jtable
			tableCus.setModel(modelCus);
			resizeColumnWidth();
			
			SQL = "SELECT * FROM Vaccine_booking ";
			rs = Conn.createStatement().executeQuery(SQL);
			displayResultSet(rs);
			Conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public  void displayResultSet(ResultSet rs) throws SQLException{
		ResultSetMetaData rsMetaData = rs.getMetaData();
		for(int i = 1; i <=rsMetaData.getColumnCount();i++) {
			System.out.printf("%-15s\t" ,rsMetaData.getColumnName(i));
			
		}
		System.out.println();
		while (rs.next()) {
			for(int i = 1; i <=rsMetaData.getColumnCount();i++) {
				System.out.printf("%-15s\t" ,rs.getString(i));
				
				
			}
			System.out.println();
		}
	}
	public ArrayList<ComboItem> Needles()
	{
		 ArrayList<ComboItem> arr = new ArrayList<ComboItem>();
		try
		{
			Conn = dbConnect.getConnection();
			String SQL = "select * from needle where active = 'Y'";

			ResultSet rs = Conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				arr.add(new ComboItem(rs.getString("ne_id"), rs.getString("ne_desc")));			
				row++;
			}
			Conn.close();
			
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
			
			Conn = dbConnect.getConnection();
			String SQL = "select * from location where active = 'Y'";

			ResultSet rs = Conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				arr.add(new ComboItem(rs.getString("lo_id"), rs.getString("lo_desc")));
				row++;
			}
			Conn.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return arr;
	}
	public void resizeColumnWidth() {
	    final TableColumnModel columnModel = tableCus.getColumnModel();
		TableCellRenderer renderer;
		Component comp; 
	    for (int column = 0; column < tableCus.getColumnCount(); column++) {

	    	int width = 10;
	        for (int row = 0; row < tableCus.getRowCount(); row++) {
	        	
	            renderer = tableCus.getCellRenderer(row, column);
	            comp = tableCus.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	public ArrayList<ComboItem> getFilterList()	{
		 ArrayList<ComboItem> arr = new ArrayList<ComboItem>();
		 String[] value = {"VB_ID","PE_PID","PE_NAME","GE_DESC","PE_AGE","PE_PHONE","NE_DESC","LO_DESC","VB_BookingDate","CreateDate"}; 
		 String[] label = {"รหัสใบจอง","รหัสบัตรประชาขน","ชื่อ-นามสกุล","เพศ","อายุ","เบอร์โทร","เข็มที่","ศูยน์ฉีด","วันที่ฉีด","ว้นที่ทำรายการ"};
		 for(int i = 0 ; i < value.length ; i++) {
			 arr.add(new ComboItem(value[i], label[i]));

		 }
		return arr;
		 }
}
