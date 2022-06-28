package Booking;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

import FormMasterData.FormLocation;
import FormMasterData.FormNeedle;
import FormReport.FormReportGender;
import FormReport.FormReportLocationSumary;
import FormReport.FormReportNeeldeSumary;
import FormReport.FormReportVaccineBooking;

import FormReport.VaccineView;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
public class MenuReport extends JFrame implements ActionListener {
	JButton btnbooking,btnneedle,btnlocation,btngender;
	public MenuReport()
	{
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(6,1)); 
		
		JLabel l1 = new JLabel("ระบบรายงาน",SwingConstants.CENTER);
		l1.setFont(new Font("Tahoma", Font.BOLD,25));
		l1.setForeground(Color.RED);
		c.add(l1);
		
		
		JPanel panel1 = new JPanel();
		btnbooking = new JButton("รายงานการจอง Vaccine");
		btnbooking.setPreferredSize(new Dimension(250, 30));
		btnbooking.addActionListener(this);
		panel1.add(btnbooking);
		
		JPanel panel2 = new JPanel();
		btnneedle = new JButton("รายงานสรุปจำนวนเข็ม");
		btnneedle.setPreferredSize(new Dimension(250, 30));
		btnneedle.addActionListener(this);
	    panel2.add(btnneedle);
	    
	    JPanel panel3 = new JPanel();
	    btnlocation = new JButton("รายงานสรุปแต่ละพื้นที่");
	    btnlocation.setPreferredSize(new Dimension(250, 30));
	    btnlocation.addActionListener(this);
		panel3.add(btnlocation);
		
	    JPanel panel4 = new JPanel();
	    btngender = new JButton("รายงานสรุปสัดส่วนเพศ");
	    btngender.setPreferredSize(new Dimension(250, 30));
	    btngender.addActionListener(this);
		panel4.add(btngender);
				
		
		c.add(panel1);
		c.add(panel2);
		c.add(panel3);
		c.add(panel4);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnbooking)
		{
//			VaccTest menuform;
//			try {
//				menuform = new VaccTest();
//				//FormReportVaccineBooking menuform = new FormReportVaccineBooking();
//				//กำหนดขนาด
//				menuform.setSize(800,680);
//				//กำหนด Title bar
//				menuform.setTitle("รายงานการจอง Vaccine");
//				//กำหนดให้กึ่งกลางหน้าจอ
//				menuform.setLocationRelativeTo(null);
//				//กำหนดให้แสดงผล
//				menuform.setVisible(true);
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			FormReportVaccineBooking menuform = new FormReportVaccineBooking();
			//กำหนดขนาด
			menuform.setSize(1500,880);
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
			menuform.setSize(1300,800);
			//กำหนด Title bar
			menuform.setTitle("รายงานสรุปจำนวนเข็ม");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
			
		}
		else if(e.getSource() == btnlocation)
		{
//			FormReportLocationSumary menuform = new FormReportLocationSumary();
//			//กำหนดขนาด
//			menuform.setSize(1500,880);
//			//กำหนด Title bar
//			menuform.setTitle("รายงานสรุปแต่ละพื้นที่");
//			//กำหนดให้กึ่งกลางหน้าจอ
//			menuform.setLocationRelativeTo(null);
//			//กำหนดให้แสดงผล
//			menuform.setVisible(true);
			
			
			VaccineView menuform;
			try {
				menuform = new VaccineView();
				//กำหนดขนาด
				menuform.setSize(1500,880);
				//กำหนด Title bar
				menuform.setTitle("รายงานสรุปแต่ละพื้นที่");
				//กำหนดให้กึ่งกลางหน้าจอ
				menuform.setLocationRelativeTo(null);
				//กำหนดให้แสดงผล
				menuform.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == btngender)
		{
			FormReportGender menuform = new FormReportGender();
			//กำหนดขนาด
			menuform.setSize(1300,800);
			//กำหนด Title bar
			menuform.setTitle("รายงานสรุปสัดส่วนเพศ");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
		}
	}
}
