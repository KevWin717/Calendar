package application.controller;
//Group 3 Easy Events project

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.model.Event;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/* This is the controller class for the AllEvents.fxml.
*  Users will be displayed all events that the user inputted into the calendar
*/ 
public class AllEventsController {
	@FXML
    private TextArea eventList;
	@FXML
    private Button deleteEvent;
    @FXML
    private Button create;
    @FXML
    private Button returnButton;
    public Event event;
    public List<Event> events= new ArrayList<Event>();
    public User user;
    public void initialize(User currentUser) throws FileNotFoundException {
    	user= currentUser;
    	event = new Event(events);
    	event.loadEvents(user.getUser());
    	Collections.sort(events, Event.compare);
    	Collections.sort(events, Event.compareDates);
    	eventList.setText("");
    	for(Event e: events) {
    		eventList.appendText(e.toString());
    	}
    	if(eventList.getText().isEmpty()) {
    		eventList.setText("There are no events to display.");
    	}
    }

    //This method allows user to return back into the calendar page when the button is clicked
    @FXML
    void ReturnToCalendar(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CalendarPage.fxml"));
    	AnchorPane root = loader.load();
    	CalendarPageController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)returnButton.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    //This method creates the event screen and goes to the add events
    @FXML
    void CreateEventScreen(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddEvent.fxml"));
    	AnchorPane root = loader.load();
    	AddEventController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)create.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
	
    //This will let users go to the delete event page	
    @FXML
    void DeleteEventScreen(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DeleteEvent.fxml"));
    	AnchorPane root = loader.load();
    	DeleteEventController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)deleteEvent.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
	
}
