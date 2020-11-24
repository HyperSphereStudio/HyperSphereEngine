package Utilities.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ReverseLineInputStream extends InputStream {

    private static final int MAX_LINE_BYTES = 1024 * 1024;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;
    private RandomAccessFile in;
    private long currentFilePos;
    private int bufferSize;
    private byte[] reader;
    private byte[] buffer;
    private int currentBufferPos;
    private int maxLineBytes;
    private byte[] currentLine;
    private int currentLineWritePos = 0;
    private int currentLineReadPos = 0;
    private boolean lineBuffered = false, finished;

    public ReverseLineInputStream(String path) throws IOException {
        this(new File(path));
    }

    public ReverseLineInputStream(File file) throws IOException {
        this(file, DEFAULT_BUFFER_SIZE, MAX_LINE_BYTES);
    }

    public ReverseLineInputStream(File file, int bufferSize, int maxLineBytes) throws IOException {
        this.maxLineBytes = maxLineBytes;
        in = new RandomAccessFile(file, "r");
        currentFilePos = file.length() - 1;
        in.seek(currentFilePos);
        if (in.readByte() == 0xA) {
            currentFilePos--;
        }
        currentLine = new byte[maxLineBytes];
        currentLine[0] = 0xA;

        this.bufferSize = bufferSize;
        buffer = new byte[bufferSize];
        fillBuffer();
        fillLineBuffer();

        reader = new byte[currentLine.length];
    }

    public boolean hasMore(){
        return !finished;
    }

    public String readLine() throws IOException {
        if(finished)return null;
        byte b;
        for(int i = 0; i < currentLineWritePos; ++i){
            b = (byte)read();
            if(b == -1)return new String(Arrays.copyOfRange(reader, 0, i), StandardCharsets.UTF_8);;
            reader[i] = b;
        }

        return new String(reader, StandardCharsets.UTF_8);
    }

    @Override
    public int read() throws IOException {
        if (currentFilePos <= 0 && currentBufferPos < 0 && currentLineReadPos < 0) {
            finished = true;
            return -1;
        }

        if (!lineBuffered) {
            fillLineBuffer();
        }

        if (lineBuffered) {
            if (currentLineReadPos == 0) {
                lineBuffered = false;
            }
            return currentLine[currentLineReadPos--];
        }
        return 0;
    }

    private void fillBuffer() throws IOException {
        if (currentFilePos < 0) {
            return;
        }

        if (currentFilePos < bufferSize) {
            in.seek(0);
            buffer = new byte[(int) currentFilePos + 1];
            in.readFully(buffer);
            currentBufferPos = (int) currentFilePos;
            currentFilePos = -1;
        } else {
            in.seek(currentFilePos - buffer.length);
            in.readFully(buffer);
            currentBufferPos = bufferSize - 1;
            currentFilePos = currentFilePos - bufferSize;
        }
    }

    private void fillLineBuffer() throws IOException {
        currentLineWritePos = 1;
        while (true) {

            // we've read all the buffer - need to fill it again
            if (currentBufferPos < 0) {
                fillBuffer();

                // nothing was buffered - we reached the beginning of a file
                if (currentBufferPos < 0) {
                    currentLineReadPos = currentLineWritePos - 1;
                    lineBuffered = true;
                    return;
                }
            }

            byte b = buffer[currentBufferPos--];

            if (b == 0xA) {
                currentLineReadPos = currentLineWritePos - 1;
                lineBuffered = true;
                break;
            } else {
                if (currentLineWritePos == maxLineBytes) {
                    throw new IOException("file has a line exceeding " + maxLineBytes
                            + " bytes; use constructor to pickup bigger line buffer");
                }


                currentLine[currentLineWritePos++] = b;
            }
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        in.close();
    }
}