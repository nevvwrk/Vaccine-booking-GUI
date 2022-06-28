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

public class FormGender extends JFrame {
Connection conn = dbConnect.getConnection();
	JButton btSave,btEdit,btDelete,btSearch;
	JTextField ge_id, ge_desc,  tfSearch;
	
	
	JTable tableGender;
	DefaultTableModel modelGender;
	
	public FormGender()
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
		JLabel lblInfo = new JLabel("รายละเอียดเพศ",SwingConstants.CENTER);
		panelHeader.add(lblInfo);
		c.add(panelHeader);
		
		
		JPanel panelInsert = new JPanel(new FlowLayout());
	
		
		JPanel panelInput = new JPanel(new GridLayout(2,1,10,10));
		
		
		JLabel lblge_id = new JLabel("รหัสศูนย์ ge_id");
		ge_id = new JTextField();
		ge_id.setPreferredSize(new Dimension(200,30));

	
		JLabel lblge_desc = new JLabel("รายละเอียด ge_desc");
		ge_desc = new JTextField();
		ge_desc.setPreferredSize(new Dimension(200,30));
		
		
		
		panelInput.add(lblge_id);
		panelInput.add(ge_id);
		panelInput.add(lblge_desc);
		panelInput.add(ge_desc);
		
	
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
		
		tableGender = new JTable();
		tableGender.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = tableGender.getSelectedRow();
				btSave.setEnabled(true);
				ge_id.setEditable(false);
				ge_id.setText(tableGender.getValueAt(index, 0).toString());
				ge_desc.setText(tableGender.getValueAt(index, 1).toString());
				
			}
		});
		
		Object data[][] = {
				{null,null},
				{null,null},
				{null,null},
				{null,null},
				{null,null}
		};
		
		String header [] = {"ข้อมูลเพศ","รายละเอียด"};
		
		modelGender = new DefaultTableModel(data,header) {
			public boolean isCellEditable(int row,int columns) {
				return false;
			}
		};
		
		tableGender.setModel(modelGender);
		scrollTable.setViewportView(tableGender);
		panelTable.add(scrollTable);
		
		c.add(panelTable);

		showData();
}
	
	
	
	public void showData() {
		
		
		try {
			int totalRow = tableGender.getRowCount()-1;
			
			while(totalRow > -1) {
				modelGender.removeRow(totalRow);
				totalRow--;
			}
			
			//String search is push value of tfSearch to String search
			//.trim() is remove spacing on textField
			
			
			
			String SQL = "select * from gender";
					 
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			
			int row=0;			
			
			while(rs.next()) {
				modelGender.addRow(new Object[0]);
				modelGender.setValueAt(rs.getString("ge_id"), row, 0);
				modelGender.setValueAt(rs.getString("ge_desc"), row, 1);
				
				
				
				row++;
			}
			tableGender.setModel(modelGender);
			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void searchData() {
		
		
		try {
			int totalRow = tableGender.getRowCount()-1;
			
			while(totalRow > -1) {
				modelGender.removeRow(totalRow);
				totalRow--;
			}
			
			//String search is push value of tfSearch to String search
			//.trim() is remove spacing on textField
			
			String search = tfSearch.getText().trim();
			
			
			String SQL = "select * from gender"
					
					+ " WHERE ge_desc LIKE '%"+ search +"%' ";
					
				 
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			
			int row=0;
			while(rs.next()) {
				modelGender.addRow(new Object[0]);
				
				modelGender.setValueAt(rs.getString("ge_id"), row, 0);
				modelGender.setValueAt(rs.getString("ge_desc"), row, 1);
				
				row++;
			}
			tableGender.setModel(modelGender);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert() {
		
		try {
		
		if(ge_id.getText().trim() == "") {
			JOptionPane.showMessageDialog(
					this,
					"ระบุรหัส",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String sql = "INSERT INTO gender VALUES (?,?)";
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1,ge_id.getText().trim());
		pre.setString(2,ge_desc.getText().trim());
		
		
		
		if(pre.executeUpdate() != -1) {
			JOptionPane.showMessageDialog(
					this,
					"บันทึกเรียบร้อย",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
			showData();
			ge_id.setText("");
			ge_desc.setText("");
			
		}
		} catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	
	public void update() {
		
		try {
		
		String sql = "UPDATE gender SET ge_id=?," 
				+ " ge_desc=? "
				+ " WHERE ge_id=? " ;
		PreparedStatement pre = conn.prepareStatement(sql);
		
		pre.setString(1,ge_id.getText().trim());
		pre.setString(2,ge_desc.getText().trim());
		
		pre.setString(3,ge_id.getText().trim());
		
		
			if(pre.executeUpdate() != -1) {
				JOptionPane.showMessageDialog(
					this,
					"แก้ไขเรียบร้อย",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
				showData();
				btEdit.setEnabled(true);
				ge_id.setEditable(true);
				ge_id.setText("");
				ge_desc.setText("");
			
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	public void delete() {
	try {
		
		String sql = "DELETE FROM gender" 
				+ " WHERE ge_id=? " ;
				
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1,ge_id.getText().trim());
		
		if(pre.executeUpdate() != -1) {
			JOptionPane.showMessageDialog(
					this,
					"ลบเรียบร้อย",
					"ผลการทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
			showData();
			btSave.setEnabled(true);
			ge_id.setEditable(true);
			ge_id.setText("");
			ge_desc.setText("");
			
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
