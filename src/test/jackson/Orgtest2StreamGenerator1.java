package test.jackson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
 
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
 
/**
 * 
 * In This example we look at generating a json using the jsongenerator. we will
 * be creating a json similar to
 * http://freemusicarchive.org/api/get/albums.json?
 * api_key=60BLHNQCAOUFPIBZ&limit=1, but use only a couple of fields
 * 
 */
public class Orgtest2StreamGenerator1 {
    public static void main(String[] args) throws IOException {
        JsonFactory factory = new JsonFactory();
        //JsonGenerator generator = factory.createGenerator(new FileWriter(new File("albums.json")));
        StringWriter sw=new StringWriter();
        JsonGenerator generator = factory.createGenerator(sw);
        // start writing with {
        generator.writeStartObject();
        generator.writeFieldName("title");
        generator.writeString("Free Music Archive - Albums");
        generator.writeFieldName("dataset");
        // start an array
        generator.writeStartArray();
        generator.writeStartObject();
        generator.writeStringField("album_title", "A.B.A.Y.A.M");
        generator.writeEndObject();
        generator.writeEndArray();
        generator.writeEndObject();
        
        generator.close();
        System.out.println(sw.toString());
    }
}