package jig.domain.model.relation;

import jig.domain.model.identifier.MethodIdentifier;
import jig.domain.model.identifier.MethodIdentifiers;
import jig.domain.model.identifier.TypeIdentifier;
import jig.domain.model.identifier.TypeIdentifiers;

public interface RelationRepository {

    void registerMethod(MethodIdentifier methodIdentifier);

    void registerMethodParameter(MethodIdentifier methodIdentifier);

    void registerMethodReturnType(MethodIdentifier methodIdentifier, TypeIdentifier returnTypeIdentifier);

    void registerMethodUseMethod(MethodIdentifier identifier, MethodIdentifier methodName);

    void registerMethodUseType(MethodIdentifier identifier, TypeIdentifier fieldTypeName);

    void registerImplementation(MethodIdentifier methodIdentifier, MethodIdentifier methodIdentifier1);

    void registerField(TypeIdentifier typeIdentifier, TypeIdentifier fieldClassTypeIdentifier);

    TypeIdentifier getReturnTypeOf(MethodIdentifier methodIdentifier);

    TypeIdentifiers findUseTypeOf(MethodIdentifier methodIdentifier);

    MethodIdentifiers findConcrete(MethodIdentifier methodIdentifier);

    MethodIdentifiers findUseMethod(MethodIdentifier methodIdentifier);

    MethodIdentifiers methodsOf(TypeIdentifier typeIdentifier);

    TypeIdentifiers findFieldUsage(TypeIdentifier name);

    MethodIdentifiers findMethodUsage(TypeIdentifier name);

    TypeIdentifiers findAllUsage(TypeIdentifier typeIdentifier);
}
