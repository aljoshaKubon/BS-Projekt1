package com.projekt1.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	@FXML
	private TextField userField;
	
	@FXML
	private PasswordField passField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	public void login() {
		Connector.login(Integer.valueOf(userField.getText()), passField.getText());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
}
