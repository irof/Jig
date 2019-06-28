package org.dddjava.jig.domain.model.declaration.field;

import org.dddjava.jig.domain.model.declaration.type.TypeIdentifier;

/**
 * フィールド定義
 */
public class FieldDeclaration {

    TypeIdentifier declaringType;
    String name;
    TypeIdentifier typeIdentifier;

    public FieldDeclaration(TypeIdentifier declaringType, String name, TypeIdentifier typeIdentifier) {
        this.declaringType = declaringType;
        this.name = name;
        this.typeIdentifier = typeIdentifier;
    }

    public TypeIdentifier typeIdentifier() {
        return typeIdentifier;
    }

    public String nameText() {
        return name;
    }

    public String signatureText() {
        return String.format("%s %s", typeIdentifier.asSimpleText(), name);
    }

    public TypeIdentifier declaringType() {
        return declaringType;
    }
}
