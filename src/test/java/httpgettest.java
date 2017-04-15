import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.util.HttpUtil;

import java.math.BigDecimal;

/**
 * Created by gaorui on 17/4/15.
 */
public class httpgettest {

    public static void main(String args[]){

        String str = "xxxxxxx";
        String strResult = HttpUtil.sendGet("http://maps.google.cn/maps/api/geocode/json","address="+str+"&sensor=false");
        JSONObject jsonObject = JSON.parseObject(strResult);
        JSONArray jsonArray = (JSONArray) jsonObject.get("results");
        JSONObject jsonObject1 = (JSONObject)jsonArray.get(0);
        JSONObject jsonObject2 = (JSONObject)jsonObject1.get("geometry");
        JSONObject jsonObject3 = (JSONObject)jsonObject2.get("bounds");
        JSONObject jsonObject4 = (JSONObject)jsonObject3.get("northeast");
        BigDecimal lat = (BigDecimal) jsonObject4.get("lat");
        BigDecimal lng = (BigDecimal) jsonObject4.get("lng");

        System.out.println(lat+"#"+lng);

    }
}
