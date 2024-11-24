package comp3111.examsystem.tools;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Utility class for handling file operations, including reading from and writing to text files.
 * This class provides static methods to facilitate file I/O operations in a convenient manner.
 */
public class FileUtil {
    /**
     * Writes the specified content to a text file.
     *
     * @param content  the content to write to the file
     * @param fileName the file to which the content will be written
     * @return true if the content was successfully written, false otherwise
     * @throws Exception if an error occurs during file writing
     */
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
    /**
     * Reads the contents of a text file line by line and returns them as a list of strings.
     *
     * @param fileName the name of the file to read
     * @return a list of strings representing the lines of the file
     */
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
