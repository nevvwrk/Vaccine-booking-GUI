package Booking;

import java.awt.Container;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Helepler.DateDifference;
import Helepler.JTextFieldLimit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.plaf.FontUIResource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;
public class FormVaccineBooking extends JFrame{
	Connection conn = dbConnect.getConnection();
	JTextField txtPEPID,txtPEName,txtPEAGE,txtPEADDR,txtPEPhone,txtSearch;
	JTable tableCust;
	DefaultTableModel modelCustomer;
	JRadioButton male = null;
	JRadioButton femal = null;
	JCheckBox checkboxDate;
	ButtonGroup sex = new ButtonGroup();
	JComboBox<ComboItem> comboNeedle = null,comboLocation = null;
	JDatePickerImpl datePickers,datePickers2;

	UtilDateModel model2;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat dfCrent = new SimpleDateFormat("yyyy-MM-dd HH:");
//	String lastDate;
	Repository repo = new Repository();
	Properties p = new Properties();
	VaccineHis vaccine = null;
	JButton cmdEdit,cmdDelete,cmdCancel,cmdSave;
	String vb_booking;
	boolean editTbl = false;
	
	public FormVaccineBooking()
	{

		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(1,2));
		pNorth.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


		JPanel panelForm = new JPanel();
		panelForm.setLayout(new GridLayout(6,2,5,5)); 
		panelForm.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		panelForm.setBorder(BorderFactory.createTitledBorder("ข้อมูลประชาชน"));


		
		JLabel labelPEPID = new JLabel("เลขบัตรประจำต้วประชาชน PE_PID");
		txtPEPID = new JTextField();
		txtPEPID.setPreferredSize(new Dimension(200,30));
		txtPEPID.setDocument(new JTextFieldLimit(13));
		txtPEPID.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent e)
			{
				ClearScreen();
				if(txtPEPID.getText().trim().length() == 13)
				PeopleHistory();
			}
		});
		panelForm.add(labelPEPID);
		panelForm.add(txtPEPID);
		
		JLabel labelPEName = new JLabel("ชื่อ-นามสกุล PE_NAME");
		txtPEName = new JTextField();
		txtPEName.setPreferredSize(new Dimension(200,30));
		txtPEName.setDocument(new JTextFieldLimit(100));
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
		txtPEAGE.setDocument(new JTextFieldLimit(3));
		panelForm.add(labelPEAGE);
		panelForm.add(txtPEAGE);
		
		JLabel labelPEADDR = new JLabel("ที่อยู่ PE_ADDR");
		txtPEADDR = new JTextField();
		txtPEADDR.setPreferredSize(new Dimension(200,30));
		txtPEADDR.setDocument(new JTextFieldLimit(200));
		panelForm.add(labelPEADDR);
		panelForm.add(txtPEADDR);
		
		JLabel labelPhone = new JLabel("เบอร์โทร PE_PHONE");
		txtPEPhone = new JTextField();
		txtPEPhone.setPreferredSize(new Dimension(200,30));
		txtPEPhone.setDocument(new JTextFieldLimit(50));
		panelForm.add(labelPhone);
		panelForm.add(txtPEPhone);
		
		pNorth.add(panelForm);
		
		
		//culumn 2
		JPanel panelForm2 = new JPanel();
		panelForm2.setLayout(new GridLayout(5,2,10,10));
		panelForm2.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		panelForm2.setBorder(BorderFactory.createTitledBorder("ข้อมูลวัคซีน"));
		
		JLabel labelPEBookingDate = new JLabel("วันที่ฉีด PE_BookingDate");

			
		UtilDateModel model = new UtilDateModel();		
		Calendar cal = new GregorianCalendar();		
		model.setDate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		datePickers = new JDatePickerImpl(datePanel,new DateLabelFormatter());

		panelForm2.add(labelPEBookingDate);
		panelForm2.add(datePickers);
		
		

		JLabel labelNeeldel = new JLabel("วัคซีนเข้มที่ ");
	    comboNeedle = new JComboBox(repo.Needles().toArray());
	    comboNeedle.insertItemAt(new ComboItem("", "เลือกเข็ม วัคซีน"),0);
	    comboNeedle.setSelectedIndex(0);
	    //comboNeedle.setSelectedItem(new ComboItem("01", ""));

		panelForm2.add(labelNeeldel);
		panelForm2.add(comboNeedle);
		
		JLabel labelLocation = new JLabel("Location ");
		comboLocation = new JComboBox(repo.Localtions().toArray());
		comboLocation.insertItemAt(new ComboItem("", "เลือกเข็ม Location"),0);
		comboLocation.setSelectedIndex(0);
		panelForm2.add(labelLocation);
		panelForm2.add(comboLocation);
		

		checkboxDate = new JCheckBox("วันที่ ฉีดล่าสุด");


	    model2 = new UtilDateModel();
		model2.setDate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		model2.setSelected(true);
		datePickers2 = new JDatePickerImpl(new JDatePanelImpl(model2,p),new DateLabelFormatter());	
		
		panelForm2.add(checkboxDate);
		panelForm2.add(datePickers2);
		
		JPanel panelButton = new JPanel();
		cmdSave = new JButton("เพิ่มข้อมูล");
		cmdSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				 
		    	cmdSave.setEnabled(false);
				if(Validate())
				insertBooking();
		    	cmdSave.setEnabled(true);
			}
		});
		panelButton.add(cmdSave);
		panelForm2.add(panelButton);
		
		
//		JPanel panelButtonCelar = new JPanel();
//		JButton cmdClearDate = new JButton("Clear Date");
//		cmdClearDate.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				ClearBtlastDate();
//			}
//		});
//		panelButtonCelar.add(cmdClearDate);
//		panelForm2.add(panelButtonCelar);
		
		
		pNorth.add(panelForm2);

		
		c.add(pNorth,BorderLayout.NORTH);
		
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new GridLayout(2,1)); 
		//panelcenter.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new GridLayout(1,2,5,5));
		panelcenter.setBorder(BorderFactory.createTitledBorder("ค้นหา"));
		
		
		JPanel panelTxtSearch = new JPanel();
		txtSearch = new JTextField();
		txtSearch.setPreferredSize(new Dimension(200,30));
		txtSearch.setDocument(new JTextFieldLimit(100));
		txtSearch.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent e)
			{
				editTbl = true;
				VaccineSearch();
			}
		});
		panelTxtSearch.add(txtSearch);
		panelSearch.add(panelTxtSearch);
		
		
		JPanel panelButtonsCen = new JPanel();
		cmdEdit = new JButton("แก้ไขข้อมูล");
        cmdDelete = new JButton("ลบ");
        cmdCancel = new JButton("ยกเลิก");
	    cmdEdit.setEnabled(false);
	    cmdDelete.setEnabled(false);
	    cmdCancel.setEnabled(false);
		panelButtonsCen.add(cmdEdit);
		panelButtonsCen.add(cmdDelete);
		panelButtonsCen.add(cmdCancel);
		panelSearch.add(panelButtonsCen);
		
		cmdEdit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
//				Date selectedDate = (Date)datePickers.getModel().getValue();
//				String bookingDate = df.format(selectedDate); 
				if(Validate())
				UpdateBooking();
			}
		});
		
		cmdDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DeleteBooking();
			}
		});
		
		cmdCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			    cmdEdit.setEnabled(false);
			    cmdDelete.setEnabled(false);
			    cmdCancel.setEnabled(false);
				cmdSave.setEnabled(true);
				txtPEPID.setEditable(true);
				txtPEPID.setText("");
				ClearScreen();
			}
		});

		panelcenter.add(panelSearch);
		
		//เธ•เธฒเธฃเธฒเธ�
		JPanel panelTable = new JPanel();
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(1300,100));
		
		tableCust = new JTable();
		tableCust.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(editTbl)
				{
					int index = tableCust.getSelectedRow();
					cmdSave.setEnabled(false);
				    cmdEdit.setEnabled(true);
				    cmdDelete.setEnabled(true);
				    cmdCancel.setEnabled(true);
				    txtPEPID.setEditable(false);
				    vb_booking = tableCust.getValueAt(index, 0).toString();
				    //System.out.print(tableCust.getValueAt(index, 0).toString());
				    
				    txtPEPID.setText(tableCust.getValueAt(index, 1).toString());
					txtPEName.setText(tableCust.getValueAt(index, 2).toString());
					txtPEAGE.setText(tableCust.getValueAt(index, 4).toString());
					txtPEPhone.setText(tableCust.getValueAt(index, 5).toString());
					
					VaccineHis obj = repo.GetVaccidne(tableCust.getValueAt(index, 0).toString());
					//System.out.print(obj.getNeedle());
					if(obj.getNeedle().equals(01)) comboLocation.setSelectedIndex(1);
					txtPEADDR.setText(obj.getAddre());
					System.out.print(obj.getSex());
					if(obj.getSex().equals("M"))
					{
						male.setSelected(true);
					}
					else if(obj.getSex().equals("F"))
					{
						femal.setSelected(true);
					}
					datePickers.getJFormattedTextField().setText(obj.getLastDate());
//				    comboNeedle.setSelectedItem(new ComboItem("01", ""));

					
					comboNeedle.setSelectedIndex(Integer.parseInt(obj.getNeedle()));
					comboLocation.setSelectedIndex(Integer.parseInt(obj.getLocation()));
					

				}
			}
		});
		Object data[][] = { };
		
		String header[] = {"รหัสใบจอง","รหัสบัตรประชาขน","ชื่อ-นามสกุล","เพศ","อายุ","เบอร์โทร","เข็มที่","ศูยน์ฉีด","วันที่ฉีด","ว้นที่ทำรายการ"};
		
		modelCustomer = new DefaultTableModel(data,header)
							{
								public boolean isCellEditable(int row, int columns)
								{
									return false; //เธ—เธณเน�เธซเน� table เน�เธกเน�เธชเธฒเธกเธฒเธฃเธ–เน�เธ�เน�เน�เธ�เน�เธ”เน�
								}
							};
		
		tableCust.setModel(modelCustomer);
		
		scrollTable.setViewportView(tableCust);
		panelTable.add(scrollTable);
		panelcenter.add(panelTable);
		c.add(panelcenter,BorderLayout.CENTER);
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
		UIManager.put("CheckBox.font", new Font("Tahoma", Font.PLAIN, 16));
	}
	public void UpdateBooking()
	{
		Date selectedDate = (Date)datePickers.getModel().getValue();
		String bookingDate = df.format(selectedDate); 
		
		String _sex = "";
		if(male.isSelected()) _sex = "M";
		else if(femal.isSelected()) _sex = "F";

		ComboItem needel = (ComboItem)comboNeedle.getSelectedItem();
		ComboItem localtion = (ComboItem)comboLocation.getSelectedItem();

		if(repo.UpdateBooking(vb_booking,needel.getValue(),localtion.getValue(),bookingDate,txtPEPID.getText().trim(),txtPEName.getText().trim(),txtPEAGE.getText().trim(),txtPEADDR.getText().trim(),txtPEPhone.getText().trim(),_sex) != -1)
		{
		    cmdEdit.setEnabled(false);
		    cmdDelete.setEnabled(false);
		    cmdCancel.setEnabled(false);
			cmdSave.setEnabled(true);
			txtPEPID.setEditable(true);
			txtPEPID.setText("");
			ClearScreen();
			
			JOptionPane.showMessageDialog(
                    this,
                    "แก้ไขข้อมูลเรียบร้อย",
                    "ผลการทำงาน",
                    JOptionPane.INFORMATION_MESSAGE
                );

		}
		else
		{
			JOptionPane.showMessageDialog(
                    this,
                    "แก้ไขข้อมูลไม่สำเร็จ",
                    "ผลการทำงาน",
                    JOptionPane.ERROR_MESSAGE
                );
		}
	}
//    public void GetEdit(String vb_id)
//    {
//		try
//		{
//			
//			conn = dbConnect.getConnection();
//			String SQL = "select v.vb_bookingdate,v.ne_id,v.lo_id,p.pe_addre from vaccine_booking as v "
//						 + " join people as p on v.pe_pid = v.pe_pid"
//						 + " where v.vb_id = '"+ vb_id +"' Limit 1";
//
//			ResultSet rs = conn.createStatement().executeQuery(SQL);
//
//			String x;
//			int row = 0;
//			while(rs.next())
//			{
//				 x = String.valueOf(rs.getString("ne_id")).trim();
//				System.out.print(x.trim());
//				System.out.print(x.length());
//				if(x == "01") comboNeedle.setSelectedIndex(1);
//				else if(x == "02")comboNeedle.setSelectedIndex(2);
//				else if(x == "03")comboNeedle.setSelectedIndex(3);
//				else if(x == "04")comboNeedle.setSelectedIndex(4);
//				else if(x == "05")comboNeedle.setSelectedIndex(5);
//				//result = new VaccineHis(rs.getString("vb_bookingdate"),rs.getString("ne_id"),rs.getString("lo_id"),rs.getString("pe_addre"));
//				row++;
//			}
//			conn.close();
//			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//
//
//    }
	public void DeleteBooking()
	{

		if(repo.DeleteBooking(vb_booking) != -1)
		{
		    cmdEdit.setEnabled(false);
		    cmdDelete.setEnabled(false);
		    cmdCancel.setEnabled(false);
			cmdSave.setEnabled(true);
			txtPEPID.setEditable(true);
			txtPEPID.setText("");
			ClearScreen();
			
			 JOptionPane.showMessageDialog(
                     this,
                     "ลบข้อมูลเรียบร้อย",
                     "ผลการทำงาน",
                     JOptionPane.INFORMATION_MESSAGE
                 );

		}
		else
		{
			  JOptionPane.showMessageDialog(
	                    this,
	                    "ลบข้อมูลไม่สำเร็จ",
	                    "ผลการทำงาน",
	                    JOptionPane.ERROR_MESSAGE
	                );
		}
	}
    public void insertBooking()
    {

		Date selectedDate = (Date)datePickers.getModel().getValue();
		String bookingDate = df.format(selectedDate); 
		
		String _sex = "";
		if(male.isSelected()) _sex = "M";
		else if(femal.isSelected()) _sex = "F";
		
		ComboItem needel = (ComboItem)comboNeedle.getSelectedItem();
		ComboItem localtion = (ComboItem)comboLocation.getSelectedItem();
		
		
		if(repo.insertBooking(
				txtPEPID.getText().trim(),
				txtPEName.getText().trim(),
				txtPEAGE.getText().trim(),
				txtPEADDR.getText().trim(),
				txtPEPhone.getText().trim(),
				bookingDate,
				_sex,
				needel.getValue(),
				localtion.getValue()
				) != -1)
		{
			txtPEPID.setText("");
			//ClearBtlastDate();
			ClearScreen();
			JOptionPane.showMessageDialog(
                    this,
                    "บันทึกข้อมูลเรียบร้อย",
                    "ผลการทำงาน",
                    JOptionPane.INFORMATION_MESSAGE
              );

		}
		else
		{
			JOptionPane.showMessageDialog(
                    this,
                    "บันทึกข้อมูลไม่สำเร็จ",
                    "ผลการทำงาน",
                    JOptionPane.ERROR_MESSAGE
                );
		}
		cmdSave.setEnabled(true);
    }
    public void ClearScreen()
    {
		int totalRow = tableCust.getRowCount()-1;
		while(totalRow > -1)
		{
			modelCustomer.removeRow(totalRow);
			totalRow--;
		}
		
		txtPEName.setText("");
		txtPEAGE.setText("");
		txtPEADDR.setText("");
		txtPEPhone.setText("");
		sex.clearSelection();
		comboNeedle.setSelectedIndex(0);
		comboLocation.setSelectedIndex(0);
		//ClearBtlastDate();
		checkboxDate.setSelected(false);
		txtSearch.setText("");
		this.vaccine = null;
		this.vb_booking = null;
		this.editTbl = false;

    }
    public void ClearBtlastDate()
    {
//		datePickers2.getJFormattedTextField().setText("");
//	    model2 = new UtilDateModel();
//		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2,p);
//		datePickers2 = new JDatePickerImpl(datePanel2,new DateLabelFormatter());
		
		datePickers2.getJFormattedTextField().setText("");
	    model2 = new UtilDateModel();
		datePickers2 = new JDatePickerImpl(new JDatePanelImpl(model2,p),new DateLabelFormatter());	
    }
    public void PeopleHistory()
    {

		ClearScreen();
		
		Pepole p = repo.GetPEOPLE(txtPEPID.getText().trim());
		//System.out.print(p);
		if(p != null)
		{
			txtPEName.setText(p.getName());
			txtPEAGE.setText(p.getAge());
			txtPEADDR.setText(p.getAddr());
			txtPEPhone.setText(p.getPhone());
			System.out.print(p.getSex());
			//String x = p.getSex();
			if(p.getSex().equals("M"))
			{
				male.setSelected(true);
			}
			else if(p.getSex().equals("F"))
			{
				femal.setSelected(true);
			}
			
			VaccineHistory();

		}
		
		//last date
		vaccine = repo.GetLastDate(txtPEPID.getText().trim());
		//System.out.println(lastDate);
    }
    public void VaccineSearch()
	{
		try
		{

			int totalRow = tableCust.getRowCount()-1;
			while(totalRow > -1)
			{
				modelCustomer.removeRow(totalRow);
				totalRow--;
			}
			
			conn = dbConnect.getConnection();
			String SQL = "select v.vb_id,v.pe_pid,p.pe_name,g.ge_desc,p.pe_age,p.pe_phone,n.ne_desc,l.lo_desc,v.vb_bookingDate,v.createDate,v.ne_id "
					 + " from vaccine_booking as v"
					 + " join people as p on v.pe_pid = p.pe_pid"
					 + " join gender as g on p.pe_sex = g.ge_id"
					 + " join needle as n on v.ne_id = n.ne_id"
					 + " join location as l on v.lo_id = l.lo_id"
					 + " where p.pe_pid  like '%"+ txtSearch.getText().trim() +"%'"
					 + " or p.pe_name like '%"+ txtSearch.getText().trim()+"%'"
					 + " or p.pe_phone like '%"+ txtSearch.getText().trim()+"%'"
					 + " or v.vb_id like '%"+ txtSearch.getText().trim()+"%'"
					 + " order by v.createdate desc";
						
			ResultSet rs = conn.createStatement().executeQuery(SQL);

			
			int row = 0;
			while(rs.next())
			{
				modelCustomer.addRow(new Object[0]);
				modelCustomer.setValueAt(rs.getString("vb_id"), row, 0);
				modelCustomer.setValueAt(rs.getString("pe_pid"), row, 1);
				modelCustomer.setValueAt(rs.getString("pe_name"), row, 2);
				modelCustomer.setValueAt(rs.getString("ge_desc"), row, 3);
				modelCustomer.setValueAt(rs.getString("pe_age"), row, 4);
				modelCustomer.setValueAt(rs.getString("pe_phone"), row, 5);
				modelCustomer.setValueAt(rs.getString("ne_desc"), row, 6);
				modelCustomer.setValueAt(rs.getString("lo_desc"), row, 7);
				modelCustomer.setValueAt(rs.getString("vb_bookingDate"), row, 8);
				modelCustomer.setValueAt(rs.getString("createDate"), row, 9);
				
				row++;
			}
			conn.close();
			tableCust.setModel(modelCustomer);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void VaccineHistory()
	{
		try
		{

			int totalRow = tableCust.getRowCount()-1;
			while(totalRow > -1)
			{
				modelCustomer.removeRow(totalRow);
				totalRow--;
			}
			
			conn = dbConnect.getConnection();
			String SQL = "select v.vb_id,v.pe_pid,p.pe_name,g.ge_desc,p.pe_age,p.pe_phone,n.ne_desc,l.lo_desc,v.vb_bookingDate,v.createDate "
					 + " from vaccine_booking as v"
					 + " join people as p on v.pe_pid = p.pe_pid"
					 + " join gender as g on p.pe_sex = g.ge_id"
					 + " join needle as n on v.ne_id = n.ne_id"
					 + " join location as l on v.lo_id = l.lo_id"
					 + " where p.pe_pid  = '"+ txtPEPID.getText().trim() +"' order by v.createdate desc";
						
			ResultSet rs = conn.createStatement().executeQuery(SQL);

			
			int row = 0;
			while(rs.next())
			{
				modelCustomer.addRow(new Object[0]);
				modelCustomer.setValueAt(rs.getString("vb_id"), row, 0);
				modelCustomer.setValueAt(rs.getString("pe_pid"), row, 1);
				modelCustomer.setValueAt(rs.getString("pe_name"), row, 2);
				modelCustomer.setValueAt(rs.getString("ge_desc"), row, 3);
				modelCustomer.setValueAt(rs.getString("pe_age"), row, 4);
				modelCustomer.setValueAt(rs.getString("pe_phone"), row, 5);
				modelCustomer.setValueAt(rs.getString("ne_desc"), row, 6);
				modelCustomer.setValueAt(rs.getString("lo_desc"), row, 7);
				modelCustomer.setValueAt(rs.getString("vb_bookingDate"), row, 8);
				modelCustomer.setValueAt(rs.getString("createDate"), row, 9);
				row++;
			}
			conn.close();
			tableCust.setModel(modelCustomer);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean Validate()
	{
	
		System.out.println((Date)datePickers2.getModel().getValue());
		ComboItem needel = (ComboItem)comboNeedle.getSelectedItem();
		
		if(txtPEPID.getText().trim().length() != 13 || !isNumberic(txtPEPID.getText().trim()))
		{
			if(txtPEPID.getText().trim().length() != 13)
			{
				JOptionPane.showMessageDialog(
						this,
						"ระบุรหัสบัตรประจำประชาชน 13 หลัก",
						"ผลการทำงาน",
						JOptionPane.ERROR_MESSAGE
					);
			}
			else if(!isNumberic(txtPEPID.getText().trim()))
			{
				JOptionPane.showMessageDialog(
						this,
						"ระบุรหัสบัตรประจำประชาชน 13 เป็นตัวเลข",
						"ผลการทำงาน",
						JOptionPane.ERROR_MESSAGE
					);
			}

			return false;
		}
		else if(txtPEName.getText().trim().length() == 0)
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณากรอกชื่อ-นามสกุล",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		else if(!male.isSelected() && !femal.isSelected())
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณาเลือกเพศ",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		else if(txtPEAGE.getText().trim().length() == 0 || !isNumberic(txtPEAGE.getText().trim()))
		{
			if(txtPEAGE.getText().trim().length() == 0)
			{
				JOptionPane.showMessageDialog(
						this,
						"กรุณากรอก อายุ",
						"ผลการทำงาน",
						JOptionPane.ERROR_MESSAGE
					);	
			}
			else if(!isNumberic(txtPEAGE.getText().trim()))
			{
				JOptionPane.showMessageDialog(
						this,
						"กรุณากรอก อายุ เป็นตัวเลข",
						"ผลการทำงาน",
						JOptionPane.ERROR_MESSAGE
					);
			}

			return false;
		}
		else if(txtPEADDR.getText().trim().length() == 0)
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณากรอกข้อมูลที่อยู่",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		else if(txtPEPhone.getText().trim().length() == 0)
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณากรอกข้อมูลเบอร์ติดต่อ",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		else if(comboNeedle.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณาเลือก เข็ม ที่ต้องการ ฉีด",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		else if(comboLocation.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(
					this,
					"กรุณาเลือก ศูยน์ฉีด",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		else if(repo.CheckNeedleDup(txtPEPID.getText().trim(),needel.getValue(),editTbl,vb_booking))
		{
			JOptionPane.showMessageDialog(
					this,
					"วัคซีน '"+needel.getLabel()+"' จากประวัติท่านได้ทำงานฉีดไปแล้ว กรุณาเลือกเข็มอื่นอีกครั้ง",
					"ผลการทำงาน",
					JOptionPane.ERROR_MESSAGE
				);
			return false;
		}
		

		try
		{
			System.out.println((Date)datePickers2.getModel().getValue());
			String _lastDate = null;
			
			if(checkboxDate.isSelected())
			{
				//ฉีดเข้มล่าสุด
				Date selectedDate2 = (Date)datePickers2.getModel().getValue();
				_lastDate = df.format(selectedDate2); 
				
				Date selectedDate = (Date)datePickers.getModel().getValue();
				String bookingDate = df.format(selectedDate); 
			
				 System.out.println(DateDifference.find(_lastDate,bookingDate));
				 if(DateDifference.find(_lastDate, bookingDate) < 120) //กว่า 4 month
				 {
						JOptionPane.showMessageDialog(
								this,
								"ระยะเวลาในการฉีดควรห่างจากเข็มแรก 4 เดือน",
								"ผลการทำงาน",
								JOptionPane.ERROR_MESSAGE
							);
						return false;
				 }	
			}

			
//			if(vaccine != null || checkboxDate.isSelected())
//			{
//				if(vaccine != null)
//				{
//					_lastDate = vaccine.getLastDate();
//				}
//				else if(checkboxDate.isSelected())
//				{
//					//ฉีดเข้มล่าสุด
//					Date selectedDate2 = (Date)datePickers2.getModel().getValue();
//					_lastDate = df.format(selectedDate2); 
//				}
//				
//				Date selectedDate = (Date)datePickers.getModel().getValue();
//				String bookingDate = df.format(selectedDate); 
//			
//				 System.out.println(DateDifference.find(_lastDate,bookingDate));
//				 if(DateDifference.find(_lastDate, bookingDate) < 4) //กว่า 4 month
//				 {
//						JOptionPane.showMessageDialog(
//								this,
//								"ระยะเวลาในการฉีดควรห่างจากเข็มแรก 4 เดือน",
//								"ผลการทำงาน",
//								JOptionPane.ERROR_MESSAGE
//							);
//						return false;
//				 }	
//			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.print("OK");
		return true;
	}
	public boolean isNumberic(String string)
	{
	      if (string == null || string.length() == 0) {
	            return false;
	        }

	        for (char c : string.toCharArray()) {
	            if (!Character.isDigit(c)) {
	                return false;
	            }
	        }

	        return true;
	}

}
