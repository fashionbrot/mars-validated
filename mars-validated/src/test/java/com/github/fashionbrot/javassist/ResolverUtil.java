package com.github.fashionbrot.javassist;

import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class ResolverUtil<T> {

    private static final Logger log = LoggerFactory.getLogger(ResolverUtil.class);

    /** The magic header that indicates a JAR (ZIP) file. */
    private static final byte[] JAR_MAGIC = { 'P', 'K', 3, 4 };


    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    public static List<String> list(String path) throws IOException {
        List<String> names = new ArrayList<>();
        for (URL url : getResources(path)) {
            names.addAll(list(url, path));
        }
        return names;
    }

    public static List<String> list(String path,ClassLoader classLoader) throws IOException {
        if (classLoader==null){
            classLoader = getClassLoader();
        }
        List<String> names = new ArrayList<>();
        for (URL url : getResources(path,classLoader)) {
            names.addAll(list(url, path));
        }
        return names;
    }

    public static List<URL> getResources(String path) throws IOException {
        return Collections.list(getClassLoader().getResources(path));
    }

    public static List<URL> getResources(String path,ClassLoader classLoader) throws IOException {
        return Collections.list(classLoader.getResources(path));
    }


    public static List<String> list(URL url, String path) throws IOException{
        InputStream is = null;
        try {
            List<String> resources = new ArrayList<>();

            URL jarUrl = findJarForResource(url);
            if (jarUrl != null) {
                is = jarUrl.openStream();
                if (log.isDebugEnabled()) {
                    log.debug("Listing " + url);
                }
                resources = listResources(new JarInputStream(is), path);
            } else {
                List<String> children = new ArrayList<>();
                try {
                    if (isJar(url)) {

                        is = url.openStream();
                        try (JarInputStream jarInput = new JarInputStream(is)) {
                            if (log.isDebugEnabled()) {
                                log.debug("Listing " + url);
                            }
                            for (JarEntry entry; (entry = jarInput.getNextJarEntry()) != null; ) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Jar entry: " + entry.getName());
                                }
                                children.add(entry.getName());
                            }
                        }
                    } else {
                        is = url.openStream();
                        List<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                            for (String line; (line = reader.readLine()) != null;) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Reader entry: " + line);
                                }
                                lines.add(line);
                                if (getResources(path + "/" + line).isEmpty()) {
                                    lines.clear();
                                    break;
                                }
                            }
                        } catch (InvalidPathException e) {
                            // #1974
                            lines.clear();
                        }
                        if (!lines.isEmpty()) {
                            if (log.isDebugEnabled()) {
                                log.debug("Listing " + url);
                            }
                            children.addAll(lines);
                        }
                    }
                } catch (FileNotFoundException e) {
                    if ("file".equals(url.getProtocol())) {
                        File file = new File(url.getFile());
                        if (log.isDebugEnabled()) {
                            log.debug("Listing directory " + file.getAbsolutePath());
                        }
                        if (file.isDirectory()) {
                            if (log.isDebugEnabled()) {
                                log.debug("Listing " + url);
                            }
                            children = Arrays.asList(file.list());
                        }
                    } else {
                        // No idea where the exception came from so rethrow it
                        throw e;
                    }
                }

                // The URL prefix to use when recursively listing child resources
                String prefix = url.toExternalForm();
                if (!prefix.endsWith("/")) {
                    prefix = prefix + "/";
                }
                // Iterate over immediate children, adding files and recurring into directories
                for (String child : children) {
                    String resourcePath = path + "/" + child;
                    resources.add(resourcePath);
                    URL childUrl = new URL(prefix + child);
                    resources.addAll(list(childUrl, resourcePath));
                }
            }

            return resources;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
    }

    protected static boolean isJar(URL url) {
        return isJar(url, new byte[JAR_MAGIC.length]);
    }

    protected static boolean isJar(URL url, byte[] buffer) {
        try (InputStream is = url.openStream()) {
            is.read(buffer, 0, JAR_MAGIC.length);
            if (Arrays.equals(buffer, JAR_MAGIC)) {
                if (log.isDebugEnabled()) {
                    log.debug("Found JAR: " + url);
                }
                return true;
            }
        } catch (Exception e) {
            // Failure to read the stream means this is not a JAR
        }

        return false;
    }

    public static URL findJarForResource(URL url) throws MalformedURLException {
        if (log.isDebugEnabled()) {
            log.debug("Find JAR URL: " + url);
        }

        // If the file part of the URL is itself a URL, then that URL probably points to the JAR
        boolean continueLoop = true;
        while (continueLoop) {
            try {
                url = new URL(url.getFile());
                if (log.isDebugEnabled()) {
                    log.debug("Inner URL: " + url);
                }
            } catch (MalformedURLException e) {
                // This will happen at some point and serves as a break in the loop
                continueLoop = false;
            }
        }

        // Look for the .jar extension and chop off everything after that
        StringBuilder jarUrl = new StringBuilder(url.toExternalForm());
        int index = jarUrl.lastIndexOf(".jar");
        if (index >= 0) {
            jarUrl.setLength(index + 4);
            if (log.isDebugEnabled()) {
                log.debug("Extracted JAR URL: " + jarUrl);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Not a JAR: " + jarUrl);
            }
            return null;
        }

        // Try to open and test it
        try {
            URL testUrl = new URL(jarUrl.toString());
            if (isJar(testUrl)) {
                return testUrl;
            } else {
                // WebLogic fix: check if the URL's file exists in the filesystem.
                if (log.isDebugEnabled()) {
                    log.debug("Not a JAR: " + jarUrl);
                }
                jarUrl.replace(0, jarUrl.length(), testUrl.getFile());
                File file = new File(jarUrl.toString());

                // File name might be URL-encoded
                if (!file.exists()) {
                    try {
                        file = new File(URLEncoder.encode(jarUrl.toString(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Unsupported encoding?  UTF-8?  That's impossible.");
                    }
                }

                if (file.exists()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Trying real file: " + file.getAbsolutePath());
                    }
                    testUrl = file.toURI().toURL();
                    if (isJar(testUrl)) {
                        return testUrl;
                    }
                }
            }
        } catch (MalformedURLException e) {
            log.warn("Invalid JAR URL: " + jarUrl);
        }

        if (log.isDebugEnabled()) {
            log.debug("Not a JAR: " + jarUrl);
        }
        return null;
    }

    public static List<String> listResources(JarInputStream jar, String path) throws IOException {
        // Include the leading and trailing slash when matching names
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        // Iterate over the entries and collect those that begin with the requested path
        List<String> resources = new ArrayList<>();
        for (JarEntry entry; (entry = jar.getNextJarEntry()) != null;) {
            if (!entry.isDirectory()) {
                // Add leading slash if it's missing
                StringBuilder name = new StringBuilder(entry.getName());
                if (name.charAt(0) != '/') {
                    name.insert(0, '/');
                }

                // Check file name
                if (name.indexOf(path) == 0) {
                    if (log.isDebugEnabled()) {
                        log.debug("Found resource: " + name);
                    }
                    // Trim leading slash
                    resources.add(name.substring(1));
                }
            }
        }
        return resources;
    }

    public static List<Class<?>> toClass(List<String> urlList){

        ClassLoader loader = getClassLoader();
        List<Class<?>> classList=new ArrayList<>(urlList.size());
        for (String clazz: urlList){
            if (clazz.endsWith(".class")) {

                try {
                    Class<?> type = loader.loadClass(clazz.replaceAll("\\.class","").replaceAll("/","."));;
                    if (type != null ){
                        classList.add(type);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return classList;
    }

    public static void main(String[] args) throws IOException {

        List<String> list = ResolverUtil.list("com/github/fashionbrot/validated/util");

        List<Class<?>> classList = ResolverUtil.toClass(list);
        if (ObjectUtil.isNotEmpty(classList)){
            classList.forEach(clazz->{
                Method[] declaredMethods = clazz.getDeclaredMethods();
                if (ObjectUtil.isNotEmpty(declaredMethods)){
                    Arrays.stream(declaredMethods).forEach(method -> {
                        if (method.getDeclaredAnnotation(Validated.class)!=null){
                            System.out.println(method.getName());
                        }
                    });
                }
            });
        }


    }

}
