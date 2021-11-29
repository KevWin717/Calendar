package application.controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.regex.Pattern;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddEventController {
	@FXML
    private DatePicker date;
    @FXML
    private Button cancel;
    @FXML
    private Button add;
    @FXML
    private TextArea notes;
    @FXML
    private TextArea description;
    @FXML
    private TextField time;
    public Writer output;
    public User currentUser;
    public String[] check;
    public void checking() throws Exception {
    	for(int i = 0; i< check.length; i++) {
    		if(check[i].isEmpty()) {
    			throw new Exception("Please fill out everything");
    		}
    	}
    }
    public void initialize(User user) throws FileNotFoundException {
    	currentUser=user;
    }
    @FXML
    void CancelEvent(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CalendarPage.fxml"));
    	AnchorPane root = loader.load();
    	CalendarPageController controller = loader.getController();
		controller.initialize(currentUser);
        Stage stage=(Stage)cancel.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void AddConfirmation(ActionEvent event) throws IOException {
    	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("data/events/"+currentUser.getUser()+".txt", true)));
    	Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Incorrect input");
		try {
	    	LocalDate x = date.getValue();
	    	String day = x+"", timeOfDay= time.getText().trim(), desc = description.getText().trim(), note=notes.getText().trim();
	    	check= new String[]{day, timeOfDay, desc};
	    	checking();
	    	if(!(Pattern.matches("[0-9]{2}:[0-5][0-9][a-z&&[ap]][a-z&&[m]]", timeOfDay))) {
	    		output.close();
	    		throw new Exception("Please enter a correct time.");
	    	}else if(!(Pattern.matches("2[0-9]{3}-[1-9][0-9]-[0-3][0-9]",String.valueOf(x)))){
	    		output.close();
	    		throw new Exception("Please enter a valid date.");
	    	}
	    	if(note.isEmpty()) {
	    		note="";
	    	}
	    	output.println(day+ ","+ timeOfDay+ ","+ desc + "," + note);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CalendarPage.fxml"));
			AnchorPane root = loader.load();
			CalendarPageController controller = loader.getController();
			controller.initialize(currentUser);
			Stage stage=(Stage)add.getScene().getWindow();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}catch(Exception exception) {
			a.setHeaderText("Error: "+exception.getMessage());
    		a.showAndWait();
		}
		output.close();
    }
}
