package knixbilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    ThumbnailCreator thumb = new ThumbnailCreator();
    private volatile List<MyFile> links = new ArrayList<>();
    private volatile List<MyFile> images = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentLocation = URLDecoder.decode(request.getRequestURI(), "UTF-8");

            if (currentLocation.endsWith("/") || currentLocation.endsWith(".jpg") || currentLocation.endsWith(".JPG")) {
                serveFile(request, response);
            } else {
                showFileLinks(request, response, currentLocation);
            }
    }

    private void showFileLinks(HttpServletRequest request, HttpServletResponse response, String currentLocation) throws IOException, ServletException {
        if (currentLocation.endsWith("index.html"))
            currentLocation = "/knixbilder/images";

        File[] files;

        if (request.getPathInfo() != null) {
            files = new File(imageRoot + "/" + URLDecoder.decode(request.getPathInfo(), "UTF-8")).listFiles();
        } else {
            files = new File(imageRoot).listFiles();
        }

        links = new ArrayList<>();
        images = new ArrayList<>();

        for (File f : files) {
            if (!f.getName().startsWith(".")) {
                if (f.getName().startsWith("thumb_") && (f.getName().endsWith(".jpg") || f.getName().endsWith(".JPG") || f.getName().endsWith(".jpeg"))) {
                    images.add(new MyFile(currentLocation + "/" + f.getName().substring("_thumb".length()), f.getName() ,currentLocation + "/" + f.getName()));
                } else if (f.getName().endsWith(".jpg") || f.getName().endsWith(".JPG") || f.getName().endsWith(".jpeg"))
                {} else {
                    links.add(new MyFile(currentLocation + "/" + f.getName(), f.getName(),f.getName()));
                }
            }
        }

        String location = currentLocation.substring("/knixbilder/images".length());
        if (location.length() == 0)
            location = "/";

        request.setAttribute("links", links);
        request.setAttribute("location", location);
        request.setAttribute("images", images);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void serveFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File srcFile = new File(imageRoot + "/" + URLDecoder.decode(request.getPathInfo(), "UTF-8"));
        response.setContentLength((int) srcFile.length());
        Files.copy(srcFile.toPath(), response.getOutputStream());
    }
}
