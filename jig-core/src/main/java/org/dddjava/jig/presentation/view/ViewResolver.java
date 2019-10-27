package org.dddjava.jig.presentation.view;

import org.dddjava.jig.domain.model.architectures.ArchitectureAngle;
import org.dddjava.jig.domain.model.businessrules.BusinessRuleNetwork;
import org.dddjava.jig.domain.model.categories.CategoryAngles;
import org.dddjava.jig.domain.model.declaration.package_.PackageIdentifierFormatter;
import org.dddjava.jig.domain.model.interpret.alias.AliasFinder;
import org.dddjava.jig.domain.model.interpret.relation.packages.PackageNetwork;
import org.dddjava.jig.domain.model.interpret.structure.PackageStructure;
import org.dddjava.jig.domain.model.services.ServiceAngles;
import org.dddjava.jig.presentation.view.graphvizj.*;

public class ViewResolver {

    AliasFinder aliasFinder;
    PackageIdentifierFormatter packageIdentifierFormatter;
    MethodNodeLabelStyle methodNodeLabelStyle;
    DiagramFormat diagramFormat;

    public ViewResolver(AliasFinder aliasFinder, PackageIdentifierFormatter packageIdentifierFormatter, MethodNodeLabelStyle methodNodeLabelStyle, DiagramFormat diagramFormat) {
        this.aliasFinder = aliasFinder;
        this.packageIdentifierFormatter = packageIdentifierFormatter;
        this.methodNodeLabelStyle = methodNodeLabelStyle;
        this.diagramFormat = diagramFormat;
    }

    public JigView<PackageNetwork> dependencyWriter() {
        return newGraphvizjView(new PackageDependencyDiagram(packageIdentifierFormatter, aliasFinder));
    }

    public JigView<ServiceAngles> serviceMethodCallHierarchy() {
        return newGraphvizjView(new ServiceMethodCallDiagram(aliasFinder, methodNodeLabelStyle));
    }

    public JigView<CategoryAngles> enumUsage() {
        return newGraphvizjView(new CategoryUsageDiagram(aliasFinder));
    }

    private <T> JigView<T> newGraphvizjView(DotTextEditor<T> diagram) {
        return new GraphvizjView<>(diagram, diagramFormat);
    }

    public JigView<ServiceAngles> booleanServiceTrace() {
        return newGraphvizjView(new BooleanServiceTraceDiagram(aliasFinder, methodNodeLabelStyle));
    }

    public JigView<BusinessRuleNetwork> businessRuleRelationWriter() {
        return newGraphvizjView(new BusinessRuleRelationDiagram(packageIdentifierFormatter, aliasFinder));
    }

    public JigView<CategoryAngles> categories() {
        return newGraphvizjView(new CategoryDiagram(aliasFinder));
    }

    public JigView<PackageStructure> packageTreeWriter() {
        return newGraphvizjView(new PackageTreeDiagram(packageIdentifierFormatter, aliasFinder));
    }

    public JigView<ArchitectureAngle> architecture() {
        return newGraphvizjView(model -> new DotTexts(model.dotText(JigDocumentContext.getInstance())));
    }
}
