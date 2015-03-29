package knixbilder;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class ImageServer extends HttpServlet {
    ThumbnailCreator thumb = new ThumbnailCreator();

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
            throws javax.servlet.ServletException, java.io.IOException {

        String imagename = req.getParameter("file");
        String type = req.getParameter("type");
        ServletContext cntx = getServletContext();
        resp.setContentType("image/jpeg");

        if (type.contains("thumb")) {
            GetImage("/bilder/thumb_", imagename, cntx, resp, 600);
        } else if (type.contains("medium")) {
            GetImage("/bilder/medium_", imagename, cntx, resp, 2000);
        } else if (type.contains("full")) {
            GetImage("/bilder/", imagename, cntx, resp, 0);
        } else {
            FileIO.getImage(resp, new File(cntx.getRealPath("/bilder/fail.jpg")));
        }
    }

    private void GetImage(String path, String imagename, ServletContext cntx, HttpServletResponse resp, int width) throws IOException {
        String filename = cntx.getRealPath(path + imagename);
        File file = new File(filename);

        if (!file.exists())
            thumb.createThumbnail(cntx.getRealPath("/bilder/" + imagename), filename, width);

        if (!file.isFile()) {
            file = new File(cntx.getRealPath("/bilder/fail.jpg"));
        }

        FileIO.getImage(resp, file);
    }
}
