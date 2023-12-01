package reflect;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/21 11:14
 */
public class Test {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //test1();
        //test2();
        //String str1 = "9542598\t3370084\t0.0\t0.0\t0.0\t0.0\t0.0\t0\t周五不可用|周六不可用|\tNULL\t0.0\t0.0\t0.0\t0.0\t0.0\t0.0\t0.75\t0.0\t0.33\t0.29\t0.4\t0.0\t0.0\t0.0\t2023-11-14 09:34:58\t2023-11-14 09:34:58";
        //String str2 = "9542601\t3371878\t0.0\t0.0\t0.13\t1074.0\t1002.81\t0\tNULL\tNULL\t0.0\t0.0\t0.0\t0.0\t0.0\t0.0\t0.0\t0.0\t0.67\t0.29\t0.33\t0.0\t0.0\t0.0\t2023-11-14 09:34:58\t2023-11-14 09:34:58";
        //ArrayList<String> list = Lists.newArrayList();
        //list.add(str1);
        //list.add(str2);
        //for (String str : list) {
        //    System.out.println(test3(str));
        //}
        //String s = test3("");
        //System.out.println(s);
        //test4();
        //test5();
        //test6();
        test3();
    }


    public static void test3(){
        List<Map<String, Object>> resp = Lists.newArrayList();
        ArrayList<String> list = Lists.newArrayList();
        list.add("2023-11-11");
        list.add("2023-11-01");
        list.add("2023-11-12");
        list.add("2023-11-13");
        list.add("2023-11-05");
        list.add("2023-11-06");
        list.add("2023-11-10");
        list.add("2023-11-09");
        list.add("2023-11-15");
        for (String date : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("date", date);
            resp.add(map);
        }
        System.out.println(JSON.toJSONString(resp));
        List<Map<String, Object>> maps = sortRes(resp);
        System.out.println("=========");
        System.out.println(JSON.toJSONString(maps));
    }
    private static List<Map<String, Object>> sortRes(List<Map<String, Object>> resp) {
        resp.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String date1 = (String) o1.get("date");
                String date2 = (String) o1.get("date");
                return date2.compareTo(date1);
            }
        });
        // 使用 sort 方法进行排序
        resp.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String date1 = (String) o1.get("date");
                String date2 = (String) o2.get("date");
                return date2.compareTo(date1);
            }
        });
        return resp;
    }
    public static Date parse(String str, String pattern) throws ParseException {
        return DateUtils.parseDate(str, pattern);
    }
    public static void test6(){
        String str = "4786#913#5996#338#914#718#711#719#926#0#4121#4180#4104#4108#5010#5018#5019#5020#5021#5022#5023#5024#5025#5026#80#4102#96#705#671#1096#4150#4130#9#247#4117#5004#5005#20010#352#873#755#4580#4581#-2#882#5500#4896#4577#5029#5502#4579#5503#5504#927#4110#4112#99999#778#5995#5997#4106#4519#422#863#469#4995#4996#4118#4514#826#4515#4516#101010";
        List<String> list = new ArrayList<>();
        String[] arr = str.split("#");
        for (String s : arr) {
            list.add(s);
        }
        Map<String, Integer> countMap = new HashMap<>();
        for (String s : list) {
            countMap.put(s, countMap.getOrDefault(s, 0) + 1);
        }
        List<String> duplicates = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey());
            }
        }
        System.out.println("Duplicates: " + duplicates);
    }

    public static void test5(){
        String str = "4150#4130#9#247#4117#5004#5005#20010#352#873#755#4580#4581#-2#882#5500#4896#4577#5029#5502#4579#5503#5504#927#4110#4112#99999#778#5995#5997#4106#4519#422#863#469#4995#4996#4118#4514#826#4515#4516";
        String replacedStr = str.replace("#", ",");
        System.out.println(replacedStr);
    }

    public static void test(){
        Double i = (double)0 / 1;
        System.out.println(null == i);
        System.out.println(i);
    }
    private static void test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Calculator calculator = new Calculator();
        Method method = calculator.getClass().getMethod("add", int.class, int.class);
        Object obj = "10";
        int result = (int) method.invoke(calculator, obj, 20);
        System.out.println(result);
    }

    public static void test4(){
        long var1 = 10L;
        long var2 = 3L;
        double res = (double) var1 / var2;
        System.out.println(res);
    }

    public static void test1() {
        float result = 1.926f;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String canOrderRate = df.format(result);
        System.out.println(canOrderRate);
    }

    public static String test3(String input) {
        input = "4053039\n" +
                "4114260\n" +
                "4114413\n" +
                "4125987\n" +
                "3973356\n" +
                "3973362\n" +
                "4078077\n" +
                "4078074\n" +
                "4167966\n" +
                "4167600\n" +
                "4167837\n" +
                "4162056\n" +
                "4162089\n" +
                "4162050\n" +
                "4162032\n" +
                "4162068\n" +
                "4162071\n" +
                "4162077\n" +
                "4162038\n" +
                "4162047\n" +
                "4162041\n" +
                "4112601\n" +
                "4046397\n" +
                "4046400\n" +
                "4046163\n" +
                "4046169\n" +
                "4046427\n" +
                "4142067\n" +
                "4156659\n" +
                "4156155\n" +
                "4142070\n" +
                "4079610\n" +
                "4079391\n" +
                "4167846\n" +
                "4167900\n" +
                "4165761\n" +
                "4160721\n" +
                "4151091\n" +
                "4151088\n" +
                "4141896\n" +
                "4167939";
        return input.replaceAll("\n", ",");
    }

}
