package application.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import application.model.Event;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteEventController{
	@FXML
    private Button cancel;
    @FXML
    private ChoiceBox<Event> choiceBox;
    @FXML
    private Button delete;
    public User user;
    public Event currentEvent;
    public List<Event> events = new ArrayList<Event>();
    public void initialize(User currentUser) throws FileNotFoundException {
    	user = currentUser;
		currentEvent= new Event(events);
		currentEvent.loadEvents(user.getUser());
		Collections.sort(events, Event.compare);
    	Collections.sort(events, Event.compareDates);
		choiceBox.getItems().addAll(events);
		choiceBox.setOnAction(this::getDate);
	}
    @FXML
    void BackScreen(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CalendarPage.fxml"));
    	AnchorPane root = loader.load();
    	CalendarPageController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)cancel.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void deleteEvent(ActionEvent event) throws IOException {
    	String line = currentEvent.getDay()+","+currentEvent.getTime()+","+currentEvent.getDescription()+","+currentEvent.getNotes();
    	String fLine;
    	String file = "";
    	File f1 = new File("data/events/"+user.getUser()+".txt");
		Scanner IN = new Scanner(f1);
		while(IN.hasNextLine()) {
			fLine= IN.nextLine();
			if(fLine.equals(line))
				continue;
			file+=fLine+"\n";
		}
		IN.close();
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("data/events/"+user.getUser()+".txt")));
		output.append(file);
		output.close();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CalendarPage.fxml"));
    	AnchorPane root = loader.load();
    	CalendarPageController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)delete.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
	
    
    public void getDate(ActionEvent event) {
    	currentEvent = choiceBox.getValue();
    }
}
