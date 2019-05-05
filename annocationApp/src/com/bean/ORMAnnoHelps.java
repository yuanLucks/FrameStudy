package com.bean;

import com.annocation.Column;
import com.annocation.Table;

import java.lang.reflect.Field;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 14:28
 * @description：实现orm框架中解析相关注解的工具类
 *                  1：获取类上得@Table注解信息
 *                  2：获取字段上的@Column注解信息
 * @modified By：
 */
public class ORMAnnoHelps {
    //指定类上的注解
    public static String getTableName(Class<?> beanCls){
        //通过Class获取类上得@Table
        Table table = beanCls.getAnnotation(Table.class);
        if (table == null){
            //类上没有使用@Table注解
            //在此表示类的简称即为对应的表名
            return beanCls.getSimpleName().toLowerCase();
        }else{
            //表示类上有注解
            return table.value();
        }
    }

    //返回指定字段上得名字
    public static String getClomunName(Field field){
        Column cloumn = field.getAnnotation(Column.class);

        if (cloumn == null){
            return field.getName().toLowerCase();
        }else{
            return cloumn.value();
        }
    }

    public static boolean isId(Field field){
        //通过Class获取类上得@Table
        Column columnAnno = field.getAnnotation(Column.class);
        if (columnAnno != null){
            //获取字段为主键字段
            return columnAnno.isId();
        }
        return false;
    }
}
