package knixbilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
@WebServlet(urlPatterns = {"/index.html/*", "/images/*"})
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String imageRoot = "/knixbilder/bilder/";
    private static String userRoot = "/knixbilder/users/";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentLocation = URLDecoder.decode(request.getRequestURI(), "UTF-8");

        if(currentLocation.endsWith("/") || currentLocation.endsWith(".jpg") || currentLocation.endsWith(".JPG")){
            showImage(request, response);
        }
        else {
            showFileLinks(request, response, currentLocation);
        }
    }

    private void showFileLinks(HttpServletRequest request, HttpServletResponse response, String currentLocation) throws IOException, ServletException {
        if(currentLocation.endsWith("index.html"))
            currentLocation = "/knixbilder/images";

        File[] files;

        if (request.getPathInfo() != null) {
            files = new File(imageRoot + "/" + URLDecoder.decode(request.getPathInfo(), "UTF-8")).listFiles();
        } else {
            files = new File(imageRoot).listFiles();
        }

        List<MyFile> folders = new ArrayList<>();
        List<MyFile> images = new ArrayList<>();

        for (File f : files) {
            if (!f.getName().startsWith(".")) {
                folders.add(new MyFile(currentLocation + "/" + f.getName(), f.getName()));
                if (f.getName().endsWith(".jpg") || f.getName().endsWith(".JPG") || f.getName().endsWith(".jpeg")) {
                   images.add(new MyFile(currentLocation + "/" + f.getName(), f.getName()));
                }
            }
        }

        request.setAttribute("folders", folders);
        request.setAttribute("images", images);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void showImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File srcFile = new File(imageRoot + "/" + URLDecoder.decode(request.getPathInfo(), "UTF-8"));

        Files.copy(srcFile.toPath(), response.getOutputStream());
    }
}
