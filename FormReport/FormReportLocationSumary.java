package FormReport;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Booking.DateLabelFormatter;
import Booking.dbConnect;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Booking.DateLabelFormatter;
import Booking.dbConnect;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;


public class FormReportLocationSumary extends JFrame{
	Properties p = new Properties();
	JDatePickerImpl datePickers,datePickers2;
	Stage stage;
	Connection conn = dbConnect.getConnection();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	XYChart.Series<String,Float> series = new XYChart.Series<>();
	 
	JFXPanel dataPanel;
	JPanel panel_2;
	JPanel panel;
	BarChart<String,Float> bar;
	private JTable table;
	DefaultTableModel modelCustomer;
	public FormReportLocationSumary()
	{
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel panlerow1 = new JPanel();
		panlerow1.setLayout(new GridLayout(1,5));
		panlerow1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		
		JLabel lblNewLabel = new JLabel("วันที่");
		UtilDateModel model = new UtilDateModel();		
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		Calendar cal = new GregorianCalendar();		
		model.setDate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		datePickers = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		panlerow1.add(lblNewLabel);
		panlerow1.add(datePickers);
		
		
		JLabel lblNewLabel2 = new JLabel("วันที่สิ้นสุด");
		UtilDateModel model2 = new UtilDateModel();		
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2,p);
		Calendar cal2 = new GregorianCalendar();		
		model2.setDate(cal2.get(Calendar.YEAR),
				cal2.get(Calendar.MONTH),
				cal2.get(Calendar.DAY_OF_MONTH));
		model2.setSelected(true);
		datePickers2 = new JDatePickerImpl(datePanel2,new DateLabelFormatter());
		panlerow1.add(lblNewLabel2);
		panlerow1.add(datePickers2);
		
		JButton btnNewButton = new JButton("ค้นหา");
		btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {                
            	vacview();
            }
        });
		panlerow1.add(btnNewButton);
		c.add(panlerow1);
		
		
		JPanel panlerow2 = new JPanel();
		panlerow2.setLayout(new GridLayout(1,1));
		panlerow2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		
		
		
//		JPanel panlerow3 = new JPanel();
//		panlerow3.setLayout(new GridLayout(1,1));
//		panlerow3.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		//ตาราง
		JPanel panelTable = new JPanel();
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(1600,300));

		table = new JTable();
		Object data[][] = {};
		String header[] = {"à¸¨à¸¹à¸™à¸¢à¹Œà¸‰à¸µà¸”à¸§à¸±à¸„à¸‹à¸µà¸™","à¸ˆà¸³à¸™à¸§à¸™"};
		
		modelCustomer = new DefaultTableModel(data,header) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row,int columns) {
				return false;
			}
		};
		
		table.setModel(modelCustomer);
		scrollTable.setViewportView(table);
		panelTable.add(scrollTable);
		
		c.add(panelTable);
		
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
	public void vacview()  {
		//String bookingdate1,bookingdate2;
		Date selectedDate = (Date)datePickers.getModel().getValue();
        String bookingDate1 = df.format(selectedDate);
        Date selectedDate2 = (Date)datePickers2.getModel().getValue();
        String bookingDate2 = df.format(selectedDate2);
        
        int totalRow = table.getRowCount()-1;
        while(totalRow > -1)
        {
            modelCustomer.removeRow(totalRow);
            totalRow--;
        }
        
        
		CategoryAxis xaxis= new CategoryAxis();  
		NumberAxis yaxis = new NumberAxis(0,5,1);
		series = new XYChart.Series<>();
	    bar = new BarChart(xaxis,yaxis);
		
	    try
	    {
	    	conn = dbConnect.getConnection();
			
			String SQL = "SELECT l.lo_desc,"
					+ " (select count(*) from vaccine_booking as n where  n.lo_id = v.lo_id and n.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as count, "
					+ " (select count(*) from vaccine_booking as n1 where n1.ne_id = '01' and n1.lo_id = v.lo_id and n1.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne1, "
					+ " (select count(*) from vaccine_booking as n2 where n2.ne_id = '02' and n2.lo_id = v.lo_id and n2.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne2, "
					+ " (select count(*) from vaccine_booking as n3 where n3.ne_id = '03' and n3.lo_id = v.lo_id and n3.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne3, "
					+ " (select count(*) from vaccine_booking as n4 where n4.ne_id = '04' and n4.lo_id = v.lo_id and n4.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne4, "
					+ " (select count(*) from vaccine_booking as n5 where n5.ne_id = '05' and n5.lo_id = v.lo_id and n5.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne5 "
	                + " FROM location as l"
	                + " LEFT JOIN vaccine_booking as v on l.lo_id = v.lo_id"
	                + " GROUP BY l.lo_desc; ";
			
//			String SQL = "SELECT l.lo_desc,"
//					+ " (select count(*) from vaccine_booking as n where  n.lo_id = l.lo_id and n.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as count, "
//					+ " (select count(*) from vaccine_booking as n1 where n1.ne_id = '01' and n1.lo_id = l.lo_id and n1.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne1, "
//					+ " (select count(*) from vaccine_booking as n2 where n2.ne_id = '02' and n2.lo_id = l.lo_id and n2.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne2, "
//					+ " (select count(*) from vaccine_booking as n3 where n3.ne_id = '03' and n3.lo_id = l.lo_id and n3.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne3, "
//					+ " (select count(*) from vaccine_booking as n4 where n4.ne_id = '04' and n4.lo_id = l.lo_id and n4.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne4, "
//					+ " (select count(*) from vaccine_booking as n5 where n5.ne_id = '05' and n5.lo_id = l.lo_id and n5.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne5 "
//	                + " FROM location as l"
//	                + " RIGHT JOIN vaccine_booking as v on l.lo_id = v.lo_id"
//	                + " GROUP BY l.lo_desc; ";
			
			
//			String SQL = "SELECT lo_desc,"
//					+ " (select count(*) from vaccine_booking as n where  n.lo_id = lo_id and n.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as count, "
//					+ " (select count(*) from vaccine_booking as n1 where n1.ne_id = '01' and n1.lo_id = lo_id and n1.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne1, "
//					+ " (select count(*) from vaccine_booking as n2 where n2.ne_id = '02' and n2.lo_id = lo_id and n2.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne2, "
//					+ " (select count(*) from vaccine_booking as n3 where n3.ne_id = '03' and n3.lo_id = lo_id and n3.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne3, "
//					+ " (select count(*) from vaccine_booking as n4 where n4.ne_id = '04' and n4.lo_id = lo_id and n4.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne4, "
//					+ " (select count(*) from vaccine_booking as n5 where n5.ne_id = '05' and n5.lo_id = lo_id and n5.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne5 "
//	                + " FROM location";
//	                + " LEFT JOIN vaccine_booking as v on l.lo_id = v.lo_id"
//	                + " GROUP BY l.lo_desc; ";
			
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			int row = 0;

	        while(rs.next())
	        {
	        	modelCustomer.addRow(new Object[0]);
	        	modelCustomer.setValueAt(rs.getString("lo_desc"),row,0);
	        	modelCustomer.setValueAt(rs.getString("count"),row,1);
	        	series.getData().add(new XYChart.Data(rs.getString("lo_desc"),Double.parseDouble(rs.getString("count"))));
	        	//series.getData().add(new XYChart.Data(rs.getString("lo_desc"),10));
	        	System.out.println(rs.getString("lo_desc") 
	        			+ " เข็ม 1 " + rs.getString("ne1") 
	        			+ " เข็ม 2 " + rs.getString("ne2") 
	        			+ " เข็ม 3 " + rs.getString("ne3") 
	        			+ " เข็ม 4 " + rs.getString("ne4") 
	        			+ " เข็ม 5 " + rs.getString("ne5") 
	        			+" total " + rs.getString("count"));
	        	
	            row++;

	        }
	        table.setModel(modelCustomer);
	        

//			series.getData().add(new XYChart.Data("test1",0.83));
//			series.getData().add(new XYChart.Data("test2",1.83));
			bar.getData().add(series);  
			xaxis.setLabel("ศูนย์ฉีด");
			yaxis.setLabel("Count");
			bar.setTitle("à¸£à¸²à¸¢à¸‡à¸²à¸™à¸ˆà¸³à¹�à¸™à¸�à¸•à¸²à¸¡à¸¨à¸¹à¸™à¸¢à¹Œà¸‰à¸µà¸”à¸§à¸±à¸„à¸‹à¸µà¸™");  
			
			Group root = new Group(); 
			root.getChildren().add(bar);
			Scene scene  = new Scene(root,100,100);
			dataPanel.setScene(scene);
			panel_2.add(dataPanel);
			//panel.add(panel_2);
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
