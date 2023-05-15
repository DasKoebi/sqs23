package inf.iso_to_name.json;

public class IndustryIdentifiers {
    private String type;
    private String identifier;

    public String getIdentifier(){
        return identifier;
    }

    public String getType() {
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }
}
