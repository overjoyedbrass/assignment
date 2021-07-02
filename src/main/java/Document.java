
import java.util.UUID;

public class Document {
    private UUID uuid;
    private String name;
    private int clicksCount;
    
    public void setUUID(UUID uuid){
        this.uuid = uuid;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setClicksCount(int count){
        this.clicksCount = count;
    }
    
    public UUID getUUID(){
        return this.uuid;
    }
    public String getName(){
        return this.name;
    }
    public int getClicksCount(){
        return this.clicksCount;
    }
    
}
