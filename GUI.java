package Notes;

import javax.swing.*;
import javax.swing.text.Style;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GUI extends JFrame{
	
	private boolean isNew;
	//第一行
	private JMenuBar bar;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem news;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JMenuItem exit;
	private JMenuItem useHelp;
	private JMenuItem about;
	private JPanel none;
	
	//第二行
	private JLabel open;
	private JTextField searchLine;
	private JButton search;
	private JButton confirm;
	private JButton clear;
	
	//列表框
	private JComboBox<String> textBox;
	private JList textList;
	private List<String> txtList;
	private String defaultPath = "C:\\Users\\肖启贤\\Documents";
	private String filePath;
	private String fileName;
	private String defaultName = "未命名";
	private int defaultCount = 1;
	
	
	//文本框
	private JPanel textPane;
	private JScrollPane scrollPane;
	private JTextArea jta;
	
	
	
	private GridBagLayout gbl;
	
	public GUI() {
		init();
		this.setLayout(gbl);
		this.setTitle("记事本");
		this.setSize(800,800);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(GUI.this,"您要保存改动吗？","提示",JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					
					GUI.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
				else if (option == JOptionPane.NO_OPTION) {
					GUI.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
				else {
					return ;
				}
			}
		});
	}
	public GUI(String fileName) {
		this.filePath = fileName;
		init();
		this.setLayout(gbl);
		this.setTitle("记事本");
		this.setSize(800,800);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(GUI.this,"您要保存改动吗？","提示",JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					
					GUI.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
				else if (option == JOptionPane.NO_OPTION) {
					GUI.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
				else {
					return ;
				}
			}
		});
	}
	
	
	
	public void init() {
		bar = new JMenuBar();
		fileMenu = new JMenu("文件(F)");
		helpMenu = new JMenu("帮助(H)");
		news = new JMenuItem("新建");
		news.addActionListener(new myActionListener());
		save = new JMenuItem("保存");
		save.addActionListener(new myActionListener());
		saveAs = new JMenuItem("另存为");
		saveAs.addActionListener(new myActionListener());
		exit = new JMenuItem("退出");
		exit.addActionListener(new myActionListener());
		useHelp = new JMenuItem("使用帮助");
		about = new JMenuItem("关于记事本");	
		none = new JPanel();
		fileMenu.add(news);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(exit);
		helpMenu.add(useHelp);
		helpMenu.add(about);
		this.setJMenuBar(bar);
		this.add(none);
		bar.add(fileMenu);
		bar.add(helpMenu);
		
		open = new JLabel("打开文件");
		searchLine = new JTextField(25);
		search = new JButton("搜索");
		confirm = new JButton("确定");
		clear = new JButton("清空");
		search.addActionListener(new searchFileAL());
		confirm.addActionListener(new confirmActionListener());
		clear.addActionListener(new confirmActionListener());
		this.add(open);
		this.add(searchLine);
		this.add(search);
		this.add(confirm);
		this.add(clear);
		
		String[] asd = new String[] {"java笔记","python笔记","sql笔记" };
		txtList = new LinkedList<>();
		textBox = new JComboBox<>(asd);
		textList = new JList(txtList.toArray());
		this.add(textList);

		jta = new JTextArea();
		jta.setBackground(Color.pink);
		jta.setFont(new Font("楷体",Font.BOLD,20));
		scrollPane = new JScrollPane(jta);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollPane);
		
		//构造记事本的基本布局
		gbl = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = constraints.BOTH;
		
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		gbl.setConstraints(fileMenu, constraints);
		
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		gbl.setConstraints(helpMenu, constraints);
		
		constraints.gridwidth = 0;
		constraints.weightx = 1; //空白部分是跟着searchLine进行拉伸的
		constraints.weighty = 0;
		gbl.setConstraints(none, constraints);
		
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		gbl.setConstraints(open, constraints);
		
		constraints.gridwidth = 3;
		constraints.weightx = 1; 
		constraints.weighty = 0;
		gbl.setConstraints(searchLine, constraints);
		
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		gbl.setConstraints(search, constraints);
		
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		gbl.setConstraints(confirm, constraints);
		
		constraints.gridwidth = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		gbl.setConstraints(clear, constraints);
		
		constraints.gridwidth = 4;
		constraints.weightx = 0;
		constraints.weighty = 1;
		gbl.setConstraints(textList, constraints);
		
		constraints.gridwidth = 6;
		constraints.weightx = 0;
		constraints.weighty = 1;
		gbl.setConstraints(scrollPane, constraints);
	}
	
	private class confirmActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = searchLine.getText();
			if (path.length() != 0) {
				File file = new File(path);
				try {
					InputStream is = new FileInputStream(file);
					byte[] flush = new byte[1024 * 1024];
					int len = -1;
					while ((len = is.read(flush)) != -1) {
						if (jta.getText().length() == 0) { //如果还没有写东西，则直接在本窗口打开
							jta.setText(new String(flush,0,len));
						}
						else {
							GUI newWin = new GUI();
							newWin.writeText(new String(flush,0,len));
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	private class searchFileAL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equals("搜索")) {
			JFileChooser jfc = new JFileChooser();
			int val = jfc.showOpenDialog(null);
			if (val == jfc.APPROVE_OPTION) {
				searchLine.setText(jfc.getSelectedFile().toString());
			}
			else {
				searchLine.setText("未找到该文件");
			}
			}
			else if (e.getActionCommand().equals("清空")) {
				searchLine.setText("");
			}
		}
		
	}
	
	private class myActionListener implements ActionListener {

		BufferedOutputStream bos ;
		int len = -1;
		byte[] flush = new byte[1024 * 1024];
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equals("新建") && jta.getText().length() != 0) {
				new GUI();
			}
			else if (e.getActionCommand().equals("保存")) {
				if (fileName == null) {
				fileName = JOptionPane.showInputDialog("请输入文件名字");
				writeIn(GUI.this.defaultPath + "\\" + fileName,jta.getText());
				}
				else {
					if (filePath == null) {
						writeIn(GUI.this.defaultPath + "\\" + fileName,jta.getText());
					}
					else {
						writeIn(GUI.this.filePath,jta.getText());
					}
				}
			}
			else if (e.getActionCommand().equals("另存为")) {
				GUI.this.filePath = JOptionPane.showInputDialog("请输入文件绝对路径");
				fileName = new File(filePath).getName();
				writeIn(GUI.this.filePath,jta.getText());
			}
			else if (e.getActionCommand().equals("退出")) {
				if (filePath != null) {
					writeIn(GUI.this.filePath,jta.getText());
				}
				else {
					if (fileName != null) {
						writeIn(GUI.this.defaultPath + "\\" + fileName,jta.getText());
					}
					else {
						Date date = new Date();
						fileName = "未命名" + (date.getMonth() + 1 ) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
						writeIn(GUI.this.defaultPath + "\\" + fileName,jta.getText());
					}
				}
			}
		}
		
		public void writeIn(String path,String str) {
			try {
				File f = new File(path);
				bos = new BufferedOutputStream(new FileOutputStream(f));
				bos.write(str.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void writeText(String text) {
		jta.setText(text);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
