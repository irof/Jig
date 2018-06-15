package org.dddjava.jig.domain.model.collections;

import org.dddjava.jig.domain.basic.UserNumber;
import org.dddjava.jig.domain.model.declaration.field.FieldDeclarations;
import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;
import org.dddjava.jig.domain.model.declaration.method.MethodNumber;
import org.dddjava.jig.domain.model.declaration.type.TypeIdentifier;
import org.dddjava.jig.domain.model.declaration.type.TypeIdentifiers;
import org.dddjava.jig.domain.model.networks.type.TypeDependencies;

/**
 * コレクションの切り口
 *
 * 以下を推測したい
 * ・ロジックがいろいろ書かれていそう --> そのクラスのロジックの書き方を重点レビュー
 * ・ロジックがほとんどなさそう --> そのクラスを使っているクラス側にロジックが書かれていないか、レビュー
 */
public class CollectionAngle {

    TypeIdentifier typeIdentifier;
    FieldDeclarations fields;
    MethodDeclarations methods;
    TypeIdentifiers userTypeIdentifiers;

    public CollectionAngle(TypeIdentifier typeIdentifier, FieldDeclarations fieldDeclarations, MethodDeclarations methodDeclarations, TypeDependencies allTypeDependencies) {
        this.typeIdentifier = typeIdentifier;
        this.userTypeIdentifiers = allTypeDependencies.stream()
                .filterTo(typeIdentifier)
                .removeSelf()
                .fromTypeIdentifiers();
        this.fields = fieldDeclarations.filterDeclareTypeIs(typeIdentifier);
        this.methods = methodDeclarations.filterDeclareTypeIs(typeIdentifier);
    }

    public TypeIdentifier typeIdentifier() {
        return typeIdentifier;
    }

    public TypeIdentifiers userTypeIdentifiers() {
        return userTypeIdentifiers;
    }

    public UserNumber userNumber() {
        return new UserNumber(userTypeIdentifiers().list().size());
    }

    public MethodNumber methodNumber() {
        return methods.number();
    }

    public MethodDeclarations methods() {
        return methods;
    }
}
