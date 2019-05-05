import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/28 19:08
 * @description：
 * @modified By：
 */
public class Test {


    //获取表明
    private static String getTabName(Class<?> clazz){
        String name = null;
        if (clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            name = table.name();
        }
        return name;
    }


    private static List<NameAndType> getColmun(Class<?> clazz) throws Exception {
        ArrayList<NameAndType> nameAndTypes = new ArrayList<>();
        //getDeclaredFields: 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段。
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null){
            //分析clazz中变量是否需要生产sql语句
            for (Field field :fields){
                //判断该类的字段是否有Colum,有则取出
                if (field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);
                    String name = column.name();
                    String type;
                    if (int.class.isAssignableFrom(field.getType())){
                        type = "integer";
                    }else if(String.class.isAssignableFrom(field.getType())){
                        type = "text";
                    }else{
                        throw new Exception(".....");
                    }
                    nameAndTypes.add(new NameAndType(name,type));
                }
            }
        }
        return nameAndTypes;
    }


    public static String createTable(Class<?> clazz) throws Exception {
        String sql = null;

        String tableName = getTabName(clazz);
        List<NameAndType> colums = getColmun(clazz);
        if (tableName != null &&  !tableName.equals("") && tableName.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer("create table " + tableName +"(");

            for (NameAndType column:colums){
                String bean = column.getName()+ " " + column.getType() + ",";
                stringBuffer.append(bean);
            }

            stringBuffer.deleteCharAt(stringBuffer.length() - 2);
            stringBuffer.append(");");
            sql = stringBuffer.toString();
        }
        return sql;
    }

}
