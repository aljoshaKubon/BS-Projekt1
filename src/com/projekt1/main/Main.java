package com.projekt1.main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	protected static Stage loginStage, mainStage = new Stage();
	protected static Scene loginScene, mainScene;
	protected static FXMLLoader loginLoader, mainLoader;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage loginStage) throws Exception {
		Main.loginStage = loginStage;
		//Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
		loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
		mainLoader = new FXMLLoader(getClass().getResource("Layout.fxml"));
		
		Parent loginRoot = loginLoader.load();
		Parent mainRoot = mainLoader.load();
		
		loginScene = new Scene(loginRoot);
		mainScene = new Scene(mainRoot);
		
		mainStage.setTitle("Scooteq");
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		mainStage.setOnCloseRequest(e -> {
			mainStage.close();
			System.exit(0);
		});
		
		loginStage.setTitle("Login");
		loginStage.setScene(loginScene);
		loginStage.setResizable(false);
		loginStage.show();
		loginStage.setOnCloseRequest(e -> {
			loginStage.close();
			System.exit(0);
		});
	}

}
