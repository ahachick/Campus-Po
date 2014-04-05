package com.campuspo.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	/**
     * 将json对象转换为java对象的工具类
     *
     * @param entityName
     *            类的完全限定名 (例:com.sura.entity.SuraStoreroomAssistStockinfo)
     * @param object
     *            需要转换的json对象(只可以为JSONObject对象不能使JSONArry对象)
     * @return 返回对应的java实体
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ParseException
     */
    public Object getNewObj(String entityName, JSONObject object)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, ParseException {
        /*Class cl = Class.forName(entityName);
        Object obj = cl.newInstance();
        Field[] fds = cl.getDeclaredFields();
        for (Field fd : fds) {
            if (!fd.isAccessible()) {
                fd.setAccessible(true);
            }
            Set<String> setref = object.keySet();
            for (String keyref : setref) {
                // System.out.println(key);
                if (keyref.indexOf(".") != -1) {

                    String key = keyref.split("\\.")[1];
                    if (fd.getName().equals(key)) {

                        String value = object.get(keyref).toString();
                        if (fd.getType().equals(Long.class)) {
                            if (!value.equals("") && !value.equals("0")) {
                                fd.set(obj, Long.parseLong(value));
                            }
                        } else if (fd.getType().equals(String.class)) {
                            fd.set(obj, value);
                        } else if (fd.getType().equals(Integer.class)) {
                            if (!value.equals("") && !value.equals("0")) {
                                fd.set(obj, Integer.parseInt(value));
                            }
                        } else if (fd.getType().equals(Double.class)) {
                            if (!value.equals("") && !value.equals("0")) {
                            fd.set(obj, Double.parseDouble(value));
                            }
                        } else if (fd.getType().equals(Timestamp.class)) {
                            if (!value.equals("") && !value.equals("null")) {
                                Date zhxhrq = null;
                                if (value.indexOf(" ") == -1) {
                                    zhxhrq = this.getsdf().parse(
                                            value + " 00:00:00");
                                } else {
                                    zhxhrq = this.getsdf().parse(value);
                                }
                                fd.set(obj,
                                        Timestamp.valueOf(this.getsdf().format(
                                                zhxhrq)));
                            }
                        } else if (fd.getType().equals(Date.class)) {
                            if (!value.equals("") && !value.equals("null")) {
                                Date zhxhrq = null;
                                if (value.indexOf(" ") == -1) {
                                    zhxhrq = this.getsdf().parse(
                                            value + " 00:00:00");
                                    System.out.println(zhxhrq);
                                } else {
                                    zhxhrq = this.getsdf().parse(value);
                                    System.out.println(zhxhrq);
                                }
                                fd.set(obj, zhxhrq);
                            }
                        }
                    }
                } else {
                    String key = keyref.trim();
                    if (fd.getName().equals(key)) {

                        String value = object.get(keyref).toString();
                        if (fd.getType().equals(Long.class)) {
                            if (!value.equals("") && !value.equals("0")) {
                                fd.set(obj, Long.parseLong(value));
                            }
                        } else if (fd.getType().equals(String.class)) {
                            fd.set(obj, value);
                        } else if (fd.getType().equals(Integer.class)) {
                            if (!value.equals("") && !value.equals("0")) {
                                fd.set(obj, Integer.parseInt(value));
                            }
                        } else if (fd.getType().equals(Double.class)) {
                            if (!value.equals("") && !value.equals("0")) {
                            fd.set(obj, Double.parseDouble(value));
                            }
                        } else if (fd.getType().equals(Timestamp.class)) {
                            if (!value.equals("") && !value.equals("null")) {
                                Date zhxhrq = null;
                                if (value.indexOf(" ") == -1) {
                                    zhxhrq = this.getsdf().parse(
                                            value + " 00:00:00");
                                } else {
                                    zhxhrq = this.getsdf().parse(value);
                                }
                                fd.set(obj,
                                        Timestamp.valueOf(this.getsdf().format(
                                                zhxhrq)));
                            }
                        } else if (fd.getType().equals(Date.class)) {
                            System.out.println(value);
                            if (!value.equals("") && !value.equals("null")) {
                                Date zhxhrq = null;
                                if (value.indexOf(" ") == -1) {
                                    zhxhrq = this.getsdf().parse(
                                            value + " 00:00:00");

                                } else {
                                    zhxhrq = this.getsdf().parse(value);
                                }
                                fd.set(obj, zhxhrq);
                            }
                        }
                    }
                }
            }
        }*/
        return null;
    }
    
    public static Object toEntity(String className, JSONObject jsonObject ) throws InstantiationException, IllegalAccessException, JSONException, ClassNotFoundException {
    	
    	Class<?> clz = Class.forName(className);
    	Object entity = clz.newInstance();
    	Field[] fields = clz.getDeclaredFields();
    	for(Field fd : fields) {
    		//判断属性是否可见，若不可见，刚设为可见
    		if(!fd.isAccessible()) 
    			fd.setAccessible(true);
    		
    		for(Iterator<?> iterator = jsonObject.keys(); iterator.hasNext();) {
    			String key = (String) iterator.next();
    			if(fd.getName().equals(key)) {
    				
    				String value = jsonObject.get(key).toString();
    				
    				if(fd.getType().equals(String.class))
    					fd.set(entity, value);
    				else if(fd.getType().equals(String[].class)) {
    					JSONArray array = new JSONArray(value);
    					String[] stringArray = new String[array.length()];
    					for(int i = 0; i < array.length(); i++)
    						stringArray[i] = array.getString(i);
    					fd.set(entity, stringArray);
    				}
    				else if (fd.getType().equals(Integer.class)){
    					if(!value.equals("")) 
    						fd.set(entity, Integer.parseInt(value));
    				}else if (fd.getType().equals(Long.class)) {
    					if(!value.equals(""))
    						fd.set(entity, Long.parseLong(value));
    				}else if(fd.getType().equals(Double.class)) {
    					if(!value.equals(""))
    						fd.set(entity, Double.parseDouble(value));
    				} 				
    			}
    		}
    	}
    	return entity;
    }
}
