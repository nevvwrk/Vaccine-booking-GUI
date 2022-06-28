package Booking;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class MainMenu extends JFrame implements ActionListener {
	JButton btnmenuForm,btnmasterDataForm,btnmenuReport,btnexit,btnpdpa;
	public MainMenu()
	{
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(7,1)); 
		JLabel l1 = new JLabel("ระบบจอง Vaccine",SwingConstants.CENTER);
		l1.setFont(new Font("Tahoma", Font.BOLD,25));
		l1.setForeground(Color.RED);
		c.add(l1);
		
		JPanel panel1 = new JPanel();
	    btnmenuForm = new JButton("แบบฟอร์มการจอง Vaccine");
	    btnmenuForm.setPreferredSize(new Dimension(250, 30));
		btnmenuForm.addActionListener(this);
		panel1.add(btnmenuForm);
		
		JPanel panel2 = new JPanel();
	    btnmasterDataForm = new JButton("ระบบจัดการข้อมูลพื้นฐาน");
	    btnmasterDataForm.setPreferredSize(new Dimension(250, 30));
	    btnmasterDataForm.addActionListener(this);
	    panel2.add(btnmasterDataForm);
	    
	    JPanel panel3 = new JPanel();
	    btnmenuReport = new JButton("ระบบรายงาน");
	    btnmenuReport.setPreferredSize(new Dimension(250, 30));
		btnmenuReport.addActionListener(this);
		panel3.add(btnmenuReport);
				
		JPanel panel4 = new JPanel();
		btnexit = new JButton("ออกจากโปรแกรม");
		btnexit.setPreferredSize(new Dimension(250, 30));
		btnexit.addActionListener(this);
		panel4.add(btnexit);
		
		JPanel panel5 = new JPanel();
		btnpdpa = new JButton("ยอมรับ");
		btnpdpa.setPreferredSize(new Dimension(250, 30));
		btnpdpa.addActionListener(this);
		panel5.add(btnpdpa);
		
		JLabel labelNeeldel = new JLabel("นโยบาย PDPA ",SwingConstants.CENTER);
		labelNeeldel.setForeground(Color.RED);
		
		c.add(panel1);
		c.add(panel2);
		c.add(panel3);
		c.add(panel4);
		c.add(panel5);
		c.add(labelNeeldel);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainMenu mailmenu = new MainMenu();
		//กำหนดขนาด
		mailmenu.setSize(650,550);
		//กำหนด Title bar
		mailmenu.setTitle("ระบบจองวัคซีน Modona");
		//กำหนดให้หยุดการทำงานเมือปิดหน้าจอ
		mailmenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//กำหนดให้กึ่งกลางหน้าจอ
		mailmenu.setLocationRelativeTo(null);
		//กำหนดให้แสดงผล
		mailmenu.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnexit)
		{
			System.out.print("ออกจากโปรแกรม");
			System.exit(NORMAL); //0
		}
		else if(e.getSource() == btnmenuForm)
		{
			
			FormVaccineBooking menuform = new FormVaccineBooking();
		//	PieCharts menuform = new PieCharts();
			//DatePickerDemo menuform = new DatePickerDemo();
			//กำหนดขนาด
			menuform.setSize(1650,780);
			//กำหนด Title bar
			menuform.setTitle("แบบฟอร์มการจอง Vaccine");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
			
		}
		else if(e.getSource() == btnmasterDataForm)
		{
			MenuMasterData menuform = new MenuMasterData();
			//กำหนดขนาด
			menuform.setSize(500,400);
			//กำหนด Title bar
			menuform.setTitle("ระบบจัดการข้อมูลพื้นฐาน");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
			
		}
		else if(e.getSource() == btnmenuReport)
		{
			MenuReport menureport = new MenuReport();
			//กำหนดขนาด
			menureport.setSize(500,400);
			//กำหนด Title bar
			menureport.setTitle("การจัดการรายงาน");
			//กำหนดให้กึ่งกลางหน้าจอ
			menureport.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menureport.setVisible(true);
		}
		else if(e.getSource() == btnpdpa)
		{
			formPDPA menureport = new formPDPA();
			//กำหนดขนาด
			menureport.setSize(900,800);
			//กำหนด Title bar
			menureport.setTitle("การจัดการรายงาน");
			//กำหนดให้กึ่งกลางหน้าจอ
			menureport.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menureport.setVisible(true);
		}
		
		//btnpdpa
	}
	public void SetLanguage()
	{
		UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TitledBorder.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, 13)));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 13));	
	}
}
