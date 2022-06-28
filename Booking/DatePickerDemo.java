package Booking;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jdatepicker.impl.UtilDateModel;
public class DatePickerDemo extends JFrame implements ActionListener{
	private JDatePickerImpl datePicker;
	
	public DatePickerDemo() {
		super("Calendar Component Demo");
		setLayout(new FlowLayout());
		
		add(new JLabel("Birthday: "));
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(1990, 8, 24);
		model.setSelected(true);
		
		//UtilCalendarModel model = new UtilCalendarModel();
		//SqlDateModel model = new SqlDateModel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		
//		datePicker = new JDatePickerImpl(datePanel);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		add(datePicker);
		
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(this);
		
		add(buttonOK);
		
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new DatePickerDemo().setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// for UtilDateModel, the value returned is of type java.util.Date
		Date selectedDate = (Date) datePicker.getModel().getValue();
		
		// for UtilCalendarModel, the value returned is of type java.util.Calendar
//		Calendar selectedValue = (Calendar) datePicker.getModel().getValue();
//		Date selectedDate = selectedValue.getTime();

		// for SqlDateModel, the value returned is of type java.sql.Date
//		java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
		
		JOptionPane.showMessageDialog(this, "The selected date is " + selectedDate);
	}
}
