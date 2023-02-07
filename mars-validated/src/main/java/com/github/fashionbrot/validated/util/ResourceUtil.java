package com.github.fashionbrot.validated.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fashionbrot
 */
public class ResourceUtil {

    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    public static InputStream getResourceAsStream(String resource)  {
        try {
            return getResourceAsStream(null, resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Returns a resource on the classpath as a Stream object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return in;
    }


    /*
     * Returns a resource on the classpath as a Properties object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Properties getResourceAsProperties(String resource){
        Properties props = new Properties();
        InputStream in = getResourceAsStream(resource);
        try {
            props.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

}
