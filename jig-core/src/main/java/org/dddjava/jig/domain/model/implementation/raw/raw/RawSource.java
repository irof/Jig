package org.dddjava.jig.domain.model.implementation.raw.raw;

import org.dddjava.jig.domain.model.implementation.raw.binary.BinarySources;
import org.dddjava.jig.domain.model.implementation.raw.classfile.ClassSources;
import org.dddjava.jig.domain.model.implementation.raw.sqlfile.SqlSources;
import org.dddjava.jig.domain.model.implementation.raw.textfile.TextSource;
import org.dddjava.jig.domain.model.implementation.raw.textfile.TextSources;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 生ソース
 */
public class RawSource {

    TextSources textSources;
    BinarySources binarySources;

    public RawSource(TextSources textSources, BinarySources binarySources) {
        this.textSources = textSources;
        this.binarySources = binarySources;
    }

    public TextSource textSource() {
        return textSources.toTextSource();
    }

    public SqlSources sqlSources() {
        URL[] urls = binarySources.list().stream()
                .map(binarySource -> {
                    try {
                        return binarySource.sourceLocation().uri().toURL();
                    } catch (MalformedURLException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .toArray(URL[]::new);
        List<String> classNames = binarySources.list().stream()
                .flatMap(binarySource -> binarySource.classSources().list().stream())
                .map(classSource -> classSource.className())
                .filter(name -> name.endsWith("Mapper"))
                .collect(toList());
        return new SqlSources(urls, classNames);
    }

    public boolean nothingBinarySource() {
        return binarySources.nothing();
    }

    public boolean nothingTextSource() {
        return textSources.nothing();
    }

    public ClassSources classSources() {
        return binarySources.toBinarySource().classSources();
    }
}
