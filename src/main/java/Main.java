import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Main {  
    
    public static void main(String[] args) throws SQLException{
        try (Connection connection = DbContext.getConnection("localhost", 5432, "postgres","postgres","root")){
            DbContext.setConnection(connection);
            
            
            DbContext.executeSQLFile("sql/createscript.sql");            
            DbContext.executeSQLFile("sql/generatescript.sql");
            
            int limit = 100;
            
            var result = DocumentCount.getAll(limit);
            printResult(result);

            
        } finally {
            DbContext.setConnection(null);
        }
    }
    
    public static void printResult(List<Document> result){
        if(result.size() == 0) return;
        System.out.println("              DOC-UUID                |   NAME   |  CLICKS  ");
        for(var doc: result){
            System.out.println(doc.getUUID() + "  |  " + doc.getName() + "  |  " + doc.getClicksCount());
        }        
    }
    
    public static void addClick(UUID usr, UUID doc) throws SQLException{        
        if(DbContext.getConnection() == null) throw new SQLException("No connection");
        
        Click clk = new Click();
        clk.setUsr(usr);
        clk.setDoc(doc);
        clk.setDate(LocalDate.now());
        
        clk.insert();
    }
}
