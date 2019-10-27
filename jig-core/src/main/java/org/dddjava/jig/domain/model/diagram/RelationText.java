package org.dddjava.jig.domain.model.diagram;

import org.dddjava.jig.domain.model.declaration.method.MethodDeclaration;
import org.dddjava.jig.domain.model.declaration.package_.PackageIdentifier;
import org.dddjava.jig.domain.model.declaration.type.TypeIdentifier;

import java.util.StringJoiner;

/**
 * 関連
 */
public class RelationText {

    StringJoiner stringJoiner;

    public RelationText() {
        this("");
    }

    public RelationText(String attribute) {
        this.stringJoiner = new StringJoiner("\n");
        stringJoiner.add(attribute);
    }

    private void add(String from, String to) {
        // "hoge" -> "fuga";
        String line = '"' + from + '"' + " -> " + '"' + to + '"' + ';';
        stringJoiner.add(line);
    }

    public String asText() {
        return stringJoiner.toString();
    }

    public void add(PackageIdentifier from, PackageIdentifier to) {
        add(from.asText(), to.asText());
    }

    public void add(TypeIdentifier from, TypeIdentifier to) {
        add(from.fullQualifiedName(), to.fullQualifiedName());
    }

    public void add(MethodDeclaration from, MethodDeclaration to) {
        add(from.asFullNameText(), to.asFullNameText());
    }

    public void add(TypeIdentifier from, String to) {
        add(from.fullQualifiedName(), to);
    }

    public void add(MethodDeclaration from, TypeIdentifier to) {
        add(from.asFullNameText(), to.fullQualifiedName());
    }
}
