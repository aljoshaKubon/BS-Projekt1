package com.projekt1.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class Connector {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/scooteq?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
	private static final String USER = "root";
	private static final String PASS = "";
	private static Connection conn = null;
	private static Statement stmt = null;
	private static MainController controller = Main.mainLoader.getController();

	protected static void login(int id, String password) {
		try {
			connect();
			
			String sql = "Select * from mitarbeiter where mitarbeiter_id = " + id;
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				if(password.equals(rs.getString("mitarbeiter_PW"))) {
					Main.mainStage.show();
					Main.loginStage.close();
					controller.initProfile(rs);
					controller.initCharts(rs);
				}else {
					
				}
			}else {
				
			}
			disconnect(rs);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void fillTableView(TableView tableView, String sql) {
		ObservableList<ObservableList> data = FXCollections.observableArrayList();
		tableView.getColumns().clear();
		
		try {
			connect();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue call(CellDataFeatures<ObservableList, String> param) {
                    	try {
                    		return new SimpleStringProperty(param.getValue().get(j).toString());
                    	}catch(NullPointerException e) {
                    		return null;
                    	}
                    }
                });
                
                tableView.getColumns().addAll(col);
			}
			
			while(rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				data.add(row);
			}
			
			tableView.setItems(data);
			
			disconnect(rs);
		}catch(Exception e) {
			
		}
	}
	
	protected static void getDataPerMonth(String sql) {
		try {
			ResultSet rs = stmt.executeQuery(sql);
			
			
		}catch(Exception e) {
			
		}
	}
	
	private static void connect() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void disconnect(ResultSet rs) throws SQLException {
		rs.close();
		stmt.close();
		conn.close();
	}
}
