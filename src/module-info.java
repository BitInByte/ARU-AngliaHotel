module ARUHotelAnglia {

    //    Requires all of the packages and libraries to the HotelAnglia package
    requires javafx.fxml;
    requires javafx.controls;

    //    Points the objects and packages that will use JavaFX
    opens HotelAnglia;
    opens HotelAnglia.controllers;
    opens HotelAnglia.models;
    opens HotelAnglia.views;
}