package org.dddjava.jig.gradle;

import org.assertj.core.api.SoftAssertions;
import org.gradle.internal.impldep.org.junit.Before;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class IntegrationTestForKotlinDSL {
    final Path outputDir = Paths.get("stub-kotlin-dsl/build/jig");

    @Before
    public void clean() {
        File dir = outputDir.toFile();
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }
    }


    @Test
    void スタブプロジェクトへの適用でパッケージ図と機能一覧が出力されること() throws IOException, URISyntaxException {
        BuildResult result = executeGradleTasks("5.6", "clean", "compileJava", "jigReports", "--stacktrace");

        System.out.println(result.getOutput());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getOutput()).contains("BUILD SUCCESSFUL");
        softly.assertThat(outputDir.resolve("package-relation-depth4.svg")).exists();
        softly.assertAll();
    }

    private BuildResult executeGradleTasks(String version, String... tasks) throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("plugin-classpath.txt");
        List<File> pluginClasspath = Files.readAllLines(Paths.get(resource.toURI())).stream()
                .map(File::new)
                .collect(toList());

        return GradleRunner.create()
                .withGradleVersion(version)
                .withProjectDir(new File("./stub-kotlin-dsl"))
                .withArguments(tasks)
                .withPluginClasspath(pluginClasspath)
                .build();
    }


}
