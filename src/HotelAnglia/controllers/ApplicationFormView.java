package HotelAnglia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationFormView {

//    Regex expression to validate emails
    private static final String regex = "^(.+)@(.+)$";

    private boolean emailValidate;

    private boolean nameValidate;

    @FXML
    private Button submitbtn;

    @FXML
    private TextField nametf;

    @FXML
    private TextField emailtf;


    @FXML
    public void initialize() {
        this.nameValidate = false;
        this.emailValidate = false;
        submitbtn.setDisable(true);
    }

    public void nameValidator() {
        if(!nametf.getText().trim().isEmpty()) {
            this.nameValidate = true;
        } else {
            this.nameValidate = false;
        }

        System.out.println(this.nameValidate);

        if(this.nameValidate && this.emailValidate) {
            submitbtn.setDisable(false);
        } else {
            submitbtn.setDisable(true);
        }
    }

    public void checkEmailValidator() {
//        Initialize the regex expression
        Pattern pattern = Pattern.compile(regex);

//        Checks if the email matches the regex expression
        Matcher matcher = pattern.matcher(emailtf.getText());

//        System.out.println(matcher.matches() ? "Matches" : "Doesnt matches");

//        this.emailValidate = matcher.matches() ? true : false;
        this.emailValidate = matcher.matches();

        System.out.println(emailValidate);

        if(this.nameValidate && this.emailValidate) {
            submitbtn.setDisable(false);
        } else {
            submitbtn.setDisable(true);
        }

    }

    public void submitHandler() {

    }

    public void closePage() {

    }
}
