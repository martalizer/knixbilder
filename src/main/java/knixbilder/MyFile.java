package knixbilder;

/**
 * Created by hedenberg on 19/01/15.
 */
public class MyFile {
    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }

    private String File;

    public String getAbsoluteFile() {
        return absoluteFile;
    }

    public void setAbsoluteFile(String absoluteFile) {
        this.absoluteFile = absoluteFile;
    }

    private String absoluteFile;
    private String thumbFile;

    public MyFile(String s, String s2, String s3) {
        File = s2;
        absoluteFile = s;
        thumbFile = s3;
    }

    public String getThumbFile() {
        return thumbFile;
    }

    public void setThumbFile(String thumbFile) {
        this.thumbFile = thumbFile;
    }
}
