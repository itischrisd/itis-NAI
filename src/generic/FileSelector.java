package generic;

import java.io.File;
import java.util.Scanner;

public class FileSelector {

    public static String getFilePath(String filePath) {
        if (!filePath.isEmpty())
            return filePath;

        File file;
        do {
            System.out.print("Enter a valid data set file path: ");
            Scanner scanner = new Scanner(System.in);
            file = new File(scanner.nextLine());
        } while(!file.exists());

        return file.getPath();
    }
}
