package com.demo.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.demo.controller.GenEntityMysql;
import com.demo.controller.GenEntityOracle;
import com.demo.controller.GetFuncStructure;

public class GenEntityWindow {

	private JFrame frmGenentity;
	private JTextField textField_table;
	private JTextField textField_entity;
	private JTextField textField_url;
	private JTextField textField_name;
	private JTextField textField_password;
	private JTextField textField_outputdir;
	private JComboBox<String> comboBox_dbtype;

	private String driver = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenEntityWindow window = new GenEntityWindow();
					window.frmGenentity.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GenEntityWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGenentity = new JFrame();
		frmGenentity.setTitle("GetEntity");
		frmGenentity.setResizable(false);
		frmGenentity.setBounds(100, 100, 634, 275);
		frmGenentity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGenentity.getContentPane().setLayout(null);

		int windowWidth = frmGenentity.getWidth(); // 获得窗口宽
		int windowHeight = frmGenentity.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		frmGenentity.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示

		JButton btn_export = new JButton("\u751F\u6210");
		btn_export.setFont(new Font("宋体", Font.PLAIN, 18));
		btn_export.setBounds(495, 185, 108, 37);
		frmGenentity.getContentPane().add(btn_export);

		JLabel lblNewLabel = new JLabel("\u6570\u636E\u5E93\u7C7B\u578B:");
		lblNewLabel.setBounds(14, 16, 90, 18);
		frmGenentity.getContentPane().add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		comboBox_dbtype = new JComboBox<String>();
		comboBox_dbtype.setBounds(118, 13, 161, 24);
		frmGenentity.getContentPane().add(comboBox_dbtype);

		JLabel lblNewLabel_4 = new JLabel("URL:");
		lblNewLabel_4.setBounds(14, 47, 90, 18);
		frmGenentity.getContentPane().add(lblNewLabel_4);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_url = new JTextField();
		textField_url.setBounds(118, 44, 484, 24);
		frmGenentity.getContentPane().add(textField_url);
		textField_url.setColumns(10);

		textField_name = new JTextField();
		textField_name.setBounds(117, 75, 161, 24);
		frmGenentity.getContentPane().add(textField_name);
		textField_name.setColumns(10);
		textField_name.setText("CQ_RUNSERVICE");

		JLabel lblNewLabel_5 = new JLabel("NAME:");
		lblNewLabel_5.setBounds(15, 78, 89, 18);
		frmGenentity.getContentPane().add(lblNewLabel_5);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_6 = new JLabel("PASSWORD:");
		lblNewLabel_6.setBounds(338, 78, 89, 18);
		frmGenentity.getContentPane().add(lblNewLabel_6);
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_password = new JTextField();
		textField_password.setBounds(441, 75, 161, 24);
		frmGenentity.getContentPane().add(textField_password);
		textField_password.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u8868\u540D:");
		lblNewLabel_1.setBounds(65, 116, 38, 18);
		frmGenentity.getContentPane().add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_table = new JTextField();
		textField_table.setBounds(117, 113, 161, 24);
		frmGenentity.getContentPane().add(textField_table);
		textField_table.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\u6620\u5C04\u5B9E\u4F53\u7C7B\u540D\u79F0:");
		lblNewLabel_3.setBounds(314, 116, 113, 18);
		frmGenentity.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_entity = new JTextField();
		textField_entity.setBounds(441, 113, 161, 24);
		frmGenentity.getContentPane().add(textField_entity);
		textField_entity.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("\u8F93\u51FA\u76EE\u5F55:");
		lblNewLabel_2.setBounds(33, 151, 68, 18);
		frmGenentity.getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_outputdir = new JTextField();
		textField_outputdir.setBounds(115, 148, 393, 24);
		frmGenentity.getContentPane().add(textField_outputdir);
		textField_outputdir.setColumns(10);

		JButton btn_browse = new JButton("\u6D4F\u89C8...");
		btn_browse.setBounds(515, 147, 87, 27);
		frmGenentity.getContentPane().add(btn_browse);

		btn_browse.setHorizontalAlignment(SwingConstants.LEFT);
		comboBox_dbtype.addItem("oracle");
		comboBox_dbtype.addItem("mysql");

		// init params
		textField_outputdir.setText("F:\\output");
		textField_url.setText("jdbc:oracle:thin:@//localhost:1521/PDBORCL");
		driver = "oracle.jdbc.OracleDriver";

		// 选择数据库类型
		comboBox_dbtype.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String item = comboBox_dbtype.getSelectedItem().toString();
				if ("mysql".equals(item)) {
					driver = "com.mysql.jdbc.Driver";

					textField_url.setText("jdbc\\:mysql\\://localhost\\:3306/watf");
					textField_name.setText("root");
					textField_password.setText("");

				} else if ("oracle".equals(item)) {
					driver = "oracle.jdbc.OracleDriver";

					textField_url.setText("jdbc:oracle:thin:@//localhost:1521/PDBORCL");
					textField_name.setText("CQ_RUNSERVICE");
					textField_password.setText("");
				}
			}
		});

		// 生成
		btn_export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String table = textField_table.getText();
				String entity = textField_entity.getText();
				String url = textField_url.getText();
				String name = textField_name.getText();
				String password = textField_password.getText();
				String outputdir = textField_outputdir.getText();

				if ("".equals(table) || "".equals(entity) || "".equals(url) || "".equals(name) || "".equals(password)
						|| "".equals(outputdir) || "".equals(driver)) {
					JOptionPane.showConfirmDialog(null, "请检查输入项是否为空", "提示", JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				Map<String, String> params = new HashMap<>();
				params.put("func_location", textField_outputdir.getText());
				params.put("table_name", textField_table.getText());
				params.put("class_name", textField_entity.getText());
				params.put("db_conn_url", textField_url.getText());
				params.put("db_conn_name", textField_name.getText());
				params.put("db_conn_password", textField_password.getText());
				params.put("db_conn_driver", driver);

				try {
					// 创建目录结构
					GetFuncStructure getFuncStructure = new GetFuncStructure(params);
					getFuncStructure.getDirectories();

					// 创建实体类
					String item = comboBox_dbtype.getSelectedItem().toString();
					if ("mysql".equals(item)) {
						new GenEntityMysql(params);
					} else if ("oracle".equals(item)) {
						new GenEntityOracle(params);
					}

				} catch (Exception e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), "结果", JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JOptionPane.showConfirmDialog(null, "生成成功", "结果", JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);

			}
		});

		// 获取输出目录
		btn_browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser(".");
				fc.setFileSelectionMode(1);// 设定只能选择到文件夹
				int state = fc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
				if (state == 1) {
					return;
				} else {
					File file = fc.getSelectedFile();// f为选择到的目录
					textField_outputdir.setText(file.getAbsolutePath());
				}
			}
		});
	}
}
