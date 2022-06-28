package Booking;

import java.awt.BorderLayout;
import java.awt.Container;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;

public class PieCharts extends JFrame{
	Connection conn = dbConnect.getConnection();
	
	PieChart pieChart;
	ObservableList<PieChart.Data> data;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	JDatePickerImpl datePickers,datePickers2;
	public PieCharts()
	{
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(1,1)); 
		
//		ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
//					new PieChart.Data("A", 20),
//					new PieChart.Data("b", 20),
//					new PieChart.Data("c", 20),
//					new PieChart.Data("d", 20),
//					new PieChart.Data("e", 20)
//				);
//		
//		PieChart pChart = new PieChart(pieData);
//		
//		Group root = new Group(pChart);
//		Scene scene = new Scene(root,600,400);
//		 primaryStage.setTitle("Pie Chart Demo");
//		    primaryStage.setScene(scene);
//		    primaryStage.show();
		
		



		
//		ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList(
//	            new PieChart.Data("Nitrogen", 7809),
//	            new PieChart.Data("Oxygen", 2195),
//	            new PieChart.Data("Other", 93));
//	    
//	PieChart pieChart = new PieChart(valueList);
//	pieChart.setTitle("Air composition");
//
//	pieChart.getData().forEach(data -> {
//	    String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
//	    Tooltip toolTip = new Tooltip(percentage);
//	    Tooltip.install(data.getNode(), toolTip);
//	});
	
		JFXPanel dataPanel = new JFXPanel();
		pieChart = new PieChart();

	    data= FXCollections.observableArrayList();
        data= FXCollections.observableArrayList(
                new PieChart.Data("test1", 25),
                new PieChart.Data("test 2", 25),
                new PieChart.Data("test 3", 25),
                new PieChart.Data("test 4", 25)
            );
		

        pieChart.setData(data);
        pieChart.setTitle("test");
        pieChart.setLabelsVisible(true);
        Group root = new Group();

        root.getChildren().add(pieChart);
        
        Scene scene = new Scene(root);
        dataPanel.setScene(scene); 
        
        
        Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
        JPanel panelForm2 = new JPanel();
		UtilDateModel model = new UtilDateModel();		
		Calendar cal = new GregorianCalendar();		
		model.setDate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		datePickers = new JDatePickerImpl(datePanel,new DateLabelFormatter());

		panelForm2.add(datePickers);
		
		JPanel panelButton = new JPanel();
		JButton cmdSave = new JButton("เพิ่มข้อมูล");
		cmdSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				 
				ShowPieChart();
			}
		});
		panelButton.add(cmdSave);
		
		c.add(panelButton);
		c.add(panelForm2);
	    c.add(dataPanel);
	}
	public void ShowPieChart()
	{
		pieChart = new PieChart();
        data= FXCollections.observableArrayList();
        

    	try
		{
			System.out.println((Date)datePickers.getModel().getValue());
			Date selectedDate = (Date)datePickers.getModel().getValue();
			String bookingdate = df.format(selectedDate); 
			
			conn = dbConnect.getConnection();

						
			String SQL = "SELECT n.ne_desc,COUNT(*) as count"
						 + " FROM vaccine_booking as v"
						 + " JOIN needle as n on v.ne_id = n.ne_id"
						 + " where vb_bookingdate = '"+bookingdate+"'"
						 + " GROUP BY n.ne_id;";

			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			int row = 0;
			while(rs.next())
			{
				System.out.println(rs.getString("ne_desc"));
				//rs.getString("vb_id")
				data.add(new PieChart.Data(rs.getString("ne_desc"), Integer.parseInt(rs.getString("count"))));
				row++;
			}
			conn.close();
			
	        pieChart.setData(data);
	        pieChart.setTitle("test");
	        pieChart.setLabelsVisible(true);
	        Group root = new Group();

	        root.getChildren().add(pieChart);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
