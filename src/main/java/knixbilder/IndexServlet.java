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
@WebServlet("/index.html/*")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String imageRoot = "/knixbilder/bilder/";
    private static String userRoot = "/knixbilder/users/";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentLocation = request.getRequestURI();

        currentLocation = URLDecoder.decode(currentLocation, "UTF-8");

        if (currentLocation.endsWith("/") || currentLocation.endsWith(".jpg") || currentLocation.endsWith(".JPG")) {
            File srcFile = new File(imageRoot + "/" + URLDecoder.decode(request.getPathInfo(), "UTF-8"));
            Files.copy(srcFile.toPath(), response.getOutputStream());
        }
        else {
            File[] files;

            if (request.getPathInfo() != null) {
                files = new File(imageRoot + "/" + URLDecoder.decode(request.getPathInfo(), "UTF-8")).listFiles();
            } else {
                files = new File(imageRoot).listFiles();
            }

            List<MyFile> folders = new ArrayList<>();

            for (File f : files) {
                if (!f.getName().startsWith(".")) {
                    folders.add(new MyFile(currentLocation + "/" + f.getName(), f.getName()));
                }
            }

            request.setAttribute("folders", folders);
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }
    }
}
