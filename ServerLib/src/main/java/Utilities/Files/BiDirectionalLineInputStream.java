package Utilities.Files;
import java.io.File;
import java.io.IOException;

public class BiDirectionalLineInputStream {

    public ReverseLineInputStream reverseLineInputStream;
    public LineInputStream lineInputStream;
    public boolean endToBeg;

    public BiDirectionalLineInputStream(String path, boolean endToBeg) throws IOException {
        this(new File(path), endToBeg);
    }

    public BiDirectionalLineInputStream(File file, boolean endToBeg) throws IOException {
        this.endToBeg = endToBeg;
        if(endToBeg){
            reverseLineInputStream = new ReverseLineInputStream(file);
        }else{
            lineInputStream = new LineInputStream(file);
        }
    }

    public String readLine() throws IOException {
        if(endToBeg){
            return reverseLineInputStream.readLine();
        }else{
            return lineInputStream.readLine();
        }
    }

    public boolean hasMore() throws IOException {
        if(endToBeg){
            return reverseLineInputStream.hasMore();
        }else{
            return lineInputStream.hasMore();
        }
    }

    public void close() throws IOException {
        if(endToBeg){
            reverseLineInputStream.close();
        }else{
            lineInputStream.close();
        }
    }

}
