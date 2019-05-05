package xmlParser;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/5/5 10:42
 * @description：
 * @modified By：
 */
public class ProPojo {
    private String name;
    private String value;

    public ProPojo() {
    }

    public ProPojo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProPojo{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
