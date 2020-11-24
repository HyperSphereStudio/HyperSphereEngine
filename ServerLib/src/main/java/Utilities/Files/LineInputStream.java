package Utilities.Files;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LineInputStream {
    private BufferedReader br;
    private String string;
    private boolean readString, finished;

    public LineInputStream(String path) throws IOException {
        this(new File(path));
    }

    public LineInputStream(File file) throws IOException {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    }

    public String readLine() throws IOException {
        if (readString) {
            readString = false;
            return string;
        }
        if (finished) return null;
        return br.readLine();
    }

    public boolean hasMore() throws IOException {
        if (!readString) {
            string = br.readLine();
            readString = true;
        }

        if (string == null) {
            finished = true;
            return false;
        } else return true;
    }

    public void close() throws IOException {
        br.close();
    }
}
