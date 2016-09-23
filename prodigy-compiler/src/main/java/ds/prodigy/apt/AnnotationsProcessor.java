package ds.prodigy.apt;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;

import static com.squareup.javapoet.JavaFile.builder;

@SupportedAnnotationTypes("ds.prodigy.Config")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
@SuppressWarnings("All")
public class AnnotationsProcessor extends AbstractProcessor {

    private final Logger logger = new Logger();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        logger.init(processingEnv);
        Utils.init(processingEnv);

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.forEach(it -> {
            logger.note(">>> annotation=%s", it.getQualifiedName().toString());
        });
        Optional<? extends TypeElement> configAnnotation =
                annotations.stream()
                           .filter(o -> o.getQualifiedName().contentEquals("ds.prodigy.Config"))
                           .findFirst();
        if (configAnnotation.isPresent()) {
            List<Config> configs = new ArrayList<>();
            for (Element element : roundEnv.getElementsAnnotatedWith(configAnnotation.get())) {
                logger.note(">>> element=%s", element.toString());
                Config config = new Config();
                config.presenter = element.toString();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : element.getAnnotationMirrors().get(0).getElementValues().entrySet()) {
                    String key = entry.getKey().getSimpleName().toString();
                    String value = entry.getValue().toString();
                    logger.note("key=%s value=%s", key, value);
                    if (key.equals("component"))
                        config.component = value;
                    else if (key.equals("layout"))
                        config.layout = value;
                }
                configs.add(config);

            }

            generate(configs);
        } else {
            logger.warn(null, ">>> annotations not found");
        }

        return true;
    }

    private void generate(List<Config> configs) {
        try {
            if (configs.isEmpty())
                return;

            CodeGenerator codeGenerator = new CodeGenerator(configs);
            TypeSpec generatedClass = codeGenerator.generateClass();

            JavaFile javaFile = builder("ds.prodigy.gen", generatedClass).build();
            javaFile.writeTo(Utils.filer);
        } catch (IOException e) {
            logger.error("Generator error: %s", e.getMessage());
        }

    }
}
