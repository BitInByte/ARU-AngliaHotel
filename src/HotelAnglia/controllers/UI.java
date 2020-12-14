package HotelAnglia.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Statement;

public class UI {

//    Creates a new UI element with definitions defined as arguments
    public void createUIElement(String title, String filename) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HotelAnglia/views/" + filename + ".fxml"));
            Parent pushingWindow = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(pushingWindow));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
//            Catch possible errors and log it into the console
            e.printStackTrace();
        }
    }

//    Create an error element
    public void showErrorView(String error) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("/HotelAnglia/views/errorView.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

        ErrorView controller = loader.getController();
        controller.initData(error);

        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();
    }

//    Show a modal and show a success message
    public void showSuccessView(String message) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UI.class.getResource("/HotelAnglia/views/successView.fxml"));
        Parent pushingWindow = loader.load();
        Scene pushingWindowScene = new Scene(pushingWindow);

        SuccessView controller = loader.getController();
        controller.initData(message);

        Stage stage = new Stage();
        stage.setTitle("Success");
        stage.setScene(pushingWindowScene);
        stage.setResizable(false);
        stage.show();
    }

//    Close the current element
    public void closeUIElement(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

//    Close the current elements based on a grid pane
    public void closeUIElement(GridPane gridPane) {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }

//    Dynamic changes the center pane to new fxml anchorpane content
    public void changePane(GridPane rootPane, String filename) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/HotelAnglia/views/" + filename + ".fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
