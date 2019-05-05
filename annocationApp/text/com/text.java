package com;

import com.bean.ORMAnnoHelps;
import com.bean.Order;
import com.bean.User;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 14:48
 * @description：
 * @modified By：
 */
public class text {
    @Test
    public void te(){
        String tableName = ORMAnnoHelps.getTableName(Order.class);
        System.out.println("tableName = " + tableName);




        //select  xxxxx,xxx  from tableName
        Field[] fields = User.class.getDeclaredFields();
        for(Field f:fields){
            System.out.println(ORMAnnoHelps.getClomunName(f));
        }
    }
}
