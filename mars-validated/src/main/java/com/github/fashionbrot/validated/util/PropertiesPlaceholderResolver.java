package com.github.fashionbrot.validated.util;

import org.springframework.core.env.PropertyResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * Placeholder Resolver for {@link Properties properties}
 */
public class PropertiesPlaceholderResolver {

    private final PropertyResolver propertyResolver;

    PropertiesPlaceholderResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }





    /**
     * Resolve placeholders in specified {@link Map properties}
     *
     * @param properties {@link Map source properties}
     * @return Resolved {@link Properties source properties}
     */
    Properties resolve(Map<?, ?> properties) {
        Properties resolvedProperties = new Properties();
        for (Map.Entry<?, ?> entry : properties.entrySet()) {
            if (entry.getValue() instanceof CharSequence) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                String resolvedValue = propertyResolver.resolvePlaceholders(value);
                if (StringUtil.isNotEmpty(resolvedValue)) { // set properties if has test
                    resolvedProperties.setProperty(key, resolvedValue);
                }
            }
        }
        return resolvedProperties;
    }


    /**
     * Retrieve the given annotation's attributes as a Map, preserving all attribute types
     * as-is.
     * <p>Note: As of Spring 3.1.1, the returned map is actually an
     * {@link AnnotationAttributes} instance, however the Map signature of this method has
     * been preserved for binary compatibility.
     * @param annotation the annotation to retrieve the attributes for
     * @return the Map of annotation attributes, with attribute names as keys and
     * corresponding attribute values as values
     */
    public static Map<String, Object> getAnnotationAttributes(Annotation annotation) {
        return getAnnotationAttributes(annotation, false, false);
    }

    /**
     * Retrieve the given annotation's attributes as an {@link AnnotationAttributes}
     * map structure. Implemented in Spring 3.1.1 to provide fully recursive annotation
     * reading capabilities on par with that of the reflection-based
     * {@link org.springframework.core.type.StandardAnnotationMetadata}.
     * @param annotation the annotation to retrieve the attributes for
     * @param classValuesAsString whether to turn Class references into Strings (for
     * compatibility with {@link org.springframework.core.type.AnnotationMetadata} or to
     * preserve them as Class references
     * @param nestedAnnotationsAsMap whether to turn nested Annotation instances into
     * {@link AnnotationAttributes} maps (for compatibility with
     * {@link org.springframework.core.type.AnnotationMetadata} or to preserve them as
     * Annotation instances
     * @return the annotation attributes (a specialized Map) with attribute names as keys
     * and corresponding attribute values as values
     * @since 3.1.1
     */
    public static AnnotationAttributes getAnnotationAttributes(Annotation annotation, boolean classValuesAsString,
                                                               boolean nestedAnnotationsAsMap) {

        AnnotationAttributes attrs = new AnnotationAttributes();
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                try {
                    Object value = method.invoke(annotation);
                    if (classValuesAsString) {
                        if (value instanceof Class) {
                            value = ((Class<?>) value).getName();
                        }
                        else if (value instanceof Class[]) {
                            Class<?>[] clazzArray = (Class[]) value;
                            String[] newValue = new String[clazzArray.length];
                            for (int i = 0; i < clazzArray.length; i++) {
                                newValue[i] = clazzArray[i].getName();
                            }
                            value = newValue;
                        }
                    }
                    if (nestedAnnotationsAsMap && value instanceof Annotation) {
                        attrs.put(method.getName(),
                                getAnnotationAttributes((Annotation) value, classValuesAsString, nestedAnnotationsAsMap));
                    }
                    else if (nestedAnnotationsAsMap && value instanceof Annotation[]) {
                        Annotation[] realAnnotations = (Annotation[]) value;
                        AnnotationAttributes[] mappedAnnotations = new AnnotationAttributes[realAnnotations.length];
                        for (int i = 0; i < realAnnotations.length; i++) {
                            mappedAnnotations[i] = getAnnotationAttributes(
                                    realAnnotations[i], classValuesAsString, nestedAnnotationsAsMap);
                        }
                        attrs.put(method.getName(), mappedAnnotations);
                    }
                    else {
                        attrs.put(method.getName(), value);
                    }
                }
                catch (Exception ex) {
                    throw new IllegalStateException("Could not obtain annotation attribute values", ex);
                }
            }
        }
        return attrs;
    }

}