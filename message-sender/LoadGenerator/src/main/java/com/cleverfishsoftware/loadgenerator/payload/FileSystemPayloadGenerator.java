/*
 */
package com.cleverfishsoftware.loadgenerator.payload;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author peter
 */
public class FileSystemPayloadGenerator implements PayloadGenerator {

    private final boolean isFile;
    private final boolean isDirectory;
    private MappedByteBuffer buffer;
    private final StringBuilder sb;

    private static FileSystemPayloadGenerator instance;

    public FileSystemPayloadGenerator(String fn) throws FileNotFoundException, IOException {
        File file = new File(fn);
        this.isFile = file.isFile();
        this.isDirectory = !this.isFile;
        if (isFile) {
            try (RandomAccessFile aFile = new RandomAccessFile(file, "r"); FileChannel inChannel = aFile.getChannel()) {
                buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
                buffer.load();
            }
            System.out.println("buffer is " + buffer.capacity() + " bytes long");
            sb = new StringBuilder(buffer.capacity());
        } else {
            throw new UnsupportedOperationException("this feature is currently unsupported");
        }
    }

    @Override
    public String[] getPayload(int size) {
        if (sb.length() == 0) {
            for (int i = 0; i < buffer.limit(); i++) {
                sb.append((char) buffer.get());
            }
        }
        return new String[]{sb.toString()};
    }

    public static void main(String... args) throws IOException {
        String fn = "/Users/peter/vagrant/server-perf/compression/estreaming-data/Input-1V_J0B_ATLPAR-3412-0008-0924-172400-266.json";
        FileSystemPayloadGenerator fspg = new FileSystemPayloadGenerator(fn);
        for (int i = 0; i < 5; i++) {
            System.out.println(fspg.getPayload(0)[0]);
        }
    }
}
