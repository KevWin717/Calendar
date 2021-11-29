package application.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class User {
	protected String user, password, individual, email;
	protected List<User> users = new ArrayList<User>();
	public User(List<User> users) {
		this.users = users;
	}
	public User(String user, String password, String individual, String email) {
		this.user = user;
		this.password = password;
		this.individual = individual;
		this.email = email;
	}
	public void loadUsers() throws FileNotFoundException {
		String fLine;
		String []data;
		File f1 = new File("data/users.txt");
		Scanner IN = new Scanner(f1);
		User u=null;
		while(IN.hasNextLine()) {
			fLine= IN.nextLine();
			data= fLine.split(" ");
			user= data[0];
			password= data[1];
			individual= data[2];
			if(data.length> 3) {
				email = data[3];
			}
			u= new User(user, password, individual, email);
			users.add(u);
		}
		IN.close();
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIndividual() {
		return individual;
	}
	public void setIndividual(String individual) {
		this.individual = individual;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
