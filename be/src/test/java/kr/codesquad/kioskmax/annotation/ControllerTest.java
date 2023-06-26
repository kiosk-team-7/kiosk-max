package kr.codesquad.kioskmax.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureRestDocs(uriHost = "api.kiosk.com", uriPort = 80)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
@WebMvcTest()
public @interface ControllerTest {

    @AliasFor(annotation = WebMvcTest.class)
    Class<?>[] value() default {};
}
