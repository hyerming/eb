package test.jackson;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test1 {
	public static void main(String[] args) {

        // Create a POJO
		Test1MyPojo pojo = new Test1MyPojo();
        // Populate it with some data
        pojo.setName("Lucky");
        pojo.setOn(true);
        Map<String,Date> map = new HashMap<String,Date>();
        map.put("now", new Date());
        pojo.setMap(map);

        // Map it to JSON and then back again
        try {
            String pojoAsString = Test1PojoMapper.toJson(pojo, true);
            System.out.println("POJO as string:\n" + pojoAsString + "\n");
            Test1MyPojo pojo2 = (Test1MyPojo) Test1PojoMapper.fromJson(pojoAsString, Test1MyPojo.class);
            System.out.println("POJO toString():\n" + pojo2 + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create another POJO
        Test1MyPojo pojo3 = new Test1MyPojo();
        pojo3.setName("Baldwin");
        pojo3.setOn(false);
        Map<String,Date> map2 = new HashMap<String,Date>();
        map2.put("now", new Date());
        pojo3.setMap(map2);

        // Map it to JSON, store it on disk and then read it back
        try {
            FileWriter fw = new FileWriter("pojo.txt");
            Test1PojoMapper.toJson(pojo3, fw, true);

            FileReader fr = new FileReader("pojo.txt");

            Test1MyPojo pojo4 = (Test1MyPojo) Test1PojoMapper.fromJson(fr, Test1MyPojo.class);
            System.out.println("POJO read from file:\n" + pojo4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
