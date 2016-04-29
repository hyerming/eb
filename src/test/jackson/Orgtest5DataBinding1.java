package test.jackson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class Orgtest5DataBinding1 {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=2";
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Orgtest5Albums albums = mapper.readValue(new URL(url), Orgtest5Albums.class);
        Orgtest5Dataset[] datasets = albums.getDataset();
        for (Orgtest5Dataset dataset : datasets) {
            System.out.println(dataset.getAlbum_title());
        }
    }    
}