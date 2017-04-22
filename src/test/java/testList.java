import org.apache.poi.util.SystemOutLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaorui on 17/4/22.
 */
public class testList {

    public static void main(String args[]){

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.removeAll(list);
        System.out.println(list.size());
    }
}
