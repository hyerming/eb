package test.jackson;

import java.util.Date;
import java.util.Map;

public class Test1MyPojo {
    private boolean on;
    private String name;
    private Map<String,Date> map;

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean getOn() {
        return this.on;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setMap(Map<String,Date> map) {
        this.map = map;
    }

    public Map<String,Date> getMap() {
        return this.map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("on=" + on);
        sb.append(" name=" + name);
        sb.append(" map={");
        for (Map.Entry<String,Date> entry : this.map.entrySet()) {
            sb.append("" + entry.getKey() + "=" + entry.getValue());
        }
        sb.append("}");
        return sb.toString();
    }
}