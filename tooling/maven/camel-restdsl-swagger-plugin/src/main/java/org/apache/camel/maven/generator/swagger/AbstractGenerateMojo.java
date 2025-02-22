/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.maven.generator.swagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.camel.generator.swagger.DestinationGenerator;
import org.apache.camel.util.IOHelper;
import org.apache.camel.util.ObjectHelper;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

@Deprecated
abstract class AbstractGenerateMojo extends AbstractMojo {

    // this list should be in priority order
    public static final String[] DEFAULT_REST_CONSUMER_COMPONENTS = new String[] {
            "servlet", "undertow", "jetty",
            "netty-http", "spark-java", "coap" };

    @Parameter
    String apiContextPath;

    @Parameter
    String destinationGenerator;

    @Parameter
    String filterOperation;

    @Parameter
    String modelNamePrefix;

    @Parameter
    String modelNameSuffix;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/swagger")
    String modelOutput;

    @Parameter
    String modelPackage;

    @Parameter(defaultValue = "true")
    String modelWithXml;

    @Parameter(defaultValue = "${project}")
    MavenProject project;

    @Parameter(defaultValue = "true")
    boolean restConfiguration;

    @Parameter(defaultValue = "false")
    boolean clientRequestValidation;

    @Parameter(defaultValue = "false")
    boolean skip;

    @Parameter(defaultValue = "${project.basedir}/src/spec/swagger.json", required = true)
    String specificationUri;

    @Parameter(defaultValue = "2.4.19")
    String swaggerCodegenMavenPluginVersion;

    // A map of the language-specific parameters passed to the swagger-codegen-maven-plugin
    @Parameter(name = "configOptions")
    Map<?, ?> configOptions;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    @Component
    private BuildPluginManager pluginManager;

    DestinationGenerator createDestinationGenerator() throws MojoExecutionException {
        final Class<DestinationGenerator> destinationGeneratorClass;

        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final URL outputDirectory;
        try {
            outputDirectory = new File(project.getBuild().getOutputDirectory()).toURI().toURL();
        } catch (final MalformedURLException e) {
            throw new IllegalStateException(e);
        }
        final URL[] withOutput = new URL[] { outputDirectory };

        try (URLClassLoader classLoader = new URLClassLoader(withOutput, contextClassLoader)) {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            final Class<DestinationGenerator> tmp = (Class) classLoader.loadClass(destinationGenerator);

            if (!DestinationGenerator.class.isAssignableFrom(tmp)) {
                throw new MojoExecutionException(
                        "The given destinationGenerator class (" + destinationGenerator
                                                 + ") does not implement " + DestinationGenerator.class.getName()
                                                 + " interface.");
            }

            destinationGeneratorClass = tmp;
        } catch (final ClassNotFoundException | IOException e) {
            throw new MojoExecutionException(
                    "The given destinationGenerator class (" + destinationGenerator
                                             + ") cannot be loaded, make sure that it is present in the COMPILE classpath scope of the project",
                    e);
        }

        final DestinationGenerator destinationGeneratorObject;
        try {
            destinationGeneratorObject = destinationGeneratorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MojoExecutionException(
                    "The given destinationGenerator class (" + destinationGenerator
                                             + ") cannot be instantiated, make sure that it is declared as public and that all dependencies are present on the COMPILE classpath scope of the project",
                    e);
        }
        return destinationGeneratorObject;
    }

    void generateDto(final String language) throws MojoExecutionException {
        getLog().info(
                "Generating DTO classes using io.swagger:swagger-codegen-maven-plugin:" + swaggerCodegenMavenPluginVersion);

        // swagger-codegen-maven-plugin documentation and its supported options
        // https://github.com/swagger-api/swagger-codegen/tree/master/modules/swagger-codegen-maven-plugin

        final List<MojoExecutor.Element> elements = new ArrayList<>();
        elements.add(new MojoExecutor.Element("inputSpec", specificationUri));
        elements.add(new MojoExecutor.Element("language", language));
        elements.add(new MojoExecutor.Element("generateApis", "false"));
        elements.add(new MojoExecutor.Element("generateModelTests", "false"));
        elements.add(new MojoExecutor.Element("generateModelDocumentation", "false"));
        elements.add(new MojoExecutor.Element("generateSupportingFiles", "false"));
        if (modelOutput != null) {
            elements.add(new MojoExecutor.Element("output", modelOutput));
        }
        if (modelPackage != null) {
            elements.add(new MojoExecutor.Element("modelPackage", modelPackage));
        }
        if (modelNamePrefix != null) {
            elements.add(new MojoExecutor.Element("modelNamePrefix", modelNamePrefix));
        }
        if (modelNameSuffix != null) {
            elements.add(new MojoExecutor.Element("modelNameSuffix", modelNameSuffix));
        }
        if (modelWithXml != null) {
            elements.add(new MojoExecutor.Element("withXml", modelPackage));
        }
        if (configOptions != null && !configOptions.isEmpty()) {
            List<MojoExecutor.Element> options = new ArrayList<>();
            for (Map.Entry<?, ?> configOption : configOptions.entrySet()) {
                options.add(new MojoExecutor.Element(configOption.getKey().toString(), configOption.getValue().toString()));
            }
            elements.add(new MojoExecutor.Element("configOptions", options.toArray(new MojoExecutor.Element[options.size()])));
        }

        executeMojo(
                plugin(
                        groupId("io.swagger"),
                        artifactId("swagger-codegen-maven-plugin"),
                        version(swaggerCodegenMavenPluginVersion)),
                goal("generate"),
                configuration(
                        elements.toArray(new MojoExecutor.Element[elements.size()])),
                executionEnvironment(
                        mavenProject,
                        mavenSession,
                        pluginManager));
    }

    protected String detectCamelVersionFromClasspath() {
        return mavenProject.getDependencies().stream().filter(
                d -> "org.apache.camel".equals(d.getGroupId()) && ObjectHelper.isNotEmpty(d.getVersion()))
                .findFirst().map(Dependency::getVersion).orElse(null);
    }

    protected String detectRestComponentFromClasspath() {
        for (final Dependency dep : mavenProject.getDependencies()) {
            if ("org.apache.camel".equals(dep.getGroupId()) || "org.apache.camel.springboot".equals(dep.getGroupId())) {
                final String aid = dep.getArtifactId();
                final Optional<String> comp = Arrays.asList(DEFAULT_REST_CONSUMER_COMPONENTS).stream()
                        .filter(c -> aid.startsWith("camel-" + c)).findFirst();
                if (comp.isPresent()) {
                    return comp.get();
                }
            }
        }
        return null;
    }

    protected boolean detectSpringBootFromClasspath() {
        return mavenProject.getDependencies().stream().anyMatch(d -> "org.springframework.boot".equals(d.getGroupId()));
    }

    protected String detectSpringBootMainPackage() throws IOException {
        for (final String src : mavenProject.getCompileSourceRoots()) {
            final String d = findSpringSpringBootPackage(new File(src));
            if (d != null) {
                return d;
            }
        }
        return null;
    }

    protected String findSpringSpringBootPackage(final File dir) throws IOException {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.getName().endsWith(".java")) {
                    try (InputStream stream = new FileInputStream(file)) {
                        final String content = IOHelper.loadText(stream);
                        if (content.contains("@SpringBootApplication")) {
                            return grabPackageName(content);
                        }
                    }
                } else if (file.isDirectory()) {
                    final String packageName = findSpringSpringBootPackage(file);
                    if (packageName != null) {
                        return packageName;
                    }
                }
            }
        }
        return null;
    }

    protected static String grabPackageName(final String content) {
        final String[] lines = content.split("\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("package ")) {
                line = line.substring(8);
                line = line.trim();
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                return line;
            }
        }
        return null;
    }

    protected String findAppropriateComponent() {
        String comp = detectRestComponentFromClasspath();
        if (comp != null) {
            getLog().info("Detected Camel Rest component from classpath: " + comp);
        } else {
            comp = "servlet";

            String gid = "org.apache.camel";
            String aid = "camel-servlet";

            // is it spring boot?
            if (detectSpringBootFromClasspath()) {
                gid = "org.apache.camel.springboot";
                aid = "camel-servlet-starter";
            }

            String dep = "\n\t\t<dependency>"
                         + "\n\t\t\t<groupId>" + gid + "</groupId>"
                         + "\n\t\t\t<artifactId>" + aid + "</artifactId>";
            String ver = detectCamelVersionFromClasspath();
            if (ver != null) {
                dep += "\n\t\t\t<version>" + ver + "</version>";
            }
            dep += "\n\t\t</dependency>\n";

            getLog().info("Cannot detect Rest component from classpath. Will use servlet as Rest component.");
            getLog().info("Add the following dependency in the Maven pom.xml file:\n" + dep + "\n");
        }

        return comp;
    }

}
