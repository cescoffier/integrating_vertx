package com.jclarity.safepoint.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SafepointLogFile implements DataSource<String> {

    protected Path path;
    protected final LogSourceFormatMetaData metadata;

    public SafepointLogFile(Path path) {
        this.path = path;
        this.metadata = new LogSourceFormatMetaData(path);
    }

    @Override
    public Stream<String> stream() throws IOException {
        if ( metadata.isFile()) {
            return Files.lines(path);
        } else if ( metadata.isZip()) {
            return streamZipFile();
        } else if ( metadata.isGZip()) {
            return streamGZipFile();
        }
        throw new IOException("Unable to read " + path.toString());
    }

    private Stream<String> streamZipFile() throws IOException {
        ZipInputStream zipStream = new ZipInputStream(Files.newInputStream(path));
        ZipEntry entry;
        do {
            entry = zipStream.getNextEntry();
        } while (entry.isDirectory());
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(zipStream))).lines();
    }

    private Stream<String> streamGZipFile() throws IOException {
        GZIPInputStream gzipStream = new GZIPInputStream(Files.newInputStream(path));
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(gzipStream))).lines();
    }
}
