package comp3111.examsystem.tools;

import comp3111.examsystem.entity.Student;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database<T> {
    Class<T> entitySample;
    String tableName;
    String jsonFile;

    public Database(Class<T> entity) {
        entitySample = entity;
        tableName = entitySample.getSimpleName().toLowerCase();
        jsonFile = Paths.get("database", tableName + ".txt").toString();
        jsonFile = "/Users/Terry/COMP3111_Alpha/ExamSystemSkeleton/database/student.txt";

        File file = new File(jsonFile);
        System.out.println("File path: " + file.getAbsolutePath());

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Query database based on key
    public T queryByKey(String key) {
        List<String> slist = FileUtil.readFileByLines(jsonFile);

        T res = null;
        for (int i = 0; i < slist.size(); i++) {
            T t = txtToEntity(slist.get(i));
            Object tvalue = getValue(t, "id");
            if (tvalue.toString().equals(key)) {
                res = t;
                break;
            }
        }
        return res;
    }

    // Query database based on keys
    public List<T> queryByKeys(List<String> keys) {
        List<String> slist = FileUtil.readFileByLines(jsonFile);

        List<T> res = new ArrayList<>();
        for (int i = 0; i < slist.size(); i++) {
            T t = txtToEntity(slist.get(i));
            Object tvalue = getValue(t, "id");
            for (String key : keys) {
                if (tvalue.toString().equals(key)) {
                    res.add(t);
                    break;
                }
            }
        }
        return res;
    }

    // Query database based on field
    public List<T> queryByField(String fieldName, String fieldValue) {
        List<T> list = getAll();
        List<T> resList = new ArrayList<>();
        for (T e : list) {
            Object value = getValue(e, fieldName);
            if ((value == null && fieldValue != null) || (value != null && fieldValue == null) || !value.toString().equals(fieldValue)) {
                continue;
            }
            resList.add(e);
        }
        list.clear();
        list.addAll(resList);
        return list;
    }

    // Query database based on field, but fuzzy matching
    public List<T> queryFuzzyByField(String fieldName, String fieldValue) {
        List<T> list = getAll();
        List<T> resList = new ArrayList<>();
        for (T e : list) {
            Object value = getValue(e, fieldName);
            if (fieldValue == null || value.toString().contains(fieldValue)) {
                resList.add(e);
            }
        }
        list.clear();
        list.addAll(resList);
        return list;
    }

    // Query database based on entity
    public List<T> queryByEntity(T entity) {
        List<T> list = getAll();
        List<String> prolist = new ArrayList<>();
        Class<?> clazz = entitySample;
        while (true) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equals("id") && !field.getName().equals("dbutil")) {
                    Object obj = getValue(entity, field.getName());
                    if (obj != null && !obj.toString().isEmpty()) {
                        prolist.add(field.getName());
                    }
                }
            }
            if (clazz.equals(Entity.class)) {
                break;
            }
            else {
                clazz = clazz.getSuperclass();
            }
        }
        List<T> resList = new ArrayList<>();
        for (T e : list) {
            boolean flag = true;
            for (int i = 0; i < prolist.size(); i++) {
                String filterProp = prolist.get(i);
                String queryValue = getValue(entity, filterProp).toString();
                Object value = getValue(e, filterProp);
                if ((queryValue == null && value != null) || (queryValue != null && value == null) || !value.toString().equals(queryValue)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                resList.add(e);
            }
        }
        list.clear();
        list.addAll(resList);
        return list;
    }

    // Query all the data from database
    public List<T> getAll() {
        List<String> slist = FileUtil.readFileByLines(jsonFile);
        List<T> tlist = new ArrayList<>();
        for (int i = 0; i < slist.size(); i++) {
            tlist.add(txtToEntity(slist.get(i)));
        }
        return tlist;
    }

    // Join two table
    public List<T> join(List<T> list1, List<T> list2) {
        List<T> resList = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                Long id1 = (Long) getValue(list1.get(i), "id");
                Long id2 = (Long) getValue(list2.get(j), "id");
                if (id1.toString().equals(id2.toString())) {
                    resList.add(list1.get(i));
                    break;
                }
            }
        }
        return resList;
    }

    // Delete from database by key
    public void delByKey(String key) {
        List<T> tlist = getAll();
        for (int i = 0; i < tlist.size(); i++) {
            Object value = getValue(tlist.get(i), "id");
            if (value.toString().equals(key)) {
                tlist.remove(i);
                break;
            }
        }
        try {
            FileUtil.writeTxtFile(listToStr(tlist), new File(jsonFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete from database by field
    public void delByFiled(String fieldName, String fieldValue) {
        List<T> tlist = getAll();
        for (int i = 0; i < tlist.size(); i++) {
            Object value = getValue(tlist.get(i), fieldName);
            if (value.toString().equals(fieldValue)) {
                tlist.remove(i);
                break;
            }
        }
        try {
            FileUtil.writeTxtFile(listToStr(tlist), new File(jsonFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update database according the entity key
    public void update(T entity) {
        Long key1 = (Long) getValue(entity, "id");
        List<T> tlist = getAll();
        for (int i = 0; i < tlist.size(); i++) {
            Long key = (Long) getValue(tlist.get(i), "id");

            if (key.toString().equals(key1.toString())) {
                Class<?> clazz = entitySample;
                while (true) {
                    for (Field field : clazz.getDeclaredFields()) {
                        if (!field.getName().equals("id") && !field.getName().equals("dbutil")) {
                            Object o = getValue(entity, field.getName());
                            setValue(tlist.get(i), field.getName(), o);
                        }
                    }
                    if (clazz.equals(Entity.class)) {
                        break;
                    }
                    else {
                        clazz = clazz.getSuperclass();
                    }
                }
                break;
            }
        }
        try {
            FileUtil.writeTxtFile(listToStr(tlist), new File(jsonFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add data into database
    public void add(T entity) {
        setValue(entity, "id", System.currentTimeMillis());
        List<T> tlist = getAll();
        tlist.add(entity);
        try {
            FileUtil.writeTxtFile(listToStr(tlist), new File(jsonFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getValue(Object entity, String fieldName) {
        Object value;
        Class<?> clazz = entity.getClass();
        while (true) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(entity);
                break;
            }
            catch (NoSuchFieldException e) {
                if (clazz.equals(Object.class))
                    throw new RuntimeException(e);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            clazz = clazz.getSuperclass();
        }
        return value;
    }

    private void setValue(Object entity, String fieldName, Object fieldValue) {
        Class<?> clazz = entity.getClass();
        while (true) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(entity, fieldValue);
                break;
            }
            catch (NoSuchFieldException e) {
                if (clazz.equals(Object.class))
                    throw new RuntimeException(e);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            clazz = clazz.getSuperclass();
        }
    }

    private String listToStr(List<T> tlist) {
        StringBuilder sbf = new StringBuilder();
        for (T t : tlist) {
            sbf.append(entityToTxt(t)).append("\r\n");
        }
        return sbf.toString();
    }

    private T txtToEntity(String txt) {
        T t = null;
        try {
            t = entitySample.getConstructor().newInstance();
            String[] pros = txt.split(",");
            for (int i = 0; i < pros.length; i++) {
                String[] pro = pros[i].split(":");
                if (pro[0].equals("id")) {
                    setValue(t, pro[0], Long.valueOf(pro[1]));
                } else {
                    setValue(t, pro[0], pro[1]);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    private String entityToTxt(T t) {
        StringBuffer sbf = new StringBuffer();
        Class<?> clazz = entitySample;
        while (true) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equals("dbutil")) {
                    Object obj = getValue(t, field.getName());
                    if (obj != null && !obj.toString().isEmpty()) {
                        sbf.append(field.getName()).append(":").append(obj).append(",");
                    }
                }
            }
            if (clazz.equals(Entity.class)) {
                break;
            }
            else {
                clazz = clazz.getSuperclass();
            }
        }

        return sbf.toString();
    }
}
