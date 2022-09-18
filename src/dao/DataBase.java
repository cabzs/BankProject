package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	private static DataBase instance;
	private Connection con;
	
	public DataBase () {
		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app?autoReconnect=true", "root", "1234");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##bank", "bank");
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DataBase getInstance() {
		if(instance == null) {
			instance = new DataBase();
		}
		return instance;
	} 
	
	public Connection getConnection() {
		return this.con;
	}
	
//	public static void main(String[] args) {
//			
//			try {
//			System.out.println("=======시작=======");
//			System.out.println(DataBase.getInstance().getConnection());
//			
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//
//		}

}
