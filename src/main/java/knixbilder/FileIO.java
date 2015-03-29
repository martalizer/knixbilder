package knixbilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

public class FileIO {

    public static String saveImage(InputStream in) throws FileNotFoundException, IOException {
        String generatedFileName = UUID.randomUUID().toString() + ".jpg";
        File imageDirectory = new File("/opt/tomcat7/webapps/ROOT/bilder/");

        if (!imageDirectory.exists())
            imageDirectory.mkdir();
        File filename = new File(imageDirectory, generatedFileName);

        FileOutputStream out = new FileOutputStream(filename);

        byte[] buffer = new byte[1024];
        int read;

        while ((read = in.read(buffer)) != -1)
            out.write(buffer, 0, read);

        out.close();
        return generatedFileName;
    }

    public static void getImage(HttpServletResponse resp, File file) throws IOException {
        resp.setContentLength((int) file.length());
        FileInputStream in = new FileInputStream(file);
        OutputStream out = resp.getOutputStream();
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();
    }
}