package reflect;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> resp = new ArrayList<>();

        // 添加示例数据
        Map<String, Object> map1 = new HashMap<>();
        map1.put("date", "2021-05-01");
        resp.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("date", "2021-04-01");
        resp.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("date", "2021-06-01");
        resp.add(map3);

        //List<Map<String, Object>> resp = Lists.newArrayList();
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

        // 使用 sort 方法进行排序
        resp.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                String date1 = (String) m1.get("date");
                String date2 = (String) m2.get("date");
                return date2.compareTo(date1); // 倒序排列
            }
        });

        System.out.println(JSON.toJSONString(resp));
        // 输出排序后的结果
        for (Map<String, Object> map : resp) {
            System.out.println(map.get("date"));
        }
    }
}