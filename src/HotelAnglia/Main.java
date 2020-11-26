package HotelAnglia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import HotelAnglia.controllers.Connect;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/mainWindow.fxml"));
        primaryStage.setTitle("ARU Anglia Hotel");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();


        if(!Connect.getConnected("postgres", "admin")){
            System.out.println("Wrong Password...");
            System.exit(0);

        } else {
            System.out.println("DataBase Connected Successfully...");
        }


        this.createTables();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void createTables() {
        String accountTable = "CREATE TABLE account (account_id SERIAL PRIMARY KEY, username VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL);";
        Connect.sqlUpdate(accountTable);
        String roomTable = "CREATE TABLE room (room_id SERIAL PRIMARY KEY, type VARCHAR(30) NOT NULL, quantity INTEGER NOT NULL, price MONEY NOT NULL);";
        Connect.sqlUpdate(roomTable);
        String serviceTable = "CREATE TABLE service (service_id SERIAL PRIMARY KEY, type VARCHAR(30) NOT NULL, price NUMERIC NOT NULL);";
        Connect.sqlUpdate(serviceTable);
        String paymentTable = "CREATE TABLE payment (payment_id SERIAL PRIMARY KEY, date DATE NOT NULL, status VARCHAR(30) NOT NULL, payment_method VARCHAR(30) NOT NULL);";
        Connect.sqlUpdate(paymentTable);
    }
}
