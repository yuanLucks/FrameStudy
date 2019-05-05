package com.xmlParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 9:02
 * @description：设计工厂（容器类）构造器
 * @modified By：
 */
public class FactoryBuilder {
/**
  *@Author shiloh
  *@Description
  *@Date 9:07 2019/4/29
  *1:解析指定的xml文件（factory.dtd）类型的bean配置文件
 * 2:创建 获取bean的工厂类对象
 *      2.1）根据id来获取Bean的实例（反射）
 *      2.2）注入实体类属性值
**/

    //将解析的Bean  id为键 ，Class为值
    private static HashMap<String,BeanInfo> beanMap;


    //对外暴露的工厂
    public Factory getFactoryBuilder(){
        return new Factory();
    }



    public FactoryBuilder(String xmlPath){
        try{
            File file = new File(xmlPath);
            if (!file.exists()){
                throw new RuntimeException("文件不存在，或路径错误!");
            }

            //创建SAX工厂     使用静态的方法创建
            SAXParserFactory factory = SAXParserFactory.newInstance();


            //创建SAXParser解析器类对象
            SAXParser saxParser = factory.newSAXParser();

            //开始解析xml文件
            saxParser.parse(new FileInputStream(xmlPath),new DefaultHandler(){
                private BeanInfo beanInfo;

                @Override
                public void startDocument() throws SAXException {
                   //文档开始解析
                    beanMap = new HashMap<>();
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException {
                    //标签开始解析
                    //判断该节点是否为bean
                    if ("bean".equals(qName)){
                        //取出节点的属性的值 并 复制给实体类
                        beanInfo = new BeanInfo();
                        beanInfo.setId(attr.getValue("id"));
                        beanInfo.setClsName(attr.getValue("class"));
                        beanInfo.setScope(attr.getValue("scope"));
                        beanInfo.setProps(new ArrayList<PropsInfo>());
                    }else if ("property".equals(qName)){
                        //判断该节点是否是property，赋值给bean标签对应的实体类中（BeanInfo）
                        beanInfo.getProps().add(new PropsInfo(attr.getValue("name"),attr.getValue("value"),attr.getValue("type")));
                    }
                }


                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    //标签结束解析
                    //bean标签的结束
                    if ("bean".equals(qName)){
                            //将解析完的Bean标签对应得对象添加到map中
                            beanMap.put(beanInfo.getId(),beanInfo);
                            beanInfo = null;
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //写一个内部类
    //利用反射实例化对象，并给你对象赋值
    public static class Factory{
        //根据id获取Bean对象
        public Object getBean(String id){
            //获取BeanInfo
            BeanInfo beanInfo = beanMap.get(id);
            //判断该节点是否为空
            if (beanInfo != null) return null;
            try{
                //通过反射实例化Bean标签的class属性指定的类对象（字节码对象）
                Class cls = Class.forName(beanInfo.getClsName());

                //实例化Class
                Object obj = cls.newInstance();

                //读取property所有的标签，将标签的中的属性注入到实例化话Class中
                //循环propsInfo然后赋值
                for (PropsInfo pi: beanInfo.getProps()){
                 //Property标签属性名对应的是object对象的字段名
                    Field field = cls.getDeclaredField(pi.getName());

                    //设置访问性为true
                    field.setAccessible(true);

                    //向字段注入属性值
                    if ("long".equals(pi.getType())){
                        field.set(obj,Long.parseLong(pi.getValue()));
                    }else if ("float".equals(pi.getType())){
                        field.set(obj,Float.parseFloat(pi.getValue()));
                    }else if ("int".equals(pi.getType())){
                        field.set(obj,Integer.parseInt(pi.getValue()));
                    }else{
                        field.set(obj,pi.getValue());
                    }
                }
                return obj;//返回实体类对象
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
