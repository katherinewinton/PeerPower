package hackCupertino;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class LS_GUI implements MouseListener {
	
	JFrame frame; JPanel panel; JButton login, signup; JLabel welcome, u1, u2, p1, p2, s, warning; JTextField uTF1, uTF2, pTF1, pTF2; JComboBox<String> school;
	
	public LS_GUI() throws IOException {
		LoginSignup.usernamesAndPasswords();
		
		frame = new JFrame("We stan Lautenslager");
		frame.setBounds(100, 100, 500, 650);
		frame.setResizable(false);
		panel = new JPanel();
		panel.setBounds(100, 100, 500, 650);
		panel.setLayout(null);
		panel.setBackground(new Color(240, 201, 208));
		
		welcome = new JLabel("PeerPower! Connect with your community :)"); welcome.setBounds(50, 50, 400, 50); welcome.setFont(new Font("Times", Font.ITALIC, 18));
		login = new JButton("Login"); login.setBounds(50, 100, 100, 50); login.setFont(new Font("Times", Font.PLAIN, 16));
		u1 = new JLabel("Username:"); u1.setBounds(50, 160, 100, 50); u1.setFont(new Font("Times", Font.PLAIN, 16));
		p1 = new JLabel("Password:"); p1.setBounds(50, 220, 100, 50); p1.setFont(new Font("Times", Font.PLAIN, 16));
		uTF1 = new JTextField(""); uTF1.setBounds(150, 160, 200, 50); uTF1.setFont(new Font("Times", Font.PLAIN, 16));
		pTF1 = new JTextField(""); pTF1.setBounds(150, 220, 200, 50); pTF1.setFont(new Font("Times", Font.PLAIN, 16));
		signup = new JButton("Signup"); signup.setBounds(50, 300, 100, 50); signup.setFont(new Font("Times", Font.PLAIN, 16));
		u2 = new JLabel("Username:"); u2.setBounds(50, 360, 100, 50); u2.setFont(new Font("Times", Font.PLAIN, 16));
		p2 = new JLabel("Password:"); p2.setBounds(50, 420, 100, 50); p2.setFont(new Font("Times", Font.PLAIN, 16));
		uTF2 = new JTextField(""); uTF2.setBounds(150, 360, 200, 50); uTF2.setFont(new Font("Times", Font.PLAIN, 16));
		pTF2 = new JTextField(""); pTF2.setBounds(150, 420, 200, 50); pTF2.setFont(new Font("Times", Font.PLAIN, 16));
		s = new JLabel("School:"); s.setBounds(50, 480, 100, 50); s.setFont(new Font("Times", Font.PLAIN, 16));
		String[] choices = {"Monta Vista", "Saint Francis", "Mountain View", "Harker", "Archbishop Mitty", "Bell Boys suck", "Silver Creek"};
		school = new JComboBox<String>(choices); school.setBounds(150, 480, 200, 50); school.setFont(new Font("Times", Font.PLAIN, 14));
		warning = new JLabel(""); warning.setBounds(50, 540, 300, 50); warning.setFont(new Font("Times", Font.PLAIN, 16));
		
		login.addMouseListener(this); signup.addMouseListener(this);
		panel.add(welcome);
		panel.add(login); panel.add(u1); panel.add(p1); panel.add(uTF1); panel.add(pTF1);
		panel.add(signup); panel.add(u2); panel.add(p2); panel.add(uTF2); panel.add(pTF2); panel.add(s); panel.add(school);
		panel.add(warning);
		frame.add(panel);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(login)) {
			String sc = LoginSignup.login(uTF1.getText(), pTF1.getText());
			if(sc==null) warning.setText("Warning: Invalid input.");
			else {
				try {
					CT_GUI gui = new CT_GUI(uTF1.getText(), sc);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource().equals(signup)) {
			try {
				if(LoginSignup.signup(uTF2.getText(), pTF2.getText(), String.valueOf(school.getSelectedItem()))) {
					String sc = String.valueOf(school.getSelectedItem()).toLowerCase().replaceAll("\\s", "");
					CT_GUI gui = new CT_GUI(uTF2.getText(), sc);
				}
				else warning.setText("Warning: Invalid input.");
			} catch (IOException e1) {
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
