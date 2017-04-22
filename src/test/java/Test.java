/**
 * Created by gaorui on 17/4/21.
 */
public class Test {

        private long counts = 0;// 这个是统计总数

        private long inci_1 = 0;// 这个是线程1的变量

        private long inci_2 = 0;// 这个是线程2的变量

        private long inci_3 = 0;// 这个是线程3的变量
        static long startTime = 0l;
        //static long endTime = 0l;

        private boolean a = false, b = false, c = false;

        /**
         * @param args
         */
        public static void main(String[] args) {
            startTime=System.currentTimeMillis();   //获取开始时间


            // TODO Auto-generated method stub
            Test t = new Test();
            Inc1 inc1 = t.new Inc1();
            Inc2 inc2 = t.new Inc2();
            Inc3 inc3 = t.new Inc3();
            CountAll c = t.new CountAll();

            Thread t1 = new Thread(inc1);
            Thread t2 = new Thread(inc2);
            Thread t3 = new Thread(inc3);
            Thread ct = new Thread(c);

            t1.start();
            t2.start();
            t3.start();
            ct.start();


            //t.countTest();


        }
        //线程1
        class Inc1 implements Runnable {
            public void run() {
                for (long i = 0; i < 300000000l; i++) {
                    inci_1++;
                }
                a = true;
            }
        }
        //线程2
        class Inc2 implements Runnable {
            public void run() {
                for (long i = 0; i < 300000000l; i++) {
                    inci_2++;
                }
                b = true;
            }
        }
        //线程3
        class Inc3 implements Runnable {
            public void run() {
                for (long i = 0; i < 300000000l; i++) {
                    inci_3++;
                }
                c = true;
            }
        }
        //统计器
        class CountAll implements Runnable {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (!(a && b&&c)) {
                    counts = inci_1 + inci_2 + inci_3;

                    System.out.println(counts);
                }
                long endTime=System.currentTimeMillis(); //获取结束时间

                System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                counts = inci_1 + inci_2 + inci_3;

                System.out.println("最后的结果是: "+counts);
            }
        }


}
