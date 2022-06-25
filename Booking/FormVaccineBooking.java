package Booking;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.plaf.FontUIResource;

public class FormVaccineBooking extends JFrame{

	JTextField txtPEPID,txtPEName,txtPESEX,txtPEAGE,txtPEADDR,txtPEBookingDate,txtPEPhone;
	JTable tableCust;
	DefaultTableModel modelCustomer;
	JRadioButton male = null;
	JRadioButton femal = null;
	ButtonGroup sex = new ButtonGroup();
	JComboBox<ComboItem> comboNeedle = null,comboLocation = null;
	Repository repo = new Repository();
	
	public FormVaccineBooking()
	{

		
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(1,2));
		pNorth.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		JPanel panelForm = new JPanel();
		panelForm.setLayout(new GridLayout(6,2)); 
		panelForm.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		JLabel labelPEPID = new JLabel("เลขบัตรประจำต้วประชาชน PE_PID");
		txtPEPID = new JTextField();
		txtPEPID.setPreferredSize(new Dimension(200,30));
		panelForm.add(labelPEPID);
		panelForm.add(txtPEPID);
		
		JLabel labelPEName = new JLabel("ชื่อ-นามสกุล PE_NAME");
		txtPEName = new JTextField();
		txtPEName.setPreferredSize(new Dimension(200,30));
		panelForm.add(labelPEName);
		panelForm.add(txtPEName);
		
		JLabel labelPESEX = new JLabel("เพศ PE_SEX");
		
		JPanel panelRd = new JPanel();
		male = new JRadioButton("ชาย");
		femal = new JRadioButton("หญิง");
		panelRd.add(male);
		panelRd.add(femal);
		sex.add(male);
		sex.add(femal);
		panelForm.add(labelPESEX);
		panelForm.add(panelRd);

		
		JLabel labelPEAGE = new JLabel("อายุ PE_AGE");
		txtPEAGE = new JTextField();
		txtPEAGE.setPreferredSize(new Dimension(200,30));
		panelForm.add(labelPEAGE);
		panelForm.add(txtPEAGE);
		
		JLabel labelPEADDR = new JLabel("ที่อยู่ PE_ADDR");
		txtPEADDR = new JTextField();
		txtPEADDR.setPreferredSize(new Dimension(200,30));
		panelForm.add(labelPEADDR);
		panelForm.add(txtPEADDR);
		
		JLabel labelPhone = new JLabel("เบอร์โทร PE_PHONE");
		txtPEPhone = new JTextField();
		txtPEPhone.setPreferredSize(new Dimension(200,30));
		panelForm.add(labelPhone);
		panelForm.add(txtPEPhone);
		
		
		pNorth.add(panelForm);
		
		
		//culumn 2
		JPanel panelForm2 = new JPanel();
		panelForm2.setLayout(new GridLayout(5,2));
		panelForm2.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		JLabel labelPEBookingDate = new JLabel("วันที่ PE_BookingDate");
		txtPEBookingDate = new JTextField();
		txtPEBookingDate.setPreferredSize(new Dimension(200,30));
		panelForm2.add(labelPEBookingDate);
		panelForm2.add(txtPEBookingDate);
		
		

		JLabel labelNeeldel = new JLabel("วัคซีนเข้มที่ ");
	    comboNeedle = new JComboBox(repo.Needles().toArray());
	    comboNeedle.insertItemAt(new ComboItem("", "เลือกเข็ม วัคซีน"),0);
	    comboNeedle.addItem(null);

	    comboNeedle.setSelectedIndex(0);
		panelForm2.add(labelNeeldel);
		panelForm2.add(comboNeedle);
		
		JLabel labelLocation = new JLabel("Location ");
		comboLocation = new JComboBox(repo.Localtions().toArray());
		comboLocation.insertItemAt(new ComboItem("", "เลือกเข็ม Location"),0);
		comboLocation.setSelectedIndex(0);
		panelForm2.add(labelLocation);
		panelForm2.add(comboLocation);
		
		pNorth.add(panelForm2);
		
		c.add(pNorth,BorderLayout.NORTH);
		
//		JPanel panelButton = new JPanel();
//		JButton cmdSaveCust = new JButton("เพิ่มข้อมูล");
//		JButton cmdEditCust = new JButton("แก้ไขข้อมูล");
//		JButton cmdDeleteCust = new JButton("ลบ");
//		panelButton.add(cmdSaveCust);
//		panelButton.add(cmdEditCust);
//		panelButton.add(cmdDeleteCust);
//		
//		c.add(panelButton,BorderLayout.CENTER);
		
		//ตาราง
		JPanel panelTable = new JPanel();
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(1150,300));
		
		tableCust = new JTable();
		Object data[][] = { //ข้อมูล
				{null,null,null,null,null},{null},{null},{null},{null},
				
		};
		
		String header[] = {"รหัสใบจอง","รหัสบัตรประชาขน","ชื่อ-นามสกุล","เพศ","อายุ","เบอร์โทร","เข็มที่","Location","วันที่ฉีด"};
		
		modelCustomer = new DefaultTableModel(data,header)
							{
								public boolean isCellEditable(int row, int columns)
								{
									return false; //ทำให้ table ไม่สามารถแก้ไขได้
								}
							};
		
		tableCust.setModel(modelCustomer);
		
		scrollTable.setViewportView(tableCust);
		panelTable.add(scrollTable);
		
		c.add(panelTable,BorderLayout.SOUTH);
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
}
