package FormMasterData;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;




import Booking.dbConnect;


public class FormLocation extends JFrame {
	
	Connection conn = dbConnect.getConnection();
	JButton btSave,btEdit,btDelete,btSearch;
	JTextField lo_id, lo_desc,  tfSearch;
	
	
	JTable tableLocation;
	DefaultTableModel modelLocation;
	
	public FormLocation()
	{
		if(conn != null) {
			System.out.println("Database connected");
		} else {
			System.out.println("Not connected");
		}
		
		SetLanguage();
		Container c = this.getContentPane();
		
		c.setLayout(new GridLayout(4,1));
		
	
		JPanel panelHeader = new JPanel();
		JLabel lblInfo = new JLabel("รายละเอียดศูนย์",SwingConstants.CENTER);
		panelHeader.add(lblInfo);
		c.add(panelHeader);
		
		
		JPanel panelInsert = new JPanel(new FlowLayout());
	
		
		JPanel panelInput = new JPanel(new GridLayout(2,1,10,10));
		
		
		JLabel lblLO_ID = new JLabel("รหัสศูนย์ LO_ID");
		lo_id = new JTextField();
		lo_id.setPreferredSize(new Dimension(200,30));

	
		JLabel lblLO_DESC = new JLabel("รายละเอียด LO_DESC");
		lo_desc = new JTextField();
		lo_desc.setPreferredSize(new Dimension(200,30));
		
		
		
		panelInput.add(lblLO_ID);
		panelInput.add(lo_id);
		panelInput.add(lblLO_DESC);
		panelInput.add(lo_desc);
		
	
		JPanel panelSearch = new JPanel();
		Border margin = new EmptyBorder(10,10,10,10);	
		panelSearch.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.gray,1),margin));
		
		
		tfSearch = new JTextField("Search");
		tfSearch.setPreferredSize(new Dimension(200,40));
		tfSearch.setMargin(new Insets(10,10,10,10));
		
	
		tfSearch.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				tfSearch.setText("");
				tfSearch.setForeground(Color.gray);
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if(tfSearch.getText().length() == 0) {
					tfSearch.setText("Search");
					tfSearch.setForeground(Color.gray);
				}
			}
		});
		
		tfSearch.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				if(tfSearch.getText() != "") {
					searchData();
				}else {
					showData();
				}
			}
		});
			
		panelSearch.add(tfSearch);

		panelInsert.add(panelInput);
		panelInsert.add(panelSearch);
		
		c.add(panelInsert);
		
		

		JPanel panelButton = new JPanel(new FlowLayout());	
		btSave = new JButton("บันทึก");
		btSave.setPreferredSize(new Dimension(100,40));
		btSave.setForeground(Color.white);
		btSave.setBackground(Color.green);
		
		btSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				insert();
			}
		}
		);
		
		btEdit = new JButton("แก้ไข");
		btEdit.setPreferredSize(new Dimension(100,40));
		btEdit.setForeground(Color.black);
		btEdit.setBackground(Color.yellow);
		btEdit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				update();
			}
		}
		);
		
		btDelete = new JButton("ลบ");
		btDelete.setPreferredSize(new Dimension(100,40));
		btDelete.setForeground(Color.white);
		btDelete.setBackground(Color.red);
		btDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		}
		);
		
		panelButton.add(btSave);
		panelButton.add(btEdit);
		panelButton.add(btDelete);
		
		c.add(panelButton);
		
		JPanel panelTable = new JPanel();
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(750,106));
		
		tableLocation = new JTable();
		tableLocation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = tableLocation.getSelectedRow();
				btSave.setEnabled(true);
				lo_id.setEditable(false);
				lo_id.setText(tableLocation.getValueAt(index, 0).toString());
				lo_desc.setText(tableLocation.getValueAt(index, 1).toString());
				
			}
		});
		
		Object data[][] = {
				{null,null,null},
				{null,null,null},
				{null,null,null},
				{null,null,null},
				{null,null,null}
		};
		
		String header [] = {"รหัสศูนย์","รายละเอียด","Active"};
		
		modelLocation = new DefaultTableModel(data,header) {
			public boolean isCellEditable(int row,int columns) {
				return false;
			}
		};
		
		tableLocation.setModel(modelLocation);
		scrollTable.setViewportView(tableLocation);
		panelTable.add(scrollTable);
		
		c.add(panelTable);

		showData();
}
	
	
	
	public void showData() {
		
		
		try {
			int totalRow = tableLocation.getRowCount()-1;
			
			while(totalRow > -1) {
				modelLocation.removeRow(totalRow);
				totalRow--;
			}
			
			//String search is push value of tfSearch to String search
			//.trim() is remove spacing on textField
			
			
			
			String SQL = "select * from location";
					 
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			
			int row=0;			
			
			while(rs.next()) {
				modelLocation.addRow(new Object[0]);
				modelLocation.setValueAt(rs.getString("lo_id"), row, 0);
				modelLocation.setValueAt(rs.getString("lo_desc"), row, 1);
				modelLocation.setValueAt(rs.getString("active"), row,2);
				
				
				row++;
			}
			tableLocation.setModel(modelLocation);
			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void searchData() {
		
		
		try {
			int totalRow = tableLocation.getRowCount()-1;
			
			while(totalRow > -1) {
				modelLocation.removeRow(totalRow);
				totalRow--;
			}
			
			//String search is push value of tfSearch to String search
			//.trim() is remove spacing on textField
			
			String search = tfSearch.getText().trim();
			
			
			String SQL = "select * from location"
					+ " WHERE lo_id LIKE '%"+ search +"%' OR"
					+ " lo_desc LIKE '%"+ search +"%' ";
					
				 
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			
			int row=0;
			while(rs.next()) {
				modelLocation.addRow(new Object[0]);
				
				modelLocation.setValueAt(rs.getString("lo_id"), row, 0);
				modelLocation.setValueAt(rs.getString("lo_desc"), row, 1);
				modelLocation.setValueAt(rs.getString("active"), row, 2);
				row++;
			}
			tableLocation.setModel(modelLocation);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert() {
		
		try {
		
		if(lo_id.getText().trim() == "") {
			JOptionPane.showMessageDialog(
					this,
					"ระบุรหัส",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String sql = "INSERT INTO LOCATION VALUES (?,?,?)";
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1,lo_id.getText().trim());
		pre.setString(2,lo_desc.getText().trim());
		pre.setString(3, "Y");
		
		
		if(pre.executeUpdate() != -1) {
			JOptionPane.showMessageDialog(
					this,
					"บันทึกเรียบร้อย",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
			showData();
			lo_id.setText("");
			lo_desc.setText("");
			
		}
		} catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	
	public void update() {
		
		try {
		
		String sql = "UPDATE LOCATION SET lo_id=?," 
				+ " lo_desc=? "
				+ " WHERE lo_id=? " ;
		PreparedStatement pre = conn.prepareStatement(sql);
		
		pre.setString(1,lo_id.getText().trim());
		pre.setString(2,lo_desc.getText().trim());
		pre.setString(3, "YES");
		pre.setString(3,lo_id.getText().trim());
		
		
			if(pre.executeUpdate() != -1) {
				JOptionPane.showMessageDialog(
					this,
					"แก้ไขเรียบร้อย",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
				showData();
				btEdit.setEnabled(true);
				lo_id.setEditable(true);
				lo_id.setText("");
				lo_desc.setText("");
			
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	public void delete() {
	try {
		
		String sql = "DELETE FROM LOCATION" 
				+ " WHERE lo_id=? " ;
				
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1,lo_id.getText().trim());
		
		if(pre.executeUpdate() != -1) {
			JOptionPane.showMessageDialog(
					this,
					"ลบเรียบร้อย",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
			showData();
			btSave.setEnabled(true);
			lo_id.setEditable(true);
			lo_id.setText("");
			lo_desc.setText("");
			
		}
	} catch(SQLException e){
		e.printStackTrace();
	}
	
	}
	
	public void SetLanguage(){
		UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("TitledBorder.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, 16)));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 16));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("RadioButton.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 16));
	}
}
