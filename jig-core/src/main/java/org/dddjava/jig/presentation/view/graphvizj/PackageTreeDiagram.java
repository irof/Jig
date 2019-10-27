package org.dddjava.jig.presentation.view.graphvizj;

import org.dddjava.jig.domain.model.interpret.structure.PackageStructure;
import org.dddjava.jig.domain.model.declaration.package_.PackageIdentifierFormatter;
import org.dddjava.jig.domain.model.interpret.alias.AliasFinder;

/**
 * パッケージツリー
 */
public class PackageTreeDiagram implements DotTextEditor<PackageStructure> {

    AliasFinder aliasFinder;

    public PackageTreeDiagram(PackageIdentifierFormatter formatter, AliasFinder aliasFinder) {
        this.aliasFinder = aliasFinder;
    }

    @Override
    public DotTexts edit(PackageStructure model) {
        return new DotTexts(model.treeDotText(aliasFinder));
    }
}
