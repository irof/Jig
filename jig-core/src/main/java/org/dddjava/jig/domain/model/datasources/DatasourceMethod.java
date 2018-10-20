package org.dddjava.jig.domain.model.datasources;

import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;
import org.dddjava.jig.domain.model.unit.method.Method;

public class DatasourceMethod {
    Method repositoryMethod;
    Method datasourceMethod;
    MethodDeclarations usingMethods;

    public DatasourceMethod(Method repositoryMethod, Method datasourceMethod, MethodDeclarations usingMethods) {
        this.repositoryMethod = repositoryMethod;
        this.datasourceMethod = datasourceMethod;
        this.usingMethods = usingMethods;
    }

    public Method repositoryMethod() {
        return repositoryMethod;
    }

    public Method datasourceMethod() {
        return datasourceMethod;
    }

    public MethodDeclarations usingMethods() {
        return usingMethods;
    }
}