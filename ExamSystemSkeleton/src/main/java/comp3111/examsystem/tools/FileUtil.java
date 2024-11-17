package comp3111.examsystem.tools;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static boolean writeTxtFile(String content, File fileName) throws Exception {
        boolean flag = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.getBytes("UTF-8"));
            fileOutputStream.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static List<String> readFileByLines(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }
}
