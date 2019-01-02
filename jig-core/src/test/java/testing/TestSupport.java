package testing;

import org.dddjava.jig.domain.model.implementation.raw.ClassSource;
import org.dddjava.jig.domain.model.implementation.raw.SourceLocation;
import org.dddjava.jig.infrastructure.DefaultLayout;
import org.dddjava.jig.infrastructure.Layout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestSupport {

    public static URL[] getTestResourceRootURLs() {
        try {
            URL classRootUrl = defaultPackageClassURI().toURL();
            URL resourceRootUrl = resourceRootURI().toURL();
            return new URL[]{classRootUrl, resourceRootUrl};
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }
    }

    public static Path getModuleRootPath() {
        URI uri = defaultPackageClassURI();
        Path path = Paths.get(uri).toAbsolutePath();

        while (!path.endsWith("jig-core")) {
            path = path.getParent();
            if (path == null) {
                throw new IllegalStateException("モジュール名変わった？");
            }
        }
        return path;
    }


    public static URI defaultPackageClassURI() {
        try {
            return TestSupport.class.getResource("/DefaultPackageClass.class").toURI().resolve("./");
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    public static URI resourceRootURI() {
        try {
            return TestSupport.class.getResource("/marker.properties").toURI().resolve("./");
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    public static ClassSource newClassSource(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new ClassSource(new SourceLocation(), bytes, "DUMMY");
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static Layout testLayout() {
        Path path = Paths.get(TestSupport.defaultPackageClassURI());
        return new DefaultLayout(
                path.toString(),
                path.toString(),
                // Mapper.xmlのためだが、ここではHitしなくてもテストのクラスパスから読めてしまう
                "not/read/resources",
                "not/read/sources");
    }
}
