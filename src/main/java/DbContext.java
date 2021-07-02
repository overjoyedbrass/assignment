import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.postgresql.ds.PGSimpleDataSource;

public class DbContext {
    private static Connection connection;

    public static void setConnection(Connection c){
        connection = c;
    }

    public static Connection getConnection() {
        return connection;
    }
    
    public static Connection getConnection(String url, int port, String database, String user, String pass) throws SQLException{
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{url});
        ds.setPortNumbers(new int[]{port});
        ds.setDatabaseName(database);
        ds.setUser(user);
        ds.setPassword(pass); 
        
        return ds.getConnection();
    }
    
    public static void executeSQLFile(String file) throws SQLException{
        if(getConnection() == null) throw new SQLException("No connection");
        
        List<String> sqlList = new ArrayList<>();
        
        try{
            Scanner reader = new Scanner(new File(file));
            StringBuilder sb = new StringBuilder();
            
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                sb.append(data);
            }
            
            String[] wordArr = sb.toString().replaceAll(";", "; ").split(" ");
            StringBuilder line = new StringBuilder();
            Boolean dolarFound = false;
            for (int i = 0; i < wordArr.length; i++) {
                String word = wordArr[i];
                word = word.replaceAll("--.*\n\r", "").trim();
                
                if(word.length() > 0)
                    line.append(word).append(" ");
                
                if(word.contains("$$")){
                    dolarFound = !dolarFound;
                }
                
                if (!word.equals("") && word.contains(";") && !dolarFound) {                    
                    sqlList.add(line.toString());                    
                    line = new StringBuilder();
                }
            }
            
            for(String sql: sqlList){
                Statement stmt = getConnection().createStatement();
                stmt.execute(sql);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
