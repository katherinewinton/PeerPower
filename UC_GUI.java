package hackCupertino;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UC_GUI implements MouseListener {
	
	private JFrame frame;
	private JPanel panel, uno, dos;
	private JTabbedPane tabbed;
	private JScrollPane pane1, pane2;
	private JTextArea text, bulletin, events;
	private JTextField msg, u, d;
	private String teacher = "", courseName = "", course = "", chatsOld = "", chatsNew = "", chat = "", username = "", school = "", bulletinText="Assignments/Exams:\n";
	private JButton send, add;
	private JLabel addUpcoming, addDate;
	private LinkedList<String> dates = new LinkedList<String>(), items=new LinkedList<String>();
	
	@SuppressWarnings("resource")
	private void setInfo(String course) {
		Scanner s = new Scanner(course);
		s.useDelimiter(", ");
		teacher = s.next();
		courseName = s.next();
		if(course.contains(":")) {
			chatsOld = s.next();
			Scanner s2 = new Scanner(chatsOld);
			s2.useDelimiter("; ");
			while(s2.hasNext()) {
				chat = chat + s2.next()+ "\n\n" ;
			}
		}
		String date, item;
		while(s.hasNext()) {
			date = s.next(); item=s.next();
			if(date!=null) bulletinText+="\n"+date+": "+item+ "\n\n";
			dates.add(date); items.add(item);
		}
		
		this.course = course;
	}
	
	public UC_GUI(String course, String username, String school) {
		setInfo(course);
		
		frame = new JFrame(teacher + ": " + courseName);
		frame.setBounds(100, 100, 500, 650);
		frame.setResizable(false);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(new Color(211, 224, 213));
		
		tabbed = new JTabbedPane();
		tabbed.setBackground(Color.white);
		tabbed.setFont(new Font("Times", Font.PLAIN, 16));
		panel.add(tabbed);

		upcomings();
		chat();
		
		frame.add(panel);
		frame.setVisible(true);
		
		this.username = username;
		this.school = school;
	}
	
	static JLabel monthLbl;
	static JLabel yearLbl;
	static JButton prevBtn;
	static JButton nextBtn;
	static JTable calTable;
	static JComboBox yearCombo;
	static JFrame mainFrame;
    static Container pane;
    static DefaultTableModel calTableModel;
    static JScrollPane calScrollTable;
    static JPanel calPanel;
    static int day;
    static int month;
    static int year;
	
	private void upcomings() {
		uno = new JPanel(); uno.setLayout(null); uno.setBackground(new Color(211, 224, 213));
		GregorianCalendar cal = new GregorianCalendar();
    	day = cal.get(GregorianCalendar.DAY_OF_MONTH);
    	month = cal.get(GregorianCalendar.MONTH);
    	year = cal.get(GregorianCalendar.YEAR);
    	String[] calMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	
    	monthLbl = new JLabel(calMonths[month]);
    	yearLbl = new JLabel(Integer.toString(year));
    	prevBtn = new JButton("<");
    	nextBtn = new JButton(">");
    	calTableModel = new DefaultTableModel();
    	calTable = new JTable(calTableModel);
    	calScrollTable = new JScrollPane(calTable);
    	yearCombo = new JComboBox();
    	calPanel = new JPanel(null);
    	calPanel.setBackground(new Color(211, 224, 213));
    	
    	uno.add(calPanel);
    	calPanel.setBounds(0, 0, 500, 250);
    	calPanel.add(monthLbl);
    	monthLbl.setBounds(200, 0, 75, 50);
    	calPanel.add(yearLbl);
    	yearLbl.setBounds(250, 0, 100, 50);
    	calPanel.add(prevBtn);
    	prevBtn.setBounds(125, 20, 15, 15);
    	calPanel.add(nextBtn);
    	nextBtn.setBounds(335, 20, 15, 15);
    	calPanel.add(calScrollTable);
    	yearCombo.setBounds(230, 305, 80, 20);
    	calPanel.add(yearCombo);
    	calScrollTable.setBounds(110, 50, 250, 175);
    	
    	String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    	for(int i = 0; i < 7; i++) {
    		calTableModel.addColumn(days[i]);
    	}
    	
    	calTable.setRowHeight(25);
        calTableModel.setColumnCount(7);
        calTableModel.setRowCount(6);
        
        GregorianCalendar calFill = new GregorianCalendar(year, month, 1);
        int maxDays = calFill.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int firstOfMonthDay = calFill.get(GregorianCalendar.DAY_OF_WEEK);

        for (int i = 1; i <= maxDays; i++){
            int row = (i + firstOfMonthDay - 2) / 7;
            int column  = (i + firstOfMonthDay - 2) % 7;
            calTableModel.setValueAt(i, row, column);
        }
        
         prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
        monthLbl.setText(calMonths[month]);
        yearCombo.setSelectedItem(String.valueOf(year));
        
        nextBtn.addMouseListener(this);
        prevBtn.addMouseListener(this);
        calTable.addMouseListener(this);
        
        events = new JTextArea("Events:\n"); events.setBounds(10, 250, 440, 100);
        events.setEditable(false); events.setBackground(new Color(211, 224, 213));
        uno.add(events);
        
        add = new JButton("Add upcoming"); add.setBounds(10, 420, 130, 50); add.setFont(new Font("Times", Font.PLAIN, 16));
		addUpcoming = new JLabel("Name of upcoming:"); addUpcoming.setBounds(10, 470, 150, 30); addUpcoming.setFont(new Font("Times", Font.PLAIN, 16));
		addDate = new JLabel("Date of upcoming:"); addDate.setBounds(10, 500, 150, 30); addDate.setFont(new Font("Times", Font.PLAIN, 16));
		u = new JTextField(""); u.setBounds(140, 470, 300, 30); u.setFont(new Font("Times", Font.PLAIN, 16));
		d = new JTextField(""); d.setBounds(140, 500, 300, 30); d.setFont(new Font("Times", Font.PLAIN, 16));
		uno.add(add); uno.add(addUpcoming);  uno.add(u); uno.add(addDate); uno.add(d);
		
		add.addMouseListener(this);
        
        tabbed.add("Upcoming", uno);
	}
	
	public void updateCalendar(int month, int year) {
    	
    	String[] calMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	monthLbl.setText(calMonths[month]);
    	
    	for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                calTableModel.setValueAt(null, i, j);
            }
        }
    	
    	GregorianCalendar calFill = new GregorianCalendar(year, month, 1);
        int maxDays = calFill.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int firstOfMonthDay = calFill.get(GregorianCalendar.DAY_OF_WEEK);
        
        for (int i = 1; i <= maxDays; i++){
            int row = (i + firstOfMonthDay - 2) / 7;
            int column  = (i + firstOfMonthDay - 2) % 7;
            calTableModel.setValueAt(i, row, column);
        }
    	
    }
	
	private void chat() {
		dos = new JPanel(); dos.setLayout(null); dos.setBackground(new Color(211, 224, 213));
		pane1 = new JScrollPane();
		text = new JTextArea(chat); text.setBackground(new Color(211, 224, 213)); text.setEditable(false); text.setLineWrap(true);  text.setFont(new Font("Times", Font.PLAIN, 16));
		pane1.setViewportView(text); pane1.setBounds(10, 10, 440, 490);
		msg = new JTextField(""); msg.setBounds(10, 500, 350, 50); msg.setFont(new Font("Times", Font.PLAIN, 16));
		send = new JButton("Send"); send.setBounds(350, 500, 100, 50); send.setFont(new Font("Times", Font.PLAIN, 16));
		dos.add(pane1); dos.add(msg); dos.add(send);
		tabbed.addTab("Chat", dos);
		
		send.addMouseListener(this);
	}
	
	private void showItems(int month, int day, int year) {
		events.setText("Events:\n\n");
		for(int i=0; i<dates.size(); i++) {
			Scanner scSlash = new Scanner(dates.get(i));
		     scSlash.useDelimiter("/");
		     int m = Integer.valueOf(scSlash.next())-1;
		     int d = Integer.valueOf(scSlash.next());
		     int y = Integer.valueOf(scSlash.next());
		     if(m==month && d==day && y==year) {
		    	 events.setText(events.getText()+items.get(i)+"\n");
		     }
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(send)) {
			chatsNew = chatsOld + "; " + username+": "+msg.getText();
			String pt1 = course.substring(0, course.indexOf(chatsOld));
			String pt3 = course.substring(course.indexOf(chatsOld)+chatsOld.length());
			course = pt1+chatsNew+pt3;
			text.setText(text.getText()+username+": "+msg.getText()+"\n\n");
			msg.setText("");
			try {
				Scanner s = new Scanner(new File("hackCupertino/"+school+".txt"));
				s.useDelimiter("\n"); 
				String item;
				String add = course;
				while(s.hasNext()) {
					item = s.next();
					if(item.startsWith(teacher)) continue;
					else add+="\n"+item;
				}
				File f = new File("hackCupertino/"+school+".txt"); FileWriter fw = new FileWriter(f, false);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(add);
				pw.flush(); pw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource().equals(add)) {
			try {
				Scanner s = new Scanner(new File("hackCupertino/"+school+".txt"));
				s.useDelimiter("\n"); 
				String item;
				String add = course+", "+d.getText()+", "+u.getText();
				while(s.hasNext()) {
					item = s.next();
					if(item.startsWith(teacher)) continue;
					else add+="\n"+item;
				}
				File f = new File("hackCupertino/"+school+".txt"); FileWriter fw = new FileWriter(f, false);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(add);
				pw.flush(); pw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dates.add(d.getText()); items.add(u.getText());
			u.setText(""); d.setText("");
			showItems(month, day, year);
		}
		
		
		if(e.getSource().equals(nextBtn)) {
        	if(month == 12) {
        		year += 1;
        		month = 0;
        	}else {
        		month += 1;
        	}
        	updateCalendar(month, year);
		}
		if(e.getSource().equals(prevBtn)) {
			if(month == 0) {
        		year -= 1;
        		month = 12;
        	}else {
        		month -= 1;
        	}
        	updateCalendar(month, year);
		}
		if(e.getSource().equals(calTable)) {
			int row = calTable.rowAtPoint(e.getPoint());
			int column = calTable.columnAtPoint(e.getPoint());
			 day = Integer.valueOf(String.valueOf(calTable.getModel().getValueAt(row, column)));
			 showItems(month, day, year);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
