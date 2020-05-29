package com.iber.portal;

import com.alibaba.fastjson.JSONObject;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.MD5;
import com.iber.portal.common.SysConstant;
import org.junit.Test;

import java.util.*;

/**
 * Created by 刘晓杰 on 2017/11/29.
 */
public class DemoTest {

    @org.junit.Test
    public void test() {
        mabi();
    }

    private void mabi() {
        List<A> wholeList = new ArrayList<>();
        A a2 = new A(1, "1");
        A a1 = new A(2, "2");
        A a3 = new A(3, "3");
        A a4 = new A(4, "4");
        wholeList.add(a1);
        wholeList.add(a2);
        wholeList.add(a3);
        wholeList.add(a4);

        List<B> FailList = new ArrayList<>();
        B b1 = new B(1, "1", 1);
        B b2 = new B(2, "2", 2);
        FailList.add(b1);
        FailList.add(b2);

        //将失败的二维码作为key，对应的对象作为value
        Map<Integer, B> map = new HashMap<>();
        for (B b : FailList) {
            map.put(b.getId(), b);
        }

        Iterator<A> iterator = wholeList.iterator();
        while (iterator.hasNext()) {
            Integer id = iterator.next().getId();
            if (map.get(id) != null) {
                iterator.remove();
            }
        }
        for (A integer : wholeList) {
            System.out.println(integer.getId());
        }

    }

    private String queryUrl = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";

    @org.junit.Test
    public void test2() {
        String deleteUrl = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";


//      删除百度人脸库里面的人脸信息
        Map<String, String> param = new HashMap<>();
        param.put("access_token", SysConstant.BAIDU_ACCESS_TOKEN);
        param.put("uid", MD5.MD5toUpper32("小鱼儿", "utf-8"));
        String result = HttpsClientUtil.post(deleteUrl, param);

        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("error_code") == null) {
            System.out.println("error_code");
        }
        System.out.println(jsonObject.toString());

    }

    @org.junit.Test
    public void test3() {
        Map<String, String> param = new HashMap<>();
        param.put("access_token", SysConstant.BAIDU_ACCESS_TOKEN);
        param.put("uid", "11580769946");
        String result = HttpsClientUtil.post(queryUrl, param);

        System.out.println(result);
        JSONObject jsonObjec = JSONObject.parseObject(result);
        System.out.println(jsonObjec.toJSONString());

    }
    @Test
    public void test4(){
        Integer a =20;
        System.out.println(a.doubleValue()/100);

        String str =String.valueOf(System.currentTimeMillis());

        System.out.println(String.valueOf(Integer.MAX_VALUE).length());
        str = "19598028788";
        System.out.println(str.length());


    }
    @Test
    public void test5(){
        String str = "-3.12";
       int a = (int)(Double.parseDouble(str) * 100);
       int b =2 ;
        System.out.println(a + b);


    }
    @Test
    public void test6(){
        Integer a=100;
        int b =10;
        System.out.println(b-a);
    }






}
