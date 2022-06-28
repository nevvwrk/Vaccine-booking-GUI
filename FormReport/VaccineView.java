package FormReport;

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
import java.awt.FlowLayout;

public class VaccineView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelCustomer;
	JDatePickerImpl datePickers,datePickers2;
	Properties p = new Properties();
	Stage stage;
	Connection conn = dbConnect.getConnection();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	XYChart.Series<String,Float> series = new XYChart.Series<>();
	 
	JFXPanel dataPanel;
	JPanel panel_2;
	JPanel panel;
	BarChart<String,Float> bar;
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public VaccineView() throws SQLException {
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TitledBorder.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 13));
		setTitle("รายงานสรุปแต่ละพื้นที่");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1266, 834);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 0, 0, 0));
		
	    panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		 dataPanel = new JFXPanel();
		 panel_2 = new JPanel();
		
		panel_2.add(dataPanel);
		
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 1));

		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 0, 0, 20));
		panel.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("วันที่เริ่มต้น");
		lblNewLabel.setBounds(149, 61, 47, 27);
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblNewLabel2 = new JLabel("วันที่สิ้นสุด");
		lblNewLabel2.setBounds(149, 125, 47, 27);
		lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		UtilDateModel model = new UtilDateModel();		
		Calendar cal = new GregorianCalendar();		
		model.setDate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		
		UtilDateModel model2 = new UtilDateModel();		
		Calendar cal2 = new GregorianCalendar();		
		model2.setDate(cal2.get(Calendar.YEAR),
				cal2.get(Calendar.MONTH),
				cal2.get(Calendar.DAY_OF_MONTH));
		model2.setSelected(true);
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2,p);
		
		datePickers2 = new JDatePickerImpl(datePanel2,new DateLabelFormatter());
		datePickers2.setBounds(206, 125, 202, 37);
		datePickers2.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_3.setLayout(null);
		
		panel_3.add(lblNewLabel);
		datePickers = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		datePickers.setBounds(206, 61, 202, 37);
		panel_3.add(datePickers);
		panel_3.add(lblNewLabel2);
		panel_3.add(datePickers2);
		
		JButton btnNewButton = new JButton("ค้นหา");
		btnNewButton.setBounds(262, 184, 86, 37);
		btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)

            {                
            	try {
					vacview();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		panel_3.add(btnNewButton);
		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(1200, 370));
		table = new JTable();
		Object data[][] = {};
		String header[] = {"ศูยน์ฉีด","เข็มที่ 1","เข็มที่ 2","เข็มที่ 3","เข็มที่ 4","เข็มที่ 5","จำนวน"};
		
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
		panel_1.add(scrollTable);
	}
	public void vacview() throws SQLException {
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
		
	    conn = dbConnect.getConnection();
	    
	    /*String SQL = "SELECT lo_desc,"
	      + " (select count(*) from vaccine_booking as n where  n.lo_id = lo_id and n.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as count, "
	      + " (select count(*) from vaccine_booking as n1 where n1.ne_id = '01' and n1.lo_id = lo_id and n1.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne1, "
	      + " (select count(*) from vaccine_booking as n2 where n2.ne_id = '02' and n2.lo_id = lo_id and n2.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne2, "
	      + " (select count(*) from vaccine_booking as n3 where n3.ne_id = '03' and n3.lo_id = lo_id and n3.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne3, "
	      + " (select count(*) from vaccine_booking as n4 where n4.ne_id = '04' and n4.lo_id = lo_id and n4.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne4, "
	      + " (select count(*) from vaccine_booking as n5 where n5.ne_id = '05' and n5.lo_id = lo_id and n5.vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"') as ne5 "
	                  + " FROM location";
	                  //+ " LEFT JOIN vaccine_booking as v on l.lo_id = v.lo_id"
	                  //+ " GROUP BY lo_desc; ";*/
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
	    ResultSet rs = conn.createStatement().executeQuery(SQL);
	    int row = 0;

	          while(rs.next())
	          {
	           modelCustomer.addRow(new Object[0]);
	           //modelCustomer.setValueAt(rs.getString("lo_desc"),row,0);
	           /*modelCustomer.setValueAt(rs.getString("ne1"),row,1);
	           modelCustomer.setValueAt(rs.getString("ne2"),row,2);
	           modelCustomer.setValueAt(rs.getString("ne3"),row,3);
	           modelCustomer.setValueAt(rs.getString("ne4"),row,4);
	           modelCustomer.setValueAt(rs.getString("count"),row,5);*/
	           if(rs.getString("lo_desc").equals("0")) modelCustomer.setValueAt("",row,0);
	           else modelCustomer.setValueAt(rs.getString("lo_desc"),row,0);
	           if(rs.getString("ne1").equals("0")) modelCustomer.setValueAt("",row,1);
	           else modelCustomer.setValueAt(rs.getString("ne1"),row,1);
	           if(rs.getString("ne2").equals("0")) modelCustomer.setValueAt("",row,2);
	           else modelCustomer.setValueAt(rs.getString("ne2"),row,2);
	           if(rs.getString("ne3").equals("0")) modelCustomer.setValueAt("",row,3);
	           else modelCustomer.setValueAt(rs.getString("ne3"),row,3);
	           if(rs.getString("ne4").equals("0")) modelCustomer.setValueAt("",row,4);
	           else modelCustomer.setValueAt(rs.getString("ne4"),row,4);
	           if(rs.getString("ne5").equals("0")) modelCustomer.setValueAt("",row,5);
	           else modelCustomer.setValueAt(rs.getString("ne5"),row,5);
	           if(rs.getString("count").equals("0")) modelCustomer.setValueAt("",row,6);
	           else modelCustomer.setValueAt(rs.getString("count"),row,6);
	           series.getData().add(new XYChart.Data(rs.getString("lo_desc"),Double.parseDouble(rs.getString("count"))));
	           //series.getData().add(new XYChart.Data(rs.getString("lo_desc"),10));
	           System.out.println(rs.getString("lo_desc") 
	             + " à¹€à¸‚à¹‡à¸¡ 1 " + rs.getString("ne1") 
	             + " à¹€à¸‚à¹‡à¸¡ 2 " + rs.getString("ne2") 
	             + " à¹€à¸‚à¹‡à¸¡ 3 " + rs.getString("ne3") 
	             + " à¹€à¸‚à¹‡à¸¡ 4 " + rs.getString("ne4") 
	             + " à¹€à¸‚à¹‡à¸¡ 5 " + rs.getString("ne5") 
	             +" total " + rs.getString("count"));
	           
	              row++;

	          }
	          table.setModel(modelCustomer);
	    
		/*conn = dbConnect.getConnection();
		String SQL = "SELECT l.lo_desc,COUNT(*) as count"
                + " FROM vaccine_booking as v"
                + " JOIN location as l on v.lo_id = l.lo_id"
                + " where vb_bookingdate between '"+bookingDate1+"' and '"+bookingDate2+"' "
                + " GROUP BY l.lo_desc; ";
		
		ResultSet rs = conn.createStatement().executeQuery(SQL);
		int row = 0;

        while(rs.next())
        {
        	modelCustomer.addRow(new Object[0]);
        	modelCustomer.setValueAt(rs.getString("lo_desc"),row,0);
        	modelCustomer.setValueAt(rs.getString("count"),row,1);
        	series.getData().add(new XYChart.Data(rs.getString("lo_desc"),Double.parseDouble(rs.getString("count"))));
        	//series.getData().add(new XYChart.Data(rs.getString("lo_desc"),10));
        	System.out.println(rs.getString("lo_desc") + " : " + rs.getString("count"));
        	
            row++;

        }
        table.setModel(modelCustomer);*/
        

//		series.getData().add(new XYChart.Data("test1",0.83));
//		series.getData().add(new XYChart.Data("test2",1.83));
		bar.getData().add(series);  
		xaxis.setLabel("");
		yaxis.setLabel("Count");
		bar.setTitle("ข้อมูล");  
		
		Group root = new Group(); 
		root.getChildren().add(bar);
		Scene scene  = new Scene(root,100,100);
		dataPanel.setScene(scene);
		panel_2.add(dataPanel);
		//panel.add(panel_2);
	}
}
