package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MainController implements Initializable {

    @FXML
    private Label test;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField cityText;

    @FXML
    private Button checkButton;

    @FXML
    void checkClick(ActionEvent event) {
    	if(choiceBox.getValue()=="Search by city name") {
    		String sURL = "http://api.openweathermap.org/data/2.5/weather?q="; //Rutki-Kossaki&APPID=c51c62d65d9b1125d513f20cb5107341";
    		sURL = new StringBuilder(sURL).append(cityText.getText()).toString();
    		sURL = new StringBuilder(sURL).append("&APPID=c51c62d65d9b1125d513f20cb5107341").toString();
    		System.out.println(sURL);
    		
    		JsonElement root = null;
		    try {
		    	URL url = new URL(sURL);
		    	HttpURLConnection request = (HttpURLConnection) url.openConnection();
				request.connect();
				
				JsonParser jp = new JsonParser();
				root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				alert("Localization not found :(");
				e.printStackTrace();
			}

		    //Convert the input stream to a json element
		    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
		    System.out.println(rootobj.toString());
		    JsonObject main = rootobj.get("main").getAsJsonObject();
		    JsonArray weather = rootobj.get("weather").getAsJsonArray();
		    JsonObject weatherr = weather.get(0).getAsJsonObject();
		    
		    int temp = main.get("temp").getAsInt();
		    temp = temp - 273;
		    test.setText("Nazwa miasta " + rootobj.get("name").getAsString() + " pogoda "+ weatherr.get("main").getAsString() +  " temperatura to " + Integer.valueOf(temp).toString() + "C");
    	}
    	else if(choiceBox.getValue()=="Search by your location*") {
    		alert("Not ready yet...");
    	}
    	else {
    		alert("Choose something, pls....");
    	}
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	// TODO Auto-generated method stub
    	cityText.setPromptText("city name");
    	test.setText("");
    	choiceBox.getItems().addAll("Search by city name", "Search by your location*");
    }
    void alert(String text) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Ups...");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
    }
}