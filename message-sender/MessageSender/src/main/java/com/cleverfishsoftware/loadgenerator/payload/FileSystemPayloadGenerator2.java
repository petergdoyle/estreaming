/*
 */
package com.cleverfishsoftware.loadgenerator.payload;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class FileSystemPayloadGenerator2 implements PayloadGenerator {

    private final MappedByteBuffer[] buffer;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    private static FileSystemPayloadGenerator2 instance;

    public FileSystemPayloadGenerator2(String fn) throws FileNotFoundException, IOException {
        File file = new File(fn);
        if (file.isFile()) {
            buffer = new MappedByteBuffer[1];
            loadFileIntoMemory(file, 0);
        } else {
            File[] files = file.listFiles();
            buffer = new MappedByteBuffer[files.length];
            for (int i = 0; i < files.length; i++) {
                loadFileIntoMemory(files[i], i);
            }
        }
    }

    private void loadFileIntoMemory(File file, int index) throws IOException {
        try (RandomAccessFile aFile = new RandomAccessFile(file, "r"); FileChannel inChannel = aFile.getChannel()) {
            MappedByteBuffer b = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            b.load();
            buffer[index] = b;
        }
    }

    @Override
    public String[] getPayload(int size) {
        int i = currentIndex.getAndIncrement();
        if (currentIndex.get() > buffer.length) {
            i = 0;
        }
        ByteBuffer b = buffer[currentIndex.getAndIncrement()];
        StringBuilder sb = new StringBuilder(b.capacity());
        if (sb.length() == 0) {
            for (int c = 0; i < b.limit(); c++) {
                sb.append((char) b.get());
            }
        }
        return new String[]{sb.toString()};
    }

    public static void main(String... args) throws IOException {
        String fn = "/Users/peter/vagrant/server-perf/compression/estreaming-data/Input-1V_J0B_ATLPAR-3412-0008-0924-172400-266.json";
        FileSystemPayloadGenerator2 fspg = new FileSystemPayloadGenerator2(fn);
        for (int i = 0; i < 5; i++) {
            System.out.println(fspg.getPayload(0)[0]);
        }
    }
}
