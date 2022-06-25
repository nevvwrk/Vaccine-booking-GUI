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

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JButton;
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


public class FormNeedle extends JFrame {
	
	Connection conn = dbConnect.getConnection();
	JButton btSave,btEdit,btDelete,btSearch;
	JTextField ne_id, ne_desc,  tfSearch;
	
	
	JTable tableNeedle;
	DefaultTableModel modelNeedle;
	
	public FormNeedle()
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
		JLabel lblInfo = new JLabel("ข้อมูลเข็มวัคซีน",SwingConstants.CENTER);
		panelHeader.add(lblInfo);
		c.add(panelHeader);
		
		
		JPanel panelInsert = new JPanel(new FlowLayout());
	
		
		JPanel panelInput = new JPanel(new GridLayout(2,1,10,10));
		
		
		JLabel lblNeId = new JLabel("รหัสเข็ม NE_ID");
		ne_id = new JTextField();
		ne_id.setPreferredSize(new Dimension(200,30));

	
		JLabel lblNeDesc = new JLabel("รายละเอียด NE_DESC");
		ne_desc = new JTextField();
		ne_desc.setPreferredSize(new Dimension(200,30));
		
		
		panelInput.add(lblNeId);
		panelInput.add(ne_id);
		panelInput.add(lblNeDesc);
		panelInput.add(ne_desc);
		
	
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
		btEdit.setForeground(Color.white);
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
		scrollTable.setPreferredSize(new Dimension(400,106));
		
		tableNeedle = new JTable();
		tableNeedle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = tableNeedle.getSelectedRow();
				btSave.setEnabled(true);
				ne_id.setEditable(false);
				ne_id.setText(tableNeedle.getValueAt(index, 0).toString());
				ne_desc.setText(tableNeedle.getValueAt(index, 1).toString());
			}
		});
		
		Object data[][] = {
				{null,null},
				{null,null},
				{null,null},
				{null,null},
				{null,null}
		};
		
		String header [] = {"รหัสเข็ม","รายละเอียด"};
		
		modelNeedle = new DefaultTableModel(data,header) {
			public boolean isCellEditable(int row,int columns) {
				return false;
			}
		};
		
		tableNeedle.setModel(modelNeedle);
		scrollTable.setViewportView(tableNeedle);
		panelTable.add(scrollTable);
		
		c.add(panelTable);

		showData();
	}
	
	public void showData() {
			
		try {
			int totalRow = tableNeedle.getRowCount()-1;
			
			while(totalRow > -1) {
				modelNeedle.removeRow(totalRow);
				totalRow--;
			}
			
			//String search is push value of tfSearch to String search
			//.trim() is remove spacing on textField
			
			/*String search = tfSearch.getText().trim();
			String SQL = "select * from needle"
					//**where need spacing on front
					+ " WHERE ne_id LIKE '%"+search+"%' OR"
					+ " ne_desc LIKE '%"+search+"%' ";
			*/
			
			String SQL = "select * from needle";
					 
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			
			int row=0;
			while(rs.next()) {
				modelNeedle.addRow(new Object[0]);
				
				modelNeedle.setValueAt(rs.getString("ne_id"), row, 0);
				modelNeedle.setValueAt(rs.getString("ne_desc"), row, 1);
				row++;
			}
			tableNeedle.setModel(modelNeedle);
			
			
			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void searchData() {
		
		
		try {
			int totalRow = tableNeedle.getRowCount()-1;
			
			while(totalRow > -1) {
				modelNeedle.removeRow(totalRow);
				totalRow--;
			}
			
			//String search is push value of tfSearch to String search
			//.trim() is remove spacing on textField
			
			String search = tfSearch.getText().trim();
			String SQL = "select * from needle"
					//**where need spacing on front
					+ " WHERE ne_id LIKE '%"+search+"%' OR"
					+ " ne_desc LIKE '%"+search+"%' ";
			
					 
			ResultSet rs = conn.createStatement().executeQuery(SQL);
			
			
			int row=0;
			while(rs.next()) {
				modelNeedle.addRow(new Object[0]);
				
				modelNeedle.setValueAt(rs.getString("ne_id"), row, 0);
				modelNeedle.setValueAt(rs.getString("ne_desc"), row, 1);
				row++;
			}
			tableNeedle.setModel(modelNeedle);
			
			
			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert() {
		
		try {
		String sql = "INSERT INTO NEEDLE VALUES (?,?,?)";
		PreparedStatement pre = conn.prepareStatement(sql);
		if(ne_id.getText().trim() == "" || ne_desc.getText() == "") {
			JOptionPane.showMessageDialog(
					this,
					"กรุณากรอกข้อมูล",
					"ผลกรทำงาน",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}else {
			pre.setString(1,ne_id.getText().trim());
			pre.setString(2,ne_desc.getText().trim());
			pre.setString(3, "Y");
		}
		
		if(pre.executeUpdate() != -1) {
			JOptionPane.showMessageDialog(
					this,
					"บันทึกข้อมูลเรียบร้อยแล้ว",
					"ผลกรทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
			showData();
			ne_id.setText("");
			ne_desc.setText("");
			
		}
		} catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	
	public void update() {
		
		try {
		
		String sql = "UPDATE NEEDLE SET NE_ID=?," 
				+ " ne_desc=? "
				+ " WHERE ne_id=? " ;
		PreparedStatement pre = conn.prepareStatement(sql);
		
		pre.setString(1,ne_id.getText().trim());
		pre.setString(2,ne_desc.getText().trim());
		pre.setString(3,ne_id.getText().trim());
		
		
			if(pre.executeUpdate() != -1) {
				JOptionPane.showMessageDialog(
					this,
					"อัพเดทข้อมูลสำเร็จแล้ว",
					"ผลกรทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
				showData();
				btEdit.setEnabled(true);
				ne_id.setEditable(true);
				ne_id.setText("");
				ne_desc.setText("");
			
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	
	}
	public void delete() {
	try {
		
		String sql = "DELETE FROM NEEDLE" 
				+ " WHERE ne_id=? " ;
				
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1,ne_id.getText().trim());
		
		if(pre.executeUpdate() != -1) {
			JOptionPane.showMessageDialog(
					this,
					"ลบข้อมูลเรียบร้อยแล้ว",
					"ผลกรทำงาน",
					JOptionPane.INFORMATION_MESSAGE
					);
			showData();
			btSave.setEnabled(true);
			ne_id.setEditable(true);
			ne_id.setText("");
			ne_desc.setText("");
			
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
