package org.dddjava.jig.domain.model.interpret.architecture;

import org.dddjava.jig.domain.model.declaration.annotation.TypeAnnotation;
import org.dddjava.jig.domain.model.declaration.type.TypeIdentifier;
import org.dddjava.jig.domain.model.implementation.bytecode.TypeByteCode;
import org.dddjava.jig.infrastructure.configuration.JigProperties;

import java.util.List;
import java.util.regex.Pattern;

/**
 * アーキテクチャ
 */
public class Architecture {

    IsBusinessRule isBusinessRule;

    public Architecture(JigProperties properties) {
        Pattern compilerGeneratedClassPattern = Pattern.compile(".+\\$\\d+");

        Pattern businessRulePattern = Pattern.compile(properties.getBusinessRulePattern());
        this.isBusinessRule = typeByteCode -> {
            String fqn = typeByteCode.typeIdentifier().fullQualifiedName();
            return businessRulePattern.matcher(fqn).matches() && !compilerGeneratedClassPattern.matcher(fqn).matches();
        };
    }

    boolean isService(List<TypeAnnotation> typeAnnotations) {
        TypeIdentifier serviceAnnotation = new TypeIdentifier("org.springframework.stereotype.Service");
        return typeAnnotations.stream()
                .anyMatch(typeAnnotation -> typeAnnotation.typeIs(serviceAnnotation));
    }

    boolean isDataSource(TypeByteCode typeByteCode) {
        // TODO インタフェース実装を見てない
        // DataSourceは Repositoryインタフェースが実装され @Repository のついた infrastructure/datasource のクラス
        List<TypeAnnotation> typeAnnotations = typeByteCode.typeAnnotations();
        TypeIdentifier repositoryAnnotation = new TypeIdentifier("org.springframework.stereotype.Repository");
        return typeAnnotations.stream()
                .anyMatch(typeAnnotation -> typeAnnotation.typeIs(repositoryAnnotation));
    }

    boolean isController(List<TypeAnnotation> typeAnnotations) {
        TypeIdentifier controller = new TypeIdentifier("org.springframework.stereotype.Controller");
        TypeIdentifier restController = new TypeIdentifier("org.springframework.web.bind.annotation.RestController");
        TypeIdentifier controllerAdvice = new TypeIdentifier("org.springframework.web.bind.annotation.ControllerAdvice");
        return typeAnnotations.stream()
                .anyMatch(typeAnnotation -> typeAnnotation.typeIs(controller)
                        || typeAnnotation.typeIs(restController)
                        || typeAnnotation.typeIs(controllerAdvice));
    }

    public boolean isBusinessRule(TypeByteCode typeByteCode) {
        return isBusinessRule.isBusinessRule(typeByteCode);
    }
}
