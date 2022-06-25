package Booking;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import FormMasterData.FormLocation;
import FormMasterData.FormNeedle;

public class MenuMasterData extends JFrame implements ActionListener {
	JButton btnlocationForm,btnneedleDataForm;
	
	public MenuMasterData()
	{
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(5,1)); 
		
		JPanel panel1 = new JPanel();
		btnlocationForm = new JButton("ข้อมูลพื้นที่");
		btnlocationForm.addActionListener(this);
		panel1.add(btnlocationForm);
		
		JPanel panel2 = new JPanel();
		btnneedleDataForm = new JButton("ข้อมูลเข็ม");
		btnneedleDataForm.addActionListener(this);
	    panel2.add(btnneedleDataForm);
	    

		
		c.add(panel1);
		c.add(panel2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnlocationForm)
		{
			FormLocation menuform = new FormLocation();
			//กำหนดขนาด
			menuform.setSize(800,680);
			//กำหนด Title bar
			menuform.setTitle("ข้อมูลพื้นที่");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
		}
		else if(e.getSource() == btnneedleDataForm)
		{
			FormNeedle menuform = new FormNeedle();
			//กำหนดขนาด
			menuform.setSize(800,680);
			//กำหนด Title bar
			menuform.setTitle("ข้อมูลเข็ม");
			//กำหนดให้กึ่งกลางหน้าจอ
			menuform.setLocationRelativeTo(null);
			//กำหนดให้แสดงผล
			menuform.setVisible(true);
			
		}
		
	}
	public void SetLanguage()
	{
		UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TitledBorder.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, 13)));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 13));	
	}
}
