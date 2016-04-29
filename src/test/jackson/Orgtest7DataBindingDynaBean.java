package test.jackson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class Orgtest7DataBindingDynaBean {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=2";
        ObjectMapper mapper = new ObjectMapper();        
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Orgtest7AlbumsDynamic albums = mapper.readValue(new URL(url), Orgtest7AlbumsDynamic.class);
        Orgtest7DatasetDynamic[] datasets = albums.getDataset();
        for (Orgtest7DatasetDynamic dataset : datasets) {
            System.out.println(dataset.get("album_type"));
             
        }
    }
 
     
}