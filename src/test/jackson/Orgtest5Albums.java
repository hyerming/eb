package test.jackson;

public class Orgtest5Albums {
	 
    private String title;
    private Orgtest5Dataset[] dataset;
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public void setDataset(Orgtest5Dataset[] dataset) {
        this.dataset = dataset;
    }
 
    public String getTitle() {
        return title;
    }
 
    public Orgtest5Dataset[] getDataset() {
        return dataset;
    }
}