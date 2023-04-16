package hackCupertino;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.*;

public class UC_GUI_backup implements MouseListener {
	
	private JFrame frame;
	private JPanel panel, uno, dos;
	private JTabbedPane tabbed;
	private JScrollPane pane1, pane2;
	private JTextArea text, bulletin;
	private JTextField msg, u, d;
	private String teacher = "", courseName = "", course = "", chatsOld = "", chatsNew = "", chat = "", username = "", school = "", bulletinText="Assignments/Exams:\n";
	private JButton send, add;
	private JLabel addUpcoming, addDate;
	
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
		String item;
		while(s.hasNext()) {
			item = s.next();
			if(item!=null) bulletinText+="\n"+item+": "+s.next() + "\n\n";
		}
		
		this.course = course;
	}
	
	public UC_GUI_backup(String course, String username, String school) {
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
	
	private void upcomings() {
		uno = new JPanel(); uno.setLayout(null); uno.setBackground(new Color(211, 224, 213));
		pane2 = new JScrollPane();
		bulletin = new JTextArea(bulletinText); bulletin.setBackground(new Color(211, 224, 213)); bulletin.setEditable(false); bulletin.setLineWrap(true); 
		bulletin.setFont(new Font("Times", Font.PLAIN, 16));
		pane2.setViewportView(bulletin); pane2.setBounds(10, 10, 440, 400);
		add = new JButton("Add upcoming"); add.setBounds(10, 420, 130, 50); add.setFont(new Font("Times", Font.PLAIN, 16));
		addUpcoming = new JLabel("Name of upcoming:"); addUpcoming.setBounds(10, 470, 150, 30); addUpcoming.setFont(new Font("Times", Font.PLAIN, 16));
		addDate = new JLabel("Date of upcoming:"); addDate.setBounds(10, 500, 150, 30); addDate.setFont(new Font("Times", Font.PLAIN, 16));
		u = new JTextField(""); u.setBounds(140, 470, 300, 30); u.setFont(new Font("Times", Font.PLAIN, 16));
		d = new JTextField(""); d.setBounds(140, 500, 300, 30); d.setFont(new Font("Times", Font.PLAIN, 16));
		uno.add(pane2); uno.add(add); uno.add(addUpcoming);  uno.add(u); uno.add(addDate); uno.add(d);
		tabbed.addTab("Upcoming", uno);
		
		add.addMouseListener(this);
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
			bulletin.setText(bulletin.getText()+d.getText()+": "+u.getText()+"\n\n");
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
			u.setText(""); d.setText("");
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
