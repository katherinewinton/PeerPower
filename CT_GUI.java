package hackCupertino;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.*;

public class CT_GUI implements MouseListener{
	
	JFrame frame; JPanel panel; JButton selectCourse; JLabel course, teacher; JComboBox<String> courses, teachers;
	String[] courseList, teacherList;
	String username, school;
	
	public CT_GUI(String username, String school) throws FileNotFoundException {
		this.school = school;
		this.username = username;
		coursesAndTeachers();
		
		frame = new JFrame(school+" courses");
		frame.setBounds(100, 100, 500, 650);
		frame.setResizable(false);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(218, 232, 245));
		
		course = new JLabel("Select a course:"); course.setBounds(100, 100, 130, 50); course.setFont(new Font("Times", Font.PLAIN, 16));
		teacher = new JLabel("Select a teacher:"); teacher.setBounds(100, 160, 130, 50); teacher.setFont(new Font("Times", Font.PLAIN, 16));
		courses = new JComboBox<String>(courseList); courses.setBounds(200, 100, 200, 50); courses.setFont(new Font("Times", Font.PLAIN, 14));
		teachers = new JComboBox<String>(teacherList); teachers.setBounds(200, 160, 200, 50); teachers.setFont(new Font("Times", Font.PLAIN, 14));
		selectCourse = new JButton("Submit option"); selectCourse.setBounds(100, 250, 130, 50); selectCourse.setFont(new Font("Times", Font.PLAIN, 16));
		selectCourse.addMouseListener(this);
		
		panel.add(course); panel.add(teacher); panel.add(courses); panel.add(teachers); panel.add(selectCourse);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private void coursesAndTeachers() throws FileNotFoundException {
		LinkedList<String> c = new LinkedList<String>(), t= new LinkedList<String>();
		Scanner s = new Scanner(new File("hackCupertino/"+school+".txt"));
		s.useDelimiter("\n");
		String item;
		Scanner s2;
		while(s.hasNext()) {
			item = s.next();
			s2 = new Scanner(item); s2.useDelimiter(", ");
			t.add(s2.next()); c.add(s2.next()); 
		}
		courseList = new String[c.size()]; teacherList = new String[t.size()];
		for(int i=0; i<courseList.length; i++) {
			courseList[i] = c.get(i);
		}
		for(int i=0; i<teacherList.length; i++) {
			teacherList[i] = t.get(i);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(selectCourse)) {
			Scanner s;
			try {
				s = new Scanner(new File("hackCupertino/"+school+".txt"));
				s.useDelimiter("\n");
				String item;
				while(s.hasNext()) {
					item = s.next();
					if(item.startsWith(String.valueOf(teachers.getSelectedItem()))) {
						UC_GUI gui = new UC_GUI(item, username, school);
					}
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
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
