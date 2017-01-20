/*
 */
package com.cleverfishsoftware.loadgenerator.payload;

import com.cleverfishsoftware.loadgenerator.PayloadGenerator;
import static com.google.common.io.Closeables.closeQuietly;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author peter
 */
public class FileSystemPayloadGenerator3 implements PayloadGenerator {

    private static FileSystemPayloadGenerator3 instance;
    private static File[] files;
    private static AtomicInteger pointer;
    public static final String NEWLINE = System.getProperty("line.separator");

    public FileSystemPayloadGenerator3(String fn) throws FileNotFoundException, IOException {
        File file = new File(fn);
        if (file.isFile()) {
            files = new File[]{file};
        } else {
            files = file.listFiles();
        }
        pointer = new AtomicInteger(-1);
    }

    @Override
    public String[] getPayload(int size) {
        StringBuilder builder = new StringBuilder(1024);
        BufferedReader br = null;
        if (pointer.get() >= files.length-1) {
            pointer.getAndSet(-1);
        }
        try {
            br = new BufferedReader(new FileReader(files[pointer.incrementAndGet()]));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append(NEWLINE);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            closeQuietly(br);
        }

        return new String[]{builder.toString()};
    }

    public static void main(String... args) throws IOException {

    }
}
