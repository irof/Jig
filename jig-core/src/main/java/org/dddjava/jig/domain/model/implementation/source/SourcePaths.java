package org.dddjava.jig.domain.model.implementation.source;

import org.dddjava.jig.domain.model.implementation.source.binary.BinarySourcePaths;
import org.dddjava.jig.domain.model.implementation.source.code.CodeSourcePaths;

import java.nio.file.Path;
import java.util.List;

/**
 * ソースのパス
 */
public class SourcePaths {

    BinarySourcePaths binarySourcePaths;
    CodeSourcePaths codeSourcePaths;

    public SourcePaths(BinarySourcePaths binarySourcePaths, CodeSourcePaths codeSourcePaths) {
        this.binarySourcePaths = binarySourcePaths;
        this.codeSourcePaths = codeSourcePaths;
    }

    public List<Path> binarySourcePaths() {
        return binarySourcePaths.paths();
    }

    public List<Path> textSourcePaths() {
        return codeSourcePaths.paths();
    }

    public SourcePaths merge(SourcePaths other) {
        return new SourcePaths(binarySourcePaths.merge(other.binarySourcePaths), codeSourcePaths.merge(other.codeSourcePaths));
    }
}
