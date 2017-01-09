/**
 * Created by gaorui on 17/1/4.
 */
public class test2 {

    private int age ;
    private String name;
    private static  int x;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String args[]){

        for (int i = 0;i<x(); i++ ){
            System.out.println(i);
        }
    }

    public static int x(){
        x++;
        return x;

    }
}
