package org.dddjava.jig.gradle;

import org.dddjava.jig.application.usecase.ImportService;
import org.dddjava.jig.presentation.view.JigDocument;
import org.dddjava.jig.infrastructure.LocalProject;
import org.dddjava.jig.presentation.view.JigDocumentHandler;
import org.dddjava.jig.presentation.view.JigHandlerContext;
import org.gradle.api.DefaultTask;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JigReportsTask extends DefaultTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(JigReportsTask.class);

    Dependencies dependencies = new Dependencies();


    @TaskAction
    void outputReports() {

        ExtensionContainer extensions = getProject().getExtensions();
        JigConfig config = extensions.findByType(JigConfig.class);

        List<JigDocument> jigDocuments = config.documentTypes();

        long startTime = System.currentTimeMillis();

        LOGGER.info("プロジェクト情報の取り込みをはじめます");
        LocalProject localProject = dependencies.localProject(getProject());
        ImportService importService = dependencies.importService();
        importService.importSources(
                localProject.getSpecificationSources(),
                localProject.getSqlSources(),
                localProject.getTypeNameSources(),
                localProject.getPackageNameSources());

        Path outputDirectory = Paths.get(config.getOutputDirectory());
        JigHandlerContext jigHandlerContext = dependencies.localViewContextWith(config);
        for (JigDocument jigDocument : jigDocuments) {
            JigDocumentHandler.of(jigDocument)
                    .handleLocal(jigHandlerContext)
                    .render(outputDirectory);
        }

        LOGGER.info("合計時間: {} ms", System.currentTimeMillis() - startTime);
    }
}
