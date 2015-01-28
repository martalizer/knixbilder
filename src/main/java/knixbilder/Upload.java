package knixbilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by hedenberg on 13/01/15.
 */
@WebServlet(name = "Upload", value = "/upload")
@MultipartConfig
public class Upload extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        fileSaveManager fm = new fileSaveManager("/knixbilder/bilder/", "/knixbilder/users/");

        try {
            fm.createDirectories(request.getParameter("location"), request.getParameter("date"), request.getParameter("author"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> extractedFiles = fm.extractArchive(request.getPart("file"));

        response.getWriter().println(extractedFiles.size() + " files unpacked successfully.");
    }

    private void deleteTempFiles(String fileName, String path) {
        new File(path + fileName).delete();
    }

    private String getUniqueName(String fileName, String path) {
        File f = new File(path + fileName);

        if(f.exists() && !f.isDirectory()) {
            String generatedFileName = UUID.randomUUID().toString();
            return generatedFileName.substring(0, 4) + "_" + fileName;
        }
        else return fileName;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("upload.html");
    }

    private class fileSaveManager {
        public String location, date, author, outputPath, imageRoot, userRoot;

        public fileSaveManager(String imageRoot, String userRoot) {
            this.imageRoot = imageRoot;
            this.userRoot = userRoot;
        }

        public List<String> extractArchive(Part filePart) throws ServletException {
            String fileName = saveFile(filePart);
            List<String> files = extractFiles(outputPath + fileName, outputPath);
            deleteTempFiles(fileName, outputPath);

            return files;
        }

        private String saveFile(Part filePart) throws ServletException {
            String fileName = getFileName(filePart);
            fileName = getUniqueName(fileName, outputPath);
            System.out.println(Paths.get(outputPath, fileName));

            try {
                Files.copy(filePart.getInputStream(), Paths.get(outputPath, fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return fileName;
        }

        private boolean validate(String fileName) {
            return (fileName.endsWith(".JPG") || fileName.endsWith(".jpg")) && !fileName.startsWith(".");
        }

        private String getFileName(final Part part) {
            final String partHeader = part.getHeader("content-disposition");
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(
                            content.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
            return null;
        }

        private List<String> extractFiles(String path, String outputFolder) {
            List<String> filenames = new ArrayList<>();

            byte[] buffer = new byte[1024];

            try {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
                ZipEntry ze;

                while ((ze = zis.getNextEntry()) != null) {
                    String fileName = ze.getName();
                    if (ze.isDirectory()) {
                        continue;
                    }
                    fileName = new File(fileName).getName();

                    if(!validate(fileName)) {
                        continue;
                    }
// Debug
                    System.out.println(fileName);
// !Debug
                    filenames.add(fileName);
                    File newFile = new File(outputFolder + File.separator + fileName);
                    FileOutputStream fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zis.closeEntry();
                zis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return filenames;
        }

        public void createDirectories(String location, String date, String author) {
            this.location = location;
            this.date = date;
            this.author = author;
            outputPath = imageRoot + location + "/" + date + "/" + author + "/";

            new File(outputPath).mkdirs();
            new File(userRoot + this.author).mkdirs();
        }
    }
}
