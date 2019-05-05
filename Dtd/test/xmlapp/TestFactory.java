package xmlapp;

import com.bean.User;
import com.xmlParser.FactoryBuilder;
import com.xmlParser.FactoryBuilder.Factory;
import org.junit.Test;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 11:53
 * @description：
 * @modified By：
 */
public class TestFactory {

    @Test
    public void test(){
        String xmlPath = TestFactory.class.getClassLoader().getResource("beans.xml").getFile();

        System.out.println(xmlPath);

        FactoryBuilder factoryBuilder = new FactoryBuilder(xmlPath);
        Factory factory = factoryBuilder.getFactoryBuilder();

        User user =(User) factory.getBean("bossID");
        System.out.println("user = " + user.toString());
    }
}
