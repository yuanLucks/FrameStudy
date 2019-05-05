/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/4/28 19:06
 * @description：
 * @modified By：
 */
@Table(name = "t_student")
public class Student {

    @Column(name = "c_name")
    public String name;

    @Column(name = "c_sex")
    public String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }





















}
