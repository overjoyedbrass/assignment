
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.time.LocalDate;
import java.util.ArrayList;

public class DocumentCount {
    
    public static ArrayList<Document> getAll(int limit) throws SQLException{
        //vybere iba dokumenty, ktoré boli navstivene
        //String sqlOptimal = "SELECT doc, d.name, count(doc) as clicks FROM clicks as c JOIN documents as d ON d.uuid = c.doc WHERE date >= DATE(NOW() - INTERVAL '7 days') GROUP BY doc, d.name ORDER BY clicks DESC";
        String sql = "SELECT "
                + "uuid, "
                + "name, "
                + "COALESCE(c.count, 0) as clicks "
                + "FROM documents "
                + "LEFT JOIN "
                + "(SELECT doc, count(doc) as count FROM clicks as c WHERE date >= DATE(NOW() - INTERVAL '7 days') GROUP BY doc) as c "
                + "ON uuid = c.doc "
                + "ORDER BY clicks DESC";
        sql += " LIMIT " + (limit > 0 ? limit : String.valueOf(100));
        
        
        try(PreparedStatement ps = DbContext.getConnection().prepareStatement(sql)){
            try (ResultSet r = ps.executeQuery()) {
                ArrayList<Document> result = new ArrayList<>();

                while (r.next()) {
                    Document doc = new Document();
                    doc.setUUID((UUID) r.getObject("uuid"));
                    doc.setName(r.getString("name"));
                    doc.setClicksCount(r.getInt("clicks"));
                    
                    result.add(doc);
                }                
                return result;
            }
        }
        
    }
}
