/*
 * Copyright 2002-2015 by bafeimao.net, The umbrella Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bafeimao.umbrella.annotation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;
import java.io.FileInputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/1.
 *
 * @author gukaitong
 * @since 1.0
 */
@SupportedAnnotationTypes("net.bafeimao.umbrella.annotation.SupportQuery")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class SupportQueryProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String fqClassName = null;
        String className = null;
        String packageName = null;
        Map<String, VariableElement> fields = new HashMap<String, VariableElement>();
        Messager messager = processingEnv.getMessager();

        for (Element elem : roundEnv.getElementsAnnotatedWith(SupportQuery.class)) {
            if (elem.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) elem;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                messager.printMessage(Diagnostic.Kind.NOTE, "annotated class: " + classElement.getQualifiedName(), elem);

                fqClassName = classElement.getQualifiedName().toString();
                className = classElement.getSimpleName().toString();
                packageName = packageElement.getQualifiedName().toString();

                messager.printMessage(Kind.NOTE, "annotated class:" + elem.getSimpleName(), elem);

                for (Element e : classElement.getEnclosedElements()) {
                    if (e.getAnnotation(Index.class) != null) {
                        fields.put(e.getSimpleName().toString(), (VariableElement) e);
                        messager.printMessage(Kind.NOTE, "  annotated field:" + e.getSimpleName(), e);
                    }
                }

                if (fqClassName != null) {
                    try {
                        Properties props = new Properties();
                        props.load(new FileInputStream("c:/velocity.properties"));

                        messager.printMessage(Kind.NOTE, "velocity properties loaded:" + props.toString());

                        VelocityEngine ve = new VelocityEngine(props);
                        ve.init();

                        VelocityContext vc = new VelocityContext();
                        vc.put("className", className);
                        vc.put("packageName", packageName);
                        vc.put("fields", fields);

                        messager.printMessage(Kind.NOTE, "loading template ...");
                        Template vt = ve.getTemplate("c:/entity-index-attribute.vm");
                        messager.printMessage(Kind.NOTE, "velocity template loaded!");
                        // Write to file
                        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(fqClassName + "BeanInfo");
                        messager.printMessage(Diagnostic.Kind.NOTE, "creating source file: " + jfo.toUri());
                        Writer writer = jfo.openWriter();
                        messager.printMessage(Diagnostic.Kind.NOTE, "applying velocity template: " + vt.getName());
                        vt.merge(vc, writer);
                        writer.close();

                    } catch (Exception e) {
                        messager.printMessage(Kind.ERROR, "error: " + e.getCause());
                    }
                }
            }
        }

        return true;
    }

}
