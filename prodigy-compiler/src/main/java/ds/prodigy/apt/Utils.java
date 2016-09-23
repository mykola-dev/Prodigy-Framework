package ds.prodigy.apt;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

final class Utils {

    public static final TypeName STRING = ClassName.get("java.lang", "String");

    public static Types typeUtils;
    public static Elements elementUtils;
    public static Filer filer;


    public static void init(final ProcessingEnvironment processingEnv) {
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }


    public static String getPackageName(TypeElement type) {
        PackageElement pkg = elementUtils.getPackageOf(type);
        return pkg.getQualifiedName().toString();
    }


    public static String getClassName(TypeElement type) {
        return type.getSimpleName().toString();
    }


    public static String getFieldSimpleType(final Element e) {
        final TypeMirror tm = e.asType();
        TypeElement te = (TypeElement) typeUtils.asElement(tm);
        if (te == null)   // primitive
            return TypeName.get(tm).toString();

        final ClassName cn = ClassName.get(te);
        return cn.simpleName();
    }

    private static <T> T findAnnotationValue(Element element, String annotationClass, String valueName, Class<T> expectedType) {
        T ret = null;
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            TypeElement annotationElement = (TypeElement) annotationType.asElement();
            if (annotationElement.getQualifiedName().contentEquals(annotationClass)) {
                ret = extractValue(annotationMirror, valueName, expectedType);
                break;
            }
        }
        return ret;
    }

    private static <T> T extractValue(AnnotationMirror annotationMirror, String valueName, Class<T> expectedType) {
        Map<ExecutableElement, AnnotationValue> elementValues = new HashMap<>(annotationMirror.getElementValues());
        for (Map.Entry<ExecutableElement, AnnotationValue> entry : elementValues.entrySet()) {
            if (entry.getKey().getSimpleName().contentEquals(valueName)) {
                Object value = entry.getValue().getValue();
                return expectedType.cast(value);
            }
        }
        return null;
    }

}