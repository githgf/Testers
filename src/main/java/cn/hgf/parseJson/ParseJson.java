package cn.hgf.parseJson;

import cn.hgf.common.CommonParam;
import cn.hgf.poi.CsvUtil;
import cn.hgf.poi.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;

/**
 * @Author: FanYing
 * @Date: 2018-08-08 19:40
 * @Desciption:
 */
public class ParseJson {

    public static String[] titles = new String[]{};
    public static String[] commonStrings = new String[]{};

    /**
     *  解析json文件为String
     * @param file
     * @return
     */
    public  String parseJsonFileToString(File file){

        StringBuilder resultJson = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            resultJson = new StringBuilder();

            String lineTxt = bufferedReader.readLine();
            while (lineTxt != null) {
                resultJson.append(lineTxt);
                lineTxt = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultJson != null ? resultJson.toString() : null;
    }

    /**
     *  解析一个json字符串为想要的对象（jsonObject，JsonArray）
     * @param jsonStr               需要被转换的字符串
     * @param classType             转换之后的类
     * @param parseClassType        转换的类型jsonObject、jsonArray（可选可不选）
     * @return
     */
    public  Object parseJsonString(String jsonStr,Class<?> classType,Class<?> parseClassType){

        if (jsonStr != null && !jsonStr.equals("")){

            //类型推断
            if (parseClassType == null && classType == null){

                return parseJsonString(jsonStr);

            }else if (parseClassType != null && classType == null){

                return parseJsonString(jsonStr,parseClassType);

            }else if (parseClassType == null && classType != null){

                List<?> jsonArray = JSON.parseArray(jsonStr,classType);
                if (jsonArray != null){

                    return jsonArray;
                }
                Object jsonObject = JSON.parseObject(jsonStr,classType);
                if (jsonObject != null){
                    return jsonObject;
                }

            }else if (parseClassType != null && classType != null){

                if (parseClassType.getName() != null && parseClassType.getName().equals(JSONObject.class.getName())){

                    return JSON.parseObject(jsonStr,classType);
                }
                if (parseClassType.getName() != null && parseClassType.getName().equals(JSONArray.class.getName())){

                    return JSON.parseArray(jsonStr,classType);
                }

            }

        }
        return null;
    }

    /**
     * 将json字符串转为合理的jsonArray或JsonObject(手动指定)
     * @param jsonStr
     * @param parseClassType
     * @return
     */

    public Object parseJsonString(String jsonStr,Class<?> parseClassType){

        if (jsonStr != null && !"".equals(jsonStr)){

            if (parseClassType == null){return parseJsonString(jsonStr);}
            else {

                if (parseClassType.getName() != null && parseClassType.getName().equals(JSONArray.class.getName())){

                    return JSON.parseArray(jsonStr);
                }

                if (parseClassType.getName() != null && parseClassType.getName().equals(JSONObject.class.getName())){

                    return JSON.parseObject(jsonStr);
                }

            }
        }

        return null;
    }

    /**
     *  将json字符串转为合理的jsonArray或JsonObject(自动判断)
     * @param jsonStr
     * @return
     */
    public Object parseJsonString(String jsonStr){
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        if (jsonArray != null){

            return jsonArray;
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (jsonObject != null){
            return jsonObject;
        }
        return null;
    }

    /**
     *  将jsonArray转为list<map>形式
     * @param jsonArray
     * @return
     */
    public  List<Map<String,Object>> parseJsonArrayToListMap (JSONArray jsonArray){

        if (jsonArray == null){return null;}

        List<Map<String,Object>> resultMap = new ArrayList<>();


        for (Object object : jsonArray) {

            Map<String, Object> objectMap = parseJsonObjectToMap("", (JSONObject) object);
            resultMap.add(objectMap);
            //将标题头集合时刻保持最新最完整
            if (objectMap.keySet() != null && objectMap.keySet().size() > titles.length){titles = new String[]{};titles = objectMap.keySet().toArray(commonStrings);}

        }

        return resultMap;
    }

    /**
     *  将jsonObject转为map形式，其中如果还包含jsonObject，key值一直迭代到没有jsonObject那一层
     * @param jsonObject
     * @param parentKeyString 父类的jsonObject 中key值
     * @return
     */
    public  Map<String,Object> parseJsonObjectToMap(String parentKeyString,JSONObject jsonObject){


        Map<String,Object> jsonMap = new LinkedHashMap<>();
        if (jsonObject == null){return jsonMap;}

        for (String jsonKey : jsonObject.keySet()) {

            Object o = jsonObject.get(jsonKey);
            if (o == null){continue;}

            if (o instanceof JSONObject){
                jsonMap.putAll(parseJsonObjectToMap((parentKeyString + jsonKey + ".").toString(), (JSONObject) o));
            }
            else {
                if (o instanceof JSONArray){
                    jsonMap.put((parentKeyString + jsonKey + ".").toString(),o.toString());
                    //待拓展
                }
                else
                {
                    jsonMap.put(parentKeyString + jsonKey,o.toString());
                }
            }

        }

        return jsonMap;
    }



    public static void main(String[] args){

        ParseJson parseJson = new ParseJson();

        Object o = parseJson.parseJsonString(FileUtil.parse(new File("C:\\Users\\Administrator\\Desktop\\result_1.json"),"",0),JSONArray.class);

        List<Map<String, Object>> maps = parseJson.parseJsonArrayToListMap((JSONArray) o);

//        ExcelUtil.exportToLocal(titles,titles,maps,"f");
        CsvUtil.createCSVFile(maps,titles,titles,"ffffffff", CommonParam.CODE_UTF8,",");
    }
}
