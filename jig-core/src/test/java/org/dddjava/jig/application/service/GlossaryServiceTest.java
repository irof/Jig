package org.dddjava.jig.application.service;

import org.assertj.core.api.Assertions;
import org.dddjava.jig.domain.model.declaration.method.MethodIdentifier;
import org.dddjava.jig.domain.model.declaration.method.MethodSignature;
import org.dddjava.jig.domain.model.declaration.namespace.PackageIdentifier;
import org.dddjava.jig.domain.model.declaration.type.TypeIdentifier;
import org.dddjava.jig.domain.model.implementation.raw.TextSource;
import org.dddjava.jig.infrastructure.DefaultLayout;
import org.dddjava.jig.infrastructure.Layout;
import org.dddjava.jig.infrastructure.LocalProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import stub.domain.model.ClassJavadocStub;
import stub.domain.model.MethodJavadocStub;
import stub.domain.model.NotJavadocStub;
import testing.JigServiceTest;
import testing.TestSupport;

import java.util.Collections;
import java.util.stream.Stream;

@JigServiceTest
class GlossaryServiceTest {

    GlossaryService sut;

    public GlossaryServiceTest(GlossaryService glossaryService) {
        sut = glossaryService;
    }

    @Test
    void パッケージ和名取得() {
        TextSource textSource = getTextSource();

        sut.importJapanese(textSource.packageInfoSources());

        Assertions.assertThat(sut.japaneseNameFrom(new PackageIdentifier("stub")).value())
                .isEqualTo("テストで使用するスタブたち");
    }

    private TextSource getTextSource() {
        LocalProject localProject = new LocalProject();
        Layout layout = new DefaultLayout(TestSupport.getModuleRootPath().toString(), "dummy", "dummy", "src/test/java");
        return localProject.createSource(layout).textSource();
    }

    @ParameterizedTest
    @MethodSource
    void クラス和名取得(TypeIdentifier typeIdentifier, String comment) {
        TextSource textSource = getTextSource();

        sut.importJapanese(textSource.javaSources());

        Assertions.assertThat(sut.japaneseNameFrom(typeIdentifier).value())
                .isEqualTo(comment);
    }

    static Stream<Arguments> クラス和名取得() {
        return Stream.of(
                Arguments.of(new TypeIdentifier(ClassJavadocStub.class), "クラスのJavadoc"),
                Arguments.of(new TypeIdentifier(MethodJavadocStub.class), ""),
                Arguments.of(new TypeIdentifier(NotJavadocStub.class), ""),
                Arguments.of(new TypeIdentifier("DefaultPackageClass"), "デフォルトパッケージにあるクラス")
        );
    }

    @Test
    void メソッド和名取得() {
        TextSource textSource = getTextSource();

        sut.importJapanese(textSource.javaSources());

        MethodIdentifier methodIdentifier = new MethodIdentifier(new TypeIdentifier(MethodJavadocStub.class), new MethodSignature(
                "method",
                new org.dddjava.jig.domain.model.declaration.method.Arguments(Collections.emptyList())));
        Assertions.assertThat(sut.japaneseNameFrom(methodIdentifier).value())
                .isEqualTo("メソッドのJavadoc");

        MethodIdentifier overloadMethodIdentifier = new MethodIdentifier(new TypeIdentifier(MethodJavadocStub.class), new MethodSignature(
                "overloadMethod",
                new org.dddjava.jig.domain.model.declaration.method.Arguments(Collections.singletonList(new TypeIdentifier(String.class)))));
        Assertions.assertThat(sut.japaneseNameFrom(overloadMethodIdentifier).value())
                // オーバーロードは一意にならないのでどちらか
                .matches("引数(なし|あり)のメソッド");
    }
}
