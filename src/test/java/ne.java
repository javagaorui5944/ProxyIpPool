/**
 * Created by gaorui on 17/4/21.
 */
public class ne {

    private long inci_Test = 0;

    public static void main(String args[]){
        long startTime=System.currentTimeMillis();   //获取开始时间

        ne ne = new ne();
        ne.countTest();
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");


    }

    public void countTest(){
        for (long i = 0; i < 900000000l; i++) {
            inci_Test++;
            //System.out.println(inci_Test);
        }
    }
}
