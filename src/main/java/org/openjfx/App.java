package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Pane{

    Button clock_in;
    Button clock_out;
    TextField numberField;
    ControllerJFX jfxController;
    GridPane grid;
    HBox hBoxPasswordError;
    Text passwordError;
    public App(){
        grid = new GridPane();

        HBox hBoxHeader = new HBox();
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setTextAlignment(TextAlignment.CENTER);
        hBoxHeader.getChildren().addAll(scenetitle);
        hBoxHeader.setAlignment(Pos.CENTER);
        grid.add(hBoxHeader, 1, 0, 8, 1);

//        grid.setGridLinesVisible(true);

        Label numberLabel = new Label("Enter your Password:");
        numberLabel.setMinWidth(100);
        numberLabel.setAlignment(Pos.CENTER_RIGHT);
        grid.add(numberLabel, 0, 1);

        numberField = new TextField();
        numberLabel.setLabelFor(numberField);
        numberField.setMaxWidth(Double.MAX_VALUE);
        grid.add(numberField, 1, 1, 8, 1);


        hBoxPasswordError = new HBox();
        passwordError = new Text("Incorrect Password, try again");
        passwordError.setFill(Color.RED);
        passwordError.setTextAlignment(TextAlignment.CENTER);
        hBoxPasswordError.getChildren().add(passwordError);
        hBoxPasswordError.setVisible(false);
        grid.add(hBoxPasswordError, 0, 2);



        clock_in = new Button("Clock In");
        clock_in.setMaxWidth(Double.MAX_VALUE);
        clock_in.setMinWidth(100);
        clock_in.setAlignment(Pos.CENTER);
        clock_in.setMinSize(100, 40);
        grid.add(clock_in, 1, 3, 2, 1);


        clock_out = new Button("Clock Out");

        clock_out.setMaxWidth(Double.MAX_VALUE);
        clock_out.setMinSize(100, 40);
        clock_out.setAlignment(Pos.CENTER);

        grid.add(clock_out, 4, 3, 5, 1);

        grid.setVgap(40);
        grid.setHgap(25);



        grid.setAlignment(Pos.CENTER);
    }


    public void setController(){
        jfxController = new ControllerJFX();
//        int inputID = Integer.parseInt(numberField.getText());
        clock_in.setOnAction(e ->{
            if (jfxController.pressClockInButton(numberField.getText()) == 2){
                hBoxPasswordError.setVisible(false);
                clock_in.setText("You have clocked In");
            }else if (jfxController.pressClockInButton(numberField.getText()) == 1){
                hBoxPasswordError.setVisible(true);
            }else if(jfxController.pressClockInButton(numberField.getText()) == 0){
                passwordError.setText("You have NOT logged Out");
                hBoxPasswordError.setVisible(true);
            }
        });

        clock_out.setOnAction(e -> {
            if (jfxController.pressClockOutButton(numberField.getText()) == 2){
                hBoxPasswordError.setVisible(false);
                clock_out.setText("You have clocked Out");
            }else if (jfxController.pressClockOutButton(numberField.getText()) == 1){
                hBoxPasswordError.setVisible(true);
            }else if(jfxController.pressClockOutButton(numberField.getText()) == 0){
                passwordError.setText("You have NOT logged In");
                hBoxPasswordError.setVisible(true);
            }
        });
    }



}

