package Booking;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

import FormMasterData.FormLocation;
import FormMasterData.FormNeedle;
import FormReport.FormReportLocationSumary;
import FormReport.FormReportNeeldeSumary;
import FormReport.FormReportVaccineBooking;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class MenuReport extends JFrame implements ActionListener {
	JButton btnbooking,btnneedle,btnlocation;
	public MenuReport()
	{
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(5,1)); 
		
		JPanel panel1 = new JPanel();
		btnbooking = new JButton("รายงานการจอง Vaccine");
		btnbooking.addActionListener(this);
		panel1.add(btnbooking);
		
		JPanel panel2 = new JPanel();
		btnneedle = new JButton("รายงานสรุปจำนวนเข็ม");
		btnneedle.addActionListener(this);
	    panel2.add(btnneedle);
	    
	    JPanel panel3 = new JPanel();
	    btnlocation = new JButton("รายงานสรุปแต่ละพื้นที่");
	    btnlocation.addActionListener(this);
		panel3.add(btnlocation);
				
		
		c.add(panel1);
		c.add(panel2);
		c.add(panel3);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnbooking)
		{
			FormReportVaccineBooking menuform = new FormReportVaccineBooking();
			//กำหนดขนาด
			menuform.setSize(800,680);
			//กำหนด Title bar
			menuform.setTitle("รายงานการจอง Vaccine");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
		}
		else if(e.getSource() == btnneedle)
		{
			FormReportNeeldeSumary menuform = new FormReportNeeldeSumary();
			//กำหนดขนาด
			menuform.setSize(800,680);
			//กำหนด Title bar
			menuform.setTitle("รายงานสรุปจำนวนเข็ม");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
			
		}
		else if(e.getSource() == btnlocation)
		{
			FormReportLocationSumary menuform = new FormReportLocationSumary();
			//กำหนดขนาด
			menuform.setSize(800,680);
			//กำหนด Title bar
			menuform.setTitle("รายงานสรุปแต่ละพื้นที่");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
			
		}
	}
}
