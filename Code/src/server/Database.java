package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database {
    
    public static void main(String[] args) {
	Database db = new Database("root", "password", "129.82.44.147", "5123");
	System.out.println(db.executeQuery("SELECT * FROM user"));
    }

    private Connection conn;
    public Database(String user, String pass, String ip, String port) {
        String url = "jdbc:mysql://" + ip + ":" + port + "/banqi?useSSL=false";
        initiateConnection(user, pass, url);
    }

    private void initiateConnection(String user, String pass, String url) {
        String myDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(myDriver).newInstance();
            //System.out.println("here");
	    conn = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception e) {
            System.err.printf("Exception: ");
            System.err.println(e.getMessage());
        }
    }

    public String executeQuery(String query)
    {
        String result = "";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            while(rs.next()) {
                String csv = "";
                for(int col=1; col<=colCount; col++) {
                    String value = rs.getString(col);
                    if(col == colCount) {
                        csv = csv + value;
                    }
                    else {
                        csv = csv + value + ",";
                    }
                }
                result = result + csv + "|";
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return result;
    }

    public void executeUpdate(String statement) {
	try {
		Statement st = conn.createStatement();
		st.executeUpdate(statement);
	}
	catch(SQLException sqle) {
		System.out.println(sqle.getMessage());
	}
    }
}
