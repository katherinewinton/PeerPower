package hackCupertino;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class LoginSignup {
	
	static LinkedList<String> usernames = new LinkedList<String>();
	static LinkedList<String> passwords = new LinkedList<String>();
	static LinkedList<String> schools = new LinkedList<String>();
	
	@SuppressWarnings("resource")
	static void usernamesAndPasswords() throws IOException {
		Scanner s = new Scanner(new File("hackCupertino/usernames_passwords.txt"));
		s.useDelimiter(", ");
		while(s.hasNext()) {
			usernames.add(s.next());
			passwords.add(s.next());
			schools.add(s.next());
		}
	}
	
	static String login(String username, String password) {
		int n=usernames.indexOf(username);
		if(n==-1) { System.out.println("-1"); return null; }
		String u=usernames.get(n), p=passwords.get(n);
		if(!u.equals(username) || !p.equals(password)) return null;
		return schools.get(n);
	}
	
	static boolean signup(String username, String password, String school) throws IOException {
		if(username.equals("")||username==null) return false;
		if(usernames.contains(username)) return false;
		FileWriter fw = new FileWriter(new File("hackCupertino/usernames_passwords.txt"), true);
		PrintWriter pw = new PrintWriter(fw);
		school = school.toLowerCase().replaceAll("\\s", "");
		pw.print(", "+username+", "+password+", "+school);
		pw.flush(); pw.close();
		return true;
	}
	
}
