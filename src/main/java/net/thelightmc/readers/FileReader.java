package net.thelightmc.readers;

import java.io.File;
import java.io.IOException;

public interface FileReader<E> {
    E read();
    File getFile() throws IOException;
}
