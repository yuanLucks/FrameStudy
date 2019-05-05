package com.bean;

import com.orm.DBSource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/29 16:29
 * @description：连接数据库
 * @modified By：
 */
public class DBSessionFactory {
    private DBSource dbSource;  //数据源

    private Properties props;   //数据源连接的属性

    public DBSessionFactory(){
        props = new Properties();
        try {
            //获取配置文件
            props.load(ClassLoader.getSystemResourceAsStream("dbConfig.properties"));

            dbSource = new DBSource(props);
            Connection conn = dbSource.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //打开数据库连接
    public DBSession openSession() throws Exception {
        return new DBSession(dbSource.openConnection());
    }

    //主要操作数据库
    //1:增加，删除，修改，查询
    public static class DBSession{
        private Connection conn;//数据库连接对象
        public DBSession(Connection conn){
            this.conn = conn;
        }


        private void paramHandler(List<Field> fields ,PreparedStatement ps){

        }


        //修改语句
        public int update(Object obj) throws Exception {
            String sql = "update %s set %s where %s";
            StringBuilder updateColumn = new StringBuilder();
            String where = "";

            //获取所有对象字段（包含主键字段）
            Field[] fs = obj.getClass().getDeclaredFields();
            //更新的字段集合
            List<Field> updateFields = new ArrayList<>();
            Field f = null;
            for (int i=0,len = fs.length; i<len;i++){
                f = fs[i];
                //判断字段是否为主键
                if (ORMAnnoHelps.isId(f)){
                    f.setAccessible(true);
                    where = ORMAnnoHelps.getClomunName(f) + "=" ;
                    //判断主键字段的类型
                    if (f.getType()==String.class){
                        where+="'" + String.valueOf(f.get(obj)) + "'";
                    }else{
                        where += f.get(obj);
                    }
                    continue;
                }

                //非主键
                updateColumn.append(ORMAnnoHelps.getClomunName(f)+"=?");
                if (i!=len-1){
                    updateColumn.append(",");
                }

                //将参与·更新的字段添加到集合中
                updateFields.add(f);
                f =null;
            }

            //生成真正的sql
            sql = String.format(sql,ORMAnnoHelps.getTableName(obj.getClass()),updateColumn.toString(),where);
            System.out.println(sql);

            //执行更新语句，参数处理
            PreparedStatement ps = conn.prepareStatement(sql);
            Class type = null;
            for (int i =0,len=updateFields.size();i<len;i++){
                f=updateFields.get(i);
                f.setAccessible(true);

                type = f.getClass();
                if (type==String.class){
                    ps.setString(i+1,String.valueOf(f.get(obj)));
                }else if(type==int.class || type==Integer.class){
                    ps.setInt(i,f.getInt(obj));
                }
            }

            int i = ps.executeUpdate();
            ps.close();
            return i;
        }


        //插入数据
        public int sava(Object obj) throws SQLException, IllegalAccessException {
            String sql  = "insert into %s(%S) values(%s)";
            StringBuilder columns = new StringBuilder();
            StringBuilder params = new StringBuilder();

            //获取实体对象的所有字段
            Field[] fs = obj.getClass().getDeclaredFields();
            for (int i =0,len = fs.length;i<len;i++){
                columns.append(ORMAnnoHelps.getClomunName(fs[i]));
                params.append("?");

                if (i!=len-1){
                    columns.append(",");
                    params.append(",");
                }
            }

            //生成最终的sql
            sql = String.format(sql,ORMAnnoHelps.getTableName(obj.getClass()),columns.toString(),params.toString());
            System.out.println(sql);


            //创建预处理sql对象
            PreparedStatement ps = conn.prepareStatement(sql);
            int i = 1;
            //设置预处理参数值
            for (Field f : fs){
                Class<?> type = f.getType();
                f.setAccessible(true);
                if (type==String.class){
                    ps.setString(i,String.valueOf(f.get(obj)));
                }else if (type==int.class || type==Integer.class){
                    ps.setInt(i,f.getInt(obj));
                }else if (type==Double.class || type==double.class){
                    ps.setDouble(i,f.getDouble(obj));
                }
                i++;
            }
            //执行预处理语句
            int i1 = ps.executeUpdate();
            ps.close();
            return i1;
        }

        //查询所有数据
        public <T> List<T> list(Class<T> cls) throws Exception {
            //生成select x,y,z from tb_user 语句
            //%s是字符串的占位符
            String sql = "select %s from %s";

            // 通过反射获取字段
            //生成查询字段的列表
            StringBuilder columns = new StringBuilder();
            //获取全部字段
            Field[] fs = cls.getDeclaredFields();
            //这样的for循环性能会大大提高
            for (int i=0,len=fs.length; i<len;i++){
                columns.append(ORMAnnoHelps.getClomunName(fs[i]));
                //当字段不是最后一个时都要加上逗号来分割。
                if (i!=len-1){
                    columns.append(",");
                }
            }

            //生成完整的sql语句
            sql = String.format(sql,columns.toString(),ORMAnnoHelps.getTableName(cls));
            System.out.println(sql);
            //创建执行sql的语句对象（Statment,PreparedStatemt）;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            List<T> list = new ArrayList<>();
            T obj ;
            while (rs.next()){
                //实例化实体类对象
                obj = cls.newInstance();
                //将读取的字段注入到对应的对象属性上
                for (Field f: fs) {
                    f.setAccessible(true);
                    Class<?> type = f.getType();

                    //注入数据
                    if (type==String.class){
                        f.set(obj,rs.getString(ORMAnnoHelps.getClomunName(f)));
                    }else if (type==Integer.class || type==int.class){
                        f.set(obj,rs.getInt(ORMAnnoHelps.getClomunName(f)));
                    }else if (type==double.class || type==Double.class){
                        f.set(obj,rs.getDouble(ORMAnnoHelps.getClomunName(f)));
                    }else if (type== Date.class){
                        f.set(obj,rs.getDate(ORMAnnoHelps.getClomunName(f)));
                    }
                }

                list.add(obj);
            }
            System.out.println(list.toString());
            statement.close();
            return list;
        }

        public void close(){
            if(conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                conn = null;
            }
        }


    }

    public static void main(String[] args) throws Exception {
        DBSessionFactory dbSessionFactory = new DBSessionFactory();
        DBSession dbSession = dbSessionFactory.openSession();
        List<User> list =  dbSession.list(User.class);
        System.out.println(list.size());


       /* int sava = dbSession.sava(new User("1","1","1","1","1"));
        System.out.println(sava);*/

        int update = dbSession.update(new User());
        System.out.println(update);

    }
}
