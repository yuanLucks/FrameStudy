package pojo;

/**
 * @author ：xie yuan yang
 * @date ：Created in 2019/5/5 15:17
 * @description：
 * @modified By：
 */
@MyCompone
public class Ts {
    @MyAutowirde
    private Person person;

    public Ts() {
    }


    public void aa(){
        System.out.println(person);
    }
}
