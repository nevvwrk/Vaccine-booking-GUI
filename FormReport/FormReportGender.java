package FormReport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Booking.DateLabelFormatter;
import Booking.dbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

public class FormReportGender extends JFrame {
	Connection conn = dbConnect.getConnection();
	JTable tableSumDose;
	DefaultTableModel modelSumDose;
	JDatePickerImpl datePickers1, datePickers2;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	PieChart pieChart;
	JFXPanel panelChart;
	ObservableList<PieChart.Data> data;
	

	public FormReportGender() {
	
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		panelChart = new JFXPanel();
		pieChart = new PieChart();
  
        
		// ---------------------------------------------------PANEL NORTH---------------------------------------------------//
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout());
		//panelNorth.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel Title = new JLabel("รายงานสรุปสัดส่วนเพศ", SwingConstants.CENTER);
		Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		Title.setForeground(Color.BLUE);
		panelNorth.add(Title);
		c.add(panelNorth, BorderLayout.NORTH);

		// ---------------------------------------------------PANEL CENTER---------------------------------------------------//
	
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(1, 2));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		JPanel panelCalendar = new JPanel();
		panelCalendar.setLayout(new GridLayout(6, 1));
		panelCalendar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		
		JPanel panelData = new JPanel();
		panelData.setLayout(new GridLayout(1, 1));
		panelData.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// ---------------------------------------------------PANEL CENTER L---------------------------------------------------//

		JPanel panelSTDate = new JPanel();
		panelSTDate.setLayout(new GridLayout(2, 2));
		JLabel txt1 = new JLabel("ตั้งแต่วันที่");
		panelSTDate.add(txt1);

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		Calendar cal = new GregorianCalendar();
		model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model, p);
		datePickers1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		panelSTDate.add(datePickers1);
		panelCalendar.add(panelSTDate);
		

		JPanel panelenDate = new JPanel();
		panelenDate.setLayout(new GridLayout(2, 2));
		JLabel txt2 = new JLabel("จนถึงวันที่");
		panelenDate.add(txt2);

		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		UtilDateModel model2 = new UtilDateModel();
		Calendar cal2 = new GregorianCalendar();
		model2.setDate(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
		model2.setSelected(true);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		datePickers2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		panelenDate.add(datePickers2);
		panelCalendar.add(panelenDate);

		JPanel panelButton1 = new JPanel();
		JButton btnOK = new JButton("ตกลง");
		btnOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{							
				ShowPieChart();
				dataTable();
			}
		});
		panelButton1.add(btnOK);
	
		panelCalendar.add(panelButton1);	
		panelCenter.add(panelCalendar);
		
		
		// ---------------------------------------------------PANEL CENTER R---------------------------------------------------//

		JPanel panelTable = new JPanel();
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(500, 750));

		tableSumDose = new JTable();
		Object data[][] = {  	{ null, null }, 
										{ null, null }, 
										{ null, null }, 
										{ null, null }, 
										{ null, null }, 
										{ null, null } };
		String header[] = { "เพศ", "จำนวน" };

		modelSumDose = new DefaultTableModel(data, header) {
				public boolean isCellEditable(int row, int columns) {
					return false;
				}
		};
		tableSumDose.setModel(modelSumDose);
		scrollTable.setViewportView(tableSumDose);
		panelTable.add(scrollTable);
		//panelTable.setSize(500, 300);
		
		
		panelData.add(panelChart);
		//panelData.add(panelTable);
		panelCalendar.add(panelTable);
		//panelCalendar.add(new JLabel("ตั้งแต่วันที่"));
			
		panelCenter.add(panelData);	
		c.add(panelCenter, BorderLayout.CENTER);
		
	}
	
	public void ShowPieChart()
	{
		Date selectedDate1 = (Date)datePickers1.getModel().getValue();
		Date selectedDate2 = (Date)datePickers2.getModel().getValue();
		String bookingdate1 = df.format(selectedDate1);
		String bookingdate2 = df.format(selectedDate2);

		if( selectedDate1.compareTo(selectedDate2) > 0 ) 
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณาตรวจสอบวันที่เริ่มต้น",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		pieChart = new PieChart();
        data= FXCollections.observableArrayList();

    	try
		{
			System.out.println((Date)datePickers1.getModel().getValue());
			System.out.println((Date)datePickers2.getModel().getValue());
			
			conn = dbConnect.getConnection();

			String SQL = "SELECT n.ge_desc,COUNT(*) as count"
					 + " FROM vaccine_booking as v"
					 + " JOIN people as p on v.pe_pid = p.pe_pid"
					 + " JOIN gender as n on p.pe_sex = n.ge_id"
					 + " where vb_bookingdate between '"+bookingdate1+"' and '"+bookingdate2+"' "
					 + " GROUP BY n.ge_desc; ";

			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				System.out.println(rs.getString("ge_desc"));
				System.out.println(rs.getString("count"));
				data.add(new PieChart.Data(rs.getString("ge_desc"), Integer.parseInt(rs.getString("count"))));
				row++;
			}
			conn.close();
			pieChart.setPrefHeight(450);
			pieChart.setPrefWidth(450);
	        pieChart.setData(data);
	        pieChart.setTitle("สรุปสัดส่วนเพศ");
	        pieChart.setLabelsVisible(true);
	        Group root = new Group();
	        root.getChildren().add(pieChart);
	        Scene scene = new Scene(root);
	        panelChart.setScene(scene); 	       
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void dataTable() 
	{
		try 
		{
			int totalRow = tableSumDose.getRowCount()-1;
			while(totalRow > -1) 
			{
				modelSumDose.removeRow(totalRow);
				totalRow-- ;
			}
			
			Date selectedDate1 = (Date)datePickers1.getModel().getValue();
			Date selectedDate2 = (Date)datePickers2.getModel().getValue();

    		String bookingdate1 = df.format(selectedDate1); 
    		String bookingdate2 = df.format(selectedDate2);
			
			conn = dbConnect.getConnection();

			String SQL = "SELECT n.ge_desc,COUNT(*) as count"
					 + " FROM vaccine_booking as v"
					 + " JOIN people as p on v.pe_pid = p.pe_pid"
					 + " JOIN gender as n on p.pe_sex = n.ge_id"
					 + " where vb_bookingdate between '"+bookingdate1+"' and '"+bookingdate2+"' "
					 + " GROUP BY n.ge_desc; ";
			
			ResultSet rs = conn.createStatement().executeQuery(SQL);

			int row = 0;
			while(rs.next()) 
			{
				modelSumDose.addRow(new Object[0]);
				modelSumDose.setValueAt(rs.getString("ge_desc"),row,0);
				modelSumDose.setValueAt(rs.getString("count"),row,1);

				row++;
			}
			tableSumDose.setModel(modelSumDose);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void SetLanguage() {
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
}
