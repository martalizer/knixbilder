package knixbilder;

/**
 * Created by hedenberg on 19/01/15.
 */
public class MyFile {
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAbsoluteFile() {
        return absoluteFile;
    }

    public void setAbsoluteFile(String absoluteFile) {
        this.absoluteFile = absoluteFile;
    }

    private String file;
    private String absoluteFile;

    public MyFile(String s, String s2) {
        file = s2;
        absoluteFile = s;
    }
}
