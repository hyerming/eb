package test.jackson;

public class Orgtest7AlbumsDynamic {
	 
    private String title;
    private Orgtest7DatasetDynamic[] dataset;
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public void setDataset(Orgtest7DatasetDynamic[] dataset) {
        this.dataset = dataset;
    }
 
    public String getTitle() {
        return title;
    }
 
    public Orgtest7DatasetDynamic[] getDataset() {
        return dataset;
    }
}