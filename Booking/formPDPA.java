package Booking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

public class formPDPA extends JFrame {
	JButton btnpdpa;
	public formPDPA()
	{
		SetLanguage();
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(28,1,20,0)); 
		
		JLabel l1 = new JLabel("นโยบายความเป็นส่วนตัว",SwingConstants.CENTER);
		l1.setFont(new Font("Tahoma", Font.BOLD,27));
		c.add(l1);
		
		c.add(new JLabel("บริษัท Servac จำกัด ของเรา เอาไว้สำหรับการลงทะเบียนเพื่อจองและนัดหมายการฉีดวัคซีน ",SwingConstants.CENTER));
		c.add(new JLabel("โรคติดเชื้อโคโรนาไวรัส 2019 ชนิดของวัคซีน โมเดอร์นา                                                 ",SwingConstants.CENTER));
		c.add(new JLabel("",SwingConstants.CENTER));
		c.add(new JLabel("ในระบบการลงทะเบียนของเราจะมีการเก็บรวบรวม ชื่อ นามสกุล และเลขบัตรประชาชน            ",SwingConstants.CENTER));
		c.add(new JLabel("หมายเลขโทรศัพท์ วันเดือนปี เกิด เพื่อเป็นการยืนยันตัวตนของท่าน ประวัติการฉีดวัคซีนครั้ง  ",SwingConstants.CENTER));
		c.add(new JLabel("ล่าสุด เพื่อระบุวันนัดหมายตามคำแนะนำทางการแพทย์ ณ ปัจจุบัน โดยข้อมูลการลงทะเบียน  ",SwingConstants.CENTER));
		c.add(new JLabel("เหล่านี้ จะมีการส่งต่อไปยังสถานที่ที่ ท่านทำนัดหมายในการฉีดวัคซีนไว้ ในแต่ละวัน ในแต่ละ",SwingConstants.CENTER));
		c.add(new JLabel("วันหรือช่วงเวลา จะมีการสรุปยอดผู้รับบริการ ตามจำนวนเข็ม ตามสถานที่ หรือ ทั้งหมด เพื่อ   ",SwingConstants.CENTER));
		c.add(new JLabel("การปรับปรุงคุณภาพ โดยไม่มีการเปิดเผยข้อมูลส่วนบุคคล                                               ",SwingConstants.CENTER));
		c.add(new JLabel("",SwingConstants.CENTER));
		c.add(new JLabel("ระบบจะไม่มีการส่งข้อมูลดังกล่าวไปยังบุคคลที่สาม นอกเหนือจากที่เกี่ยวข้องกับระบบการจอง  ",SwingConstants.CENTER));
		c.add(new JLabel("วัคซีน และณ สถานที่ ฉีดวัคซีนเท่านั้น ระบบจะมีการบันทึกข้อมูลส่วนตัวของท่านไว้ ตลอดช่วง",SwingConstants.CENTER));
		c.add(new JLabel("การลงทะเบียน เพื่อการตรวจสอบ และหลังจากที่มีการปิดให้บริการการจองวัคซีนนี้ ข้อมูล        ",SwingConstants.CENTER));
		c.add(new JLabel("ส่วนตัวของท่านก็จะถูกลบออกจากระบบ                                                                            ",SwingConstants.CENTER));
		c.add(new JLabel("ท่านมีสิทธิที่จะเข้าถึงข้อมูล ยืนยันวันและสถานที่ฉีดส่วนของท่าน ท่านอาจขอลบข้อมูลทั้งหมด",SwingConstants.CENTER));
		c.add(new JLabel("ทั้งก่อนและ หลังการฉีดวัคซีนของท่าน ท่านไม่สามารถขอระงับการส่งข้อมูลไปยังสถานที่ที่ท่าน",SwingConstants.CENTER));
		c.add(new JLabel("นัดหมาย เนื่องจาก ยกเว้นท่านมีความต้องการขอยกเลิกการนัดหมายการฉีด                           ",SwingConstants.CENTER));
		c.add(new JLabel("หากท่านมีความสงสัยในมาตรการความปลอดภัยข้อมูลส่วนตัว สามารถติดต่อได้ที่ศูนย์ความ     ",SwingConstants.CENTER));
		c.add(new JLabel("ปลอดภัยของข้อมูล บริษัท Servac จำกัด 0950477***",SwingConstants.CENTER));
		c.add(new JLabel("",SwingConstants.CENTER));
		c.add(new JLabel("",SwingConstants.CENTER));
		
		JPanel panelck = new JPanel();
		JCheckBox checkboxDate = new JCheckBox();
		checkboxDate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	btnpdpa.setEnabled(false);
            	if(checkboxDate.isSelected()) btnpdpa.setEnabled(true);
            }
        });
		panelck.add(checkboxDate);
		c.add(panelck);
		
		JPanel panel5 = new JPanel();
		 btnpdpa = new JButton("ยอมรับ");
		//btnpdpa.setPreferredSize(new Dimension(250, 40));
		 btnpdpa.setEnabled(false);
		btnpdpa.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();    
			}
		});
		

		panel5.add(btnpdpa);
		c.add(panel5);
		c.add(new JLabel("",SwingConstants.CENTER));
		
	}
	public void SetLanguage()
	{
		UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TitledBorder.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, 13)));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 14));
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 16));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 13));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 13));	
	}
}
