package knixbilder;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ThumbnailCreator {

    public void createThumbnail(String inputPath, String outputPath, int longside) {
        try {
            BufferedImage read = ImageIO.read(new File(inputPath));
            int thumb_height, thumb_width;

            if (read.getWidth() > read.getHeight()) {
                thumb_width = longside;
                float ratio = (float) read.getHeight() / (float) read.getWidth();
                thumb_height = (int) (ratio * longside);
            } else {
                thumb_height = longside;
                float ratio = (float) read.getWidth() / (float) read.getHeight();
                thumb_width = (int) (ratio * longside);
            }

            writeToDisk(outputPath, read, thumb_height, thumb_width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToDisk(String outputPath, BufferedImage read, int thumb_height, int thumb_width)
            throws IOException {
        BufferedImage bufferedImage = new BufferedImage(thumb_width, thumb_height, read.getType());
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(read, 0, 0, thumb_width, thumb_height, null);
        graphics.dispose();
        ImageIO.write(bufferedImage, "jpg", new File(outputPath));
    }
}