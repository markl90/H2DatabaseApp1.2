package H2Interaction;

public class Upgrade {

    private int id;
    private String fileName;
    private String description;

    public Upgrade(int id, String fileName, String description){
        this.id = id;
        this.fileName = fileName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
