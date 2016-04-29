package test.jackson;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
public class JacksonTest {
	private JsonGenerator jsonGenerator = null;  
	private ObjectMapper objectMapper = null;  
	private AccountBean bean = null;     
	@Before   
	public void init() {   
		bean = new AccountBean();    
		bean.setAddress("china-Guangzhou");
		bean.setEmail("hoojo_@126.com");   
		bean.setId(1);    
		bean.setName("hoojo");       
		objectMapper = new ObjectMapper();
		try {
			JsonFactory f = new JsonFactory();
			jsonGenerator = f.createGenerator(System.out, JsonEncoding.UTF8);
			System.out.println(JsonEncoding.UTF8.toString());
		} catch (IOException e) {
			e.printStackTrace();    
		}
	}
	
	@After   
	public void destory() {
		try {
			if (jsonGenerator != null) {				
				jsonGenerator.flush();     
			}     
			if (!jsonGenerator.isClosed()) {
				jsonGenerator.close();     
			}     
			jsonGenerator = null;    
			objectMapper = null;    
			bean = null;    
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test  
	public void writeEntityJSON() {
		try {   
			System.out.println("jsonGenerator");
			//writeObject可以转换java对象，eg:JavaBean/Map/List/Array等			
			objectMapper.writeValue(jsonGenerator, bean);
			System.out.println();       
			//System.out.println("ObjectMapper");   
			//writeValue具有和writeObject相同的功能  
			//objectMapper.writeValue(System.out, bean);
		} catch (Exception e) {
			e.printStackTrace();   
		}  
	}	
}
