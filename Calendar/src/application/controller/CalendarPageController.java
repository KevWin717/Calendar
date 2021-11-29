package application.controller;
//Group 3 Easy Event project

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import application.model.Event;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/* This is the controller for the CalendarPage.fxml.
*  This page is prompted after a use has login. Once a user is on this page,
*  users will be able to add, delete, and use the calendar.
*/
public class CalendarPageController {
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @FXML
    private Button nextMonth;
    @FXML
    private Button addEvent;
    @FXML
    private TextArea dayListing;
    @FXML
    private Button previousMonth;
    @FXML
    private Button deleteEvent;
    @FXML
    private Button loggingOut;
    @FXML
    private Label monthLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button allEvents;
    @FXML
    private Label curDay;
    @FXML
    private GridPane gridpane;
    public ArrayList<Button> gridButtons = new ArrayList<Button>();
    public Calendar calendar;
    public List<User> users = new ArrayList<User>();
    public List<Event> events = new ArrayList<Event>();
    public User user;
    public Event event;
    public int month, year;
    public String selectedMonth;
    public void initialize(User curUser) throws FileNotFoundException {
    	user= new User(users);
    	user.loadUsers();
    	for(User i: users){
    		if(i.getUser().equals(curUser.getUser())) {
    			welcomeLabel.setText("Welcome "+ i.getIndividual());
    			user= curUser;
        		}
    	}
    	Calendar();
    	populateDate(YearMonth.now());
    }
	
    //When logout button is clicked, users will be signed out and returned to the login screen.
    @FXML
    void LogOut(ActionEvent event) throws IOException {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Logging out");
    	alert.setHeaderText("You are about to log out!");
    	alert.setContentText("Are you sure you want to log out?");
    	if(alert.showAndWait().get() == ButtonType.OK) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/WelcomePage.fxml"));
        	AnchorPane root = loader.load();
            Stage stage=(Stage)loggingOut.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
    	}
    }
	
    //The view will change and the user will be able to see the events during the week
    @FXML
    void changeWeekView(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AllEvents.fxml"));
    	AnchorPane root = loader.load();
    	AllEventsController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)allEvents.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    //Users will be able to delete an event once made
    @FXML
    void ChangeDeleteEvent(ActionEvent event) throws IOException {
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

    //This method allows user to change an event that was already added
    @FXML
    void ChangeAddEvent(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddEvent.fxml"));
    	AnchorPane root = loader.load();
    	AddEventController controller = loader.getController();
    	controller.initialize(user);
        Stage stage=(Stage)addEvent.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    //After a user clicks on the button, users will be able to go through each month.
    @FXML
    void changeMonth(ActionEvent event) {
    	if(event.getTarget() == previousMonth) {
    		int currentMonth = month;
    		if (currentMonth == 0) {
    			setYear(year - 1);
    			setMonth(11);
    		}else {
    			setMonth((currentMonth - 1) % 12);
    		}
    	}
    	if(event.getTarget() == nextMonth) {
    		int currentMonth = month;
    		if (currentMonth == 11) {
    			setYear(year + 1);
    			setMonth(0);
    		}else {
    			setMonth((currentMonth + 1) % 12);
    		}
    	}
    }
    //This method shows the calendar and set the calendar to the Gregorian calendar
    public void Calendar(){
    	event = new Event(events);
    	try {
    		event.loadEvents(user.getUser());
    	}catch(FileNotFoundException exception) {
    		exception.getMessage();
    	}
    	selectedMonth = String.valueOf(LocalDate.now().getMonth());
    	year= Integer.parseInt(String.valueOf(LocalDate.now().getYear()));
    	for(int i =0; i< 6; i++) {
    		for(int j=0; j<7; j++) {
    			Button but = new Button();
    			but.setPrefSize(100,100);
    			but.setPadding(new Insets(10));
    			but.setTextAlignment(TextAlignment.RIGHT);
    			gridpane.add(but, j, i);
    			gridButtons.add(but); 
    		}
    	}
    	// Set current month
    	calendar = new GregorianCalendar();
    	month = calendar.get(Calendar.MONTH);
    	// Show calendar
    	showHeader();
    	populateDate(YearMonth.now());
    }
	
    //This method indicares what month/year it is
    public void showHeader() {
    	monthLabel.setText(months[month] + ", " + year);
    }
    //This method populates the calendar and enters the dates
    private void populateDate(YearMonth yearMonthNow){
    	try {
			event.loadEvents(user.getUser());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        YearMonth yearMonth = yearMonthNow;
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        dayListing.setText("");
        for (Button buttons : gridButtons) {
            buttons.setId(calendarDate.toString());
            Label label = new Label();
            label.setText("");
            buttons.setText(String.valueOf(calendarDate.getDayOfMonth()));
            buttons.setFont(Font.font("Roboto",16));
            buttons.setTextFill(Color.GRAY);
            buttons.setDisable(true);
            if(isDateInRange(yearMonth, calendarDate)){
                buttons.setTextFill(Color.BLACK);
                buttons.setDisable(false);
                
            }
            if(calendarDate.equals(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth()))){
                buttons.setTextFill(Color.BLACK);
                buttons.setDisable(false);
            }
            int count=0;
            for(Event e: events) {
            		if(e.toString().contains(buttons.getId())) {
            			if(buttons.getId().trim().equals(e.getDay())) {
            				count++;
            			}
            		}
            	}
        	if(count != 0) {
        		buttons.setText(calendarDate.getDayOfMonth()+ "\n*");
        		count=0;
        	}
            label.setTextFill(Color.MAGENTA);
            label.setFont(Font.font("Roboto",13));
            buttons.setGraphic(label);            
            buttons.setOnMouseClicked(event -> {
                dayListing.setText("");
                curDay.setText("Here are your events for the " + buttons.getId()+":");
            	Collections.sort(events, Event.compare);
                for(Button x : gridButtons){
                    x.getStyleClass().remove("selectedDate");
                    for(Event e: events) {
                    	if(buttons == x) {
                    		if(!dayListing.getText().contains(e.toString())) {
                    			if(x.getId().trim().equals(e.getDay())) {
                    				dayListing.appendText(e.toString());
                    			}
                    		}
                    	}
                    }
                }
                if(dayListing.getText().equals("")) {
                	dayListing.setText("There are events on "+buttons.getId());
                }
                buttons.getStyleClass().add("selectedDate");
            });
            calendarDate = calendarDate.plusDays(1);

        }
    }
    //Make sure the added dates are in calendar range
    private boolean isDateInRange(YearMonth entireMonth, LocalDate current){
        LocalDate start = LocalDate.of(entireMonth.getYear(), entireMonth.getMonth(), 1);
        LocalDate end = LocalDate.of(entireMonth.getYear(), entireMonth.getMonth(), entireMonth.lengthOfMonth());
        return (!current.isBefore(start)) && (current.isBefore(end));
    }
    //make sure the year and month is displayed when the calendar changes
    private void changeCalendar(int year, String month){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("yyyy MMMM").toFormatter(Locale.ENGLISH);
        populateDate(YearMonth.parse(year + " " + month, formatter));
        selectedMonth = month;
    }
    //sets the month in the calendar
    public void setMonth(int nextMonth) {
    	month = nextMonth;
    	selectedMonth = months[month];
    	changeCalendar(year, selectedMonth);
    	showHeader();
    }
    //sets year in the header
    public void setYear(int nextYear) {
    	year = nextYear;
    	changeCalendar(year, selectedMonth);
    	showHeader();
    }
}
