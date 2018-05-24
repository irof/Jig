package org.dddjava.jig.presentation.view;

import org.dddjava.jig.domain.model.categories.EnumAngles;
import org.dddjava.jig.domain.model.identifier.namespace.PackageIdentifierFormatter;
import org.dddjava.jig.domain.model.japanese.JapaneseNameFinder;
import org.dddjava.jig.domain.model.networks.PackageDependencies;
import org.dddjava.jig.domain.basic.report.Reports;
import org.dddjava.jig.domain.model.services.ServiceAngles;
import org.dddjava.jig.presentation.view.graphvizj.EnumUsageToImageView;
import org.dddjava.jig.presentation.view.graphvizj.PackageDependencyToImageView;
import org.dddjava.jig.presentation.view.graphvizj.ServiceAngleToImageView;
import org.dddjava.jig.presentation.view.poi.ReportToExcelView;
import org.springframework.stereotype.Component;

@Component
public class ViewResolver {

    private PackageIdentifierFormatter packageIdentifierFormatter;

    public ViewResolver(PackageIdentifierFormatter packageIdentifierFormatter) {
        this.packageIdentifierFormatter = packageIdentifierFormatter;
    }

    public JigView<PackageDependencies> dependencyWriter(JapaneseNameFinder japaneseNameFinder) {
        return new PackageDependencyToImageView(packageIdentifierFormatter, japaneseNameFinder);
    }

    public JigView<ServiceAngles> serviceMethodCallHierarchy(JapaneseNameFinder japaneseNameFinder) {
        return new ServiceAngleToImageView(japaneseNameFinder);
    }

    public JigView<Reports> applicationList() {
        return new ReportToExcelView();
    }

    public JigView<EnumAngles> enumUsage(JapaneseNameFinder japaneseNameFinder) {
        return new EnumUsageToImageView(japaneseNameFinder);
    }

    public JigView<Reports> domainList() {
        return new ReportToExcelView();
    }
}