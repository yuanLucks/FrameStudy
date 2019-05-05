package xmlParser;

import java.util.List;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/5/5 10:25
 * @description：
 * @modified By：
 */
public class BeansPojo {

    private String id;
    private String clazz;
    private String scope;
    private List<ProPojo> proPojo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<ProPojo> getProPojo() {
        return proPojo;
    }

    public void setProPojo(List<ProPojo> proPojo) {
        this.proPojo = proPojo;
    }

    @Override
    public String toString() {
        return "BeansPojo{" +
                "id='" + id + '\'' +
                ", clazz='" + clazz + '\'' +
                ", scope='" + scope + '\'' +
                ", proPojo=" + proPojo +
                '}';
    }
}
