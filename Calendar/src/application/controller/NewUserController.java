package application.controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewUserController {
	@FXML
    private Button confirm;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Button back;

    @FXML
    private TextField email;
    
    @FXML
    private TextField individual;
    @FXML
    private TextField username;
    public Writer output;
    public List<User> users = new ArrayList<User>();
    public User user;
    public String[] check;
    public String userName, pass, fullEmail, confirmation, person;
    public void initialize() throws FileNotFoundException {
    	user= new User(users);
    	user.loadUsers();
    	username.setPromptText("Username");
    	password.setPromptText("Password");
    }
    public void checking() throws Exception {
    	for(int i = 0; i< check.length; i++) {
    		if(check[i].isEmpty()) {
    			throw new Exception("Please fill out everything");
    		}
    	}
    }
    @FXML
    void CreateUser(ActionEvent event) throws IOException {
    	output = new BufferedWriter(new FileWriter("data/users.txt", true));
    	Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Incorrect input");
    	try {
    		userName = username.getText().trim();
        	pass = password.getText().trim();
        	fullEmail = email.getText().trim();
        	person = individual.getText().trim();
        	confirmation = confirmPassword.getText().trim();
        	check = new String[]{person, pass, fullEmail, confirmation, userName};
    		checking();
    		for(User u: users) {
    			if(userName.equals(u.getUser())) {
    				throw new Exception("User already exists");
    			}
    		}
    		if(!confirmation.equals(pass)){
    			throw new Exception("Does not match");
    		}else if(confirmation.equals(pass)) {
    			output.write("\n"+userName+ " "+ pass+ " "+ person + " " + fullEmail);
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/WelcomePage.fxml"));
    			AnchorPane root = loader.load();
    			Stage stage=(Stage)confirm.getScene().getWindow();
    			Scene scene = new Scene(root);
    			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    			stage.setScene(scene);
    			stage.show();
    		}
    	}catch(Exception exception){
    		a.setHeaderText("Error: "+exception.getMessage());
    		a.showAndWait();
    	}
    	output.close();
    }
    @FXML
    void BackScreen(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/WelcomePage.fxml"));
    	AnchorPane root = loader.load();
        Stage stage=(Stage)back.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
