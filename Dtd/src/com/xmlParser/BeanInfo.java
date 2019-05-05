package com.xmlParser;

import java.util.List;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 8:58
 * @description：对应的factory.dtd文件的bena实体类
 * @modified By：
 */
public class BeanInfo {
    private String id;
    private String clsName; //对应的Class文件
    private String scope;

    //子标签属性
    private List<PropsInfo> props;

    public List<PropsInfo> getProps() {
        return props;
    }

    public void setProps(List<PropsInfo> props) {
        this.props = props;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
