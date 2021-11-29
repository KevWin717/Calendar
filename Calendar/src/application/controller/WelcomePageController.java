package application.controller;
//Group 3 EasyEvent project
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//
public class WelcomePageController {
//Below are the fxml controls for this page:
    @FXML
    private Button newuser;
    @FXML
    private PasswordField password;
    @FXML
    private Button loggingIn;
    @FXML
    private Button passwordForgot;
    @FXML
    private TextField username;
    @FXML
    private Label invalid;
    public String userName, pass;
    public List<User> users = new ArrayList<User>();
    public User user;
    public void checking() throws Exception {
    	if(userName.isEmpty() && pass.isEmpty()) {
    		throw new Exception("Please enter a username and password");
    	}
    	else if(pass.isEmpty()){
    		throw new Exception("Please enter a password");
    	}
    	else if(userName.isEmpty()){
    		throw new Exception("Please enter a username");
    	}
    }
//CreateUser loads the the NewUser.fxml file.
    @FXML
    void CreateUser(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewUser.fxml"));
    	AnchorPane root = loader.load();
        Stage stage=(Stage)newuser.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
//PasswordForgotScreen is not being used.
    @FXML
    void PasswordForgotScreen(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ForgotPassword.fxml"));
    	AnchorPane root = loader.load();
        Stage stage=(Stage)passwordForgot.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
//LogIn checks if user exists, if not the error "Incorrect input" is printed if it is CalendarPage is loaded
    @FXML
    void LogIn(ActionEvent event) throws FileNotFoundException {
    	user = new User(users);
    	user.loadUsers();
    	Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Incorrect input");
		userName= username.getText().trim();
    	pass= password.getText().trim();
    	try {
    		checking();
    		for(User i: users) {
    			if(userName.equals(i.getUser()) && pass.equals(i.getPassword())) {
    				PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("data/events/"+i.getUser()+".txt", true)));
    				output.close();
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CalendarPage.fxml"));
    		    	AnchorPane root = loader.load();
    		    	CalendarPageController calendar = loader.getController();
    		    	calendar.initialize(i);
    		        Stage stage=(Stage)loggingIn.getScene().getWindow();
    		        Scene scene = new Scene(root);
    		        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		        stage.setScene(scene);
    		        stage.show();
    				break;
    			}
    			else if(!userName.equals(i.getUser()) && !pass.equals(i.getPassword())) {
    				invalid.setStyle("-fx-text-fill: red");
    				invalid.setText("Invalid login credentials");
    			}
    		}
    	}catch(Exception exception){
    		a.setHeaderText("Error: "+exception.getMessage());
    		a.showAndWait();
    	}
    }
}
