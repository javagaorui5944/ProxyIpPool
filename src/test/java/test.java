

import sun.misc.IOUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class test {




    public static void main(String[] args) {



System.out.println(test.xxx());

    }

    public static  int xxx(){

        try {

            return  1 ;
        }catch (Exception e)
        {
            return  2;
        }
        finally {
            return  3;
        }
    }


}

