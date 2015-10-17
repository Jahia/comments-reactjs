package org.jahia.javaone.modules.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by sajid_momin on 8/3/15.
 */
public class FileHelper {

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String loadFileContent(final String url) throws IOException {
        final InputStream inputStream = new URL(url).openStream();
        final String content =  IOUtils.toString(inputStream);
        IOUtils.closeQuietly(inputStream);
        return content;
    }

}
