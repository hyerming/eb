package test.jackson;

import java.io.File;
import java.io.IOException;
import java.util.List;
 
import org.apache.commons.io.FileUtils;
 
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class Orgtest6DeSerializeExample2 {
 
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(new String(FileUtils.readFileToByteArray(new File("zoo2.json"))));
        List<Orgtest6Animal> animals = mapper.readValue(FileUtils.readFileToByteArray(new File("zoo2.json")), List.class);
        //List<Orgtest6Animal> animals = mapper.readValue(FileUtils.readFileToByteArray(new File("zoo2.json")),  new TypeReference<List<Orgtest6Animal>>() {});
        System.out.println(animals);
    }
}