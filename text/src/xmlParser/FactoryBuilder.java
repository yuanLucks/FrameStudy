package xmlParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/5/5 10:27
 * @description：
 * @modified By：
 */
public class FactoryBuilder {

    private static HashMap<String,BeansPojo> beansPojoMap;

    public FactoryBuilder(String xmlPath) throws Exception {
        File file = new File(xmlPath);
        if (!file.exists()){
            throw new RuntimeException("文件不存在");
        }


        //创建SAX工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();

        //获取解析类对象
        SAXParser saxParser = factory.newSAXParser();

        saxParser.parse(new FileInputStream(xmlPath),new DefaultHandler(){

            private BeansPojo beansPojo;

            @Override
            public void startDocument() throws SAXException {
                beansPojoMap = new HashMap<>();
            }


            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if ("bean".equals(qName)){
                    beansPojo = new BeansPojo();
                    beansPojo.setId(attributes.getValue("id"));
                    beansPojo.setClazz(attributes.getValue("class"));
                    beansPojo.setScope(attributes.getValue("scope"));
                }else if ("property".equals(qName)){
                    ArrayList<ProPojo> beansPojos = new ArrayList<>();
                    beansPojos.add(new ProPojo(attributes.getValue("name"),attributes.getValue("value")));
                    beansPojo.setProPojo(beansPojos);
                }
            }


            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if ("bean".equals(qName)){
                    beansPojoMap.put(beansPojo.getId(),beansPojo);
                    System.out.println(beansPojo.toString());
                    System.out.println(beansPojo.getProPojo().toString());
                    beansPojo = null;
                }
            }

        });

    }

    public static void main(String[] args) throws Exception {
        String url = FactoryBuilder.class.getClassLoader().getResource("beans.xml").getFile();
        FactoryBuilder factoryBuilder = new FactoryBuilder(url);
    }
}
