package com.projekt1.main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class MainController implements Initializable{
	
	@FXML
	private Label labelID;
	
	@FXML
	private Label labelPrename;
	
	@FXML
	private Label labelName;
	
	@FXML
	private Label labelAge;
	
	@FXML
	private Label labelJob;
	
	@FXML
	private Label labelDepartment;
	
	@FXML
	private Label labelFiliale;
	
	@FXML
	private TableView<String> tableView;
	
	@FXML
	private Button showScooter;
	
	@FXML
	private Button showBill;
	
	@FXML
	private Button showBranch;
	
	@FXML
	private Button showCostumers;
	
	@FXML
	private Button showQuery;
	
	@FXML
	private CheckBox onlyLoanedCheckBox;
	
	@FXML
	private CheckBox yetLoanedCheckBox;
	
	@FXML
	private CheckBox detailsCheckBox;
	
	@FXML
	private TextArea queryTextArea;
	
	@FXML
	private LineChart<Number, Number> lineChart;

	
	public MainController() {
		
	}
	
	@FXML
	private void initialize() {
	}
	
	@FXML 
	private void showScooter() {
		String sql = "Select * from scooter";
		
		if(!onlyLoanedCheckBox.isSelected()) {
			sql = "";
		}
		Connector.fillTableView(tableView, sql);
	}
	
	@FXML 
	private void showBill() {
		String sql = "Select * from rechnung";
		
		if(yetLoanedCheckBox.isSelected()) {
			sql = "SELECT * FROM rechnung WHERE scooter_ausleihende IS null";
		}
		if(detailsCheckBox.isSelected()) {
			sql = "SELECT scooter.Scooter_ID, rechnung.kunde_nr, rechnung.scooter_ausleihstart, rechnung.scooter_ausleihende, TIMESTAMPDIFF(HOUR, rechnung.scooter_ausleihstart, rechnung.scooter_ausleihende) AS Dauer,  scooter.scooter_pps, scooter.Scooter_plz, scooter.Scooter_ort, scooter.Scooter_strasse, scooter.Scooter_hausnr\r\n" + 
					"FROM scooter\r\n" + 
					"INNER JOIN rechnung ON scooter.Scooter_ID=rechnung.scooter_nr";
		}
		if(yetLoanedCheckBox.isSelected() && detailsCheckBox.isSelected()) {
			sql = "SELECT scooter.Scooter_ID, rechnung.kunde_nr, rechnung.scooter_ausleihstart, rechnung.scooter_ausleihende, TIMESTAMPDIFF(HOUR, rechnung.scooter_ausleihstart, rechnung.scooter_ausleihende) AS Dauer, scooter.scooter_pps, scooter.Scooter_plz, scooter.Scooter_ort, scooter.Scooter_strasse, scooter.Scooter_hausnr\r\n" + 
					"FROM scooter\r\n" + 
					"INNER JOIN rechnung ON scooter.Scooter_ID=rechnung.scooter_nr\r\n" + 
					"WHERE scooter_ausleihende IS NULL";
		}
		
		Connector.fillTableView(tableView, sql);
	}
	
	@FXML
	private void showBranch() {
		String sql = "Select * from filiale";
		
		Connector.fillTableView(tableView, sql);
	}
	
	@FXML
	private void showCostumers() {
		String sql = "Select * from kunde";
		
		Connector.fillTableView(tableView, sql);
	}
	
	@FXML
	private void showQuery() {
		String sql = queryTextArea.getText();
		
		if(sql != null && sql != "") {
			Connector.fillTableView(tableView, sql);
		}
	}
	
	protected void initProfile(ResultSet rs) {
		try {
			labelID.setText(""+rs.getInt("mitarbeiter_id"));
			labelPrename.setText(rs.getString("mitarbeiter_vorname"));
			labelName.setText(rs.getString("mitarbeiter_name"));
			labelAge.setText(""+rs.getInt("mitarbeiter_alter"));
			labelJob.setText(rs.getString("mitarbeiter_job"));
			labelDepartment.setText(""+rs.getInt("abteilung_nr"));
			labelFiliale.setText(""+rs.getInt("filiale_nr"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void initCharts(ResultSet rs) {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		xAxis.setLabel("Monat");
		yAxis.setLabel("Einahmen");
		
		lineChart.setTitle("Einnahmen im Jahr 2019");
		
		Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("2019");
		
		for(int i = 0; i < 12; i++) {
			series.getData().add(new XYChart.Data<Number, Number>(i, getDataPerMonth()));
			System.out.println(i);
		}
		lineChart.getData().add(series);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	private int getDataPerMonth() {
		String sql = "";
		Connector.getDataPerMonth(sql);
		
		Random r = new Random();
		return r.nextInt(100);
	}

}
