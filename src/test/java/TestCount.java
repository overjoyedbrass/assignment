import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.*;
import static org.junit.Assert.*;
import org.testcontainers.containers.PostgreSQLContainer;
import java.time.LocalTime;


public class TestCount {
    private static final String DB_NAME = "postgres";
    private static final String USER = "postgres";
    private static final String PWD = "root";
    
    @Rule
    public PostgreSQLContainer psgc = new PostgreSQLContainer("postgres")
            .withDatabaseName(DB_NAME)
            .withUsername(USER)
            .withPassword(PWD);
    
    @Test
    public void test1() throws SQLException{
        Connection con = DbContext.getConnection(
                psgc.getContainerIpAddress(),
                PostgreSQLContainer.POSTGRESQL_PORT,
                DB_NAME,
                USER,
                PWD);
        DbContext.setConnection(con);
        
        LocalTime begin = LocalTime.now();
        
        DbContext.executeSQLFile("sql/createscript.sql");
        DbContext.executeSQLFile("sql/generatescript.sql");
        
        int diff = LocalTime.now().toSecondOfDay()-begin.toSecondOfDay();
        System.out.println("Generating data: " + diff + " sec");
    
        var list = DocumentCount.getAll(10);
        
        Main.printResult(list);
        assertTrue(list.size() > 0);
    }
    
}
