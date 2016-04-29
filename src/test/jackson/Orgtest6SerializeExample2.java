package test.jackson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 

import org.apache.commons.io.IOUtils;
 

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class Orgtest6SerializeExample2 {
    private static String outputFile = "zoo2.json";
 
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        // let start creating the zoo
    	Orgtest6Lion lion = new Orgtest6Lion("Simba");
    	Orgtest6Elephant elephant = new Orgtest6Elephant("Manny");
        List<Orgtest6Animal> animals = new ArrayList<>();
        animals.add(lion);
        animals.add(elephant);
 
        ObjectMapper mapper = new ObjectMapper();
        //String s = mapper.writeValueAsString(animals);
        
        String s = mapper.writerWithType(new TypeReference<List<Orgtest6Animal>>() {}).writeValueAsString(animals);
        System.out.println(s);
        IOUtils.write(s, new FileOutputStream(new File(outputFile)));
    }
}