import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.time.LocalDate;

public class Click{
    private UUID usr;
    private UUID doc;
    private LocalDate date;
    
    public void setUsr(UUID usr){
        this.usr = usr;
    }
    public void setDoc(UUID doc){
        this.doc = doc;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }    
    
    public UUID getUsr(){
        return this.usr;
    }
    public UUID getDoc(){
        return this.doc;
    }
    public LocalDate getDate(){
        return this.date;
    }
    
    public void insert() throws SQLException{
        String sql = "INSERT INTO clicks (usr, doc, date) VALUES (?, ?, ?)";
        try(PreparedStatement ps = DbContext.getConnection().prepareStatement(sql)){
            ps.setObject(1, this.usr);
            ps.setObject(2, this.doc);
            ps.setDate(3, java.sql.Date.valueOf(this.date));

            ps.executeQuery();
        }    
    }
}
