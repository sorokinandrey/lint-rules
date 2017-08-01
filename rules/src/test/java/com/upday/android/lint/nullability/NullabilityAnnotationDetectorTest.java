package com.upday.android.lint.nullability;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class NullabilityAnnotationDetectorTest extends LintDetectorTest {

    @Override
    protected boolean allowCompilationErrors() {
        return true;
    }

    @NonNull
    @Override
    protected Detector getDetector() {
        return new NullabilityAnnotationDetector();
    }

    @NonNull
    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(NullabilityAnnotationDetector.ISSUE);
    }

    public void testParameterNullabilityAnnotation_WhenParameterIsReference() throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "public void aMethod(String anObject) {} \n"
                                       + "}\n"));
        assertEquals(
                "src/foo/bar/Example.java:3: Warning: Parameter is missing either @Nullable "
                + "or @NonNull [MissingNullabilityAnnotation]\n"
                + "public void aMethod(String anObject) {} \n"
                + "                    ~~~~~~~~~~~~~~~\n"
                + "0 errors, 1 warnings\n", result);
    }

    public void testParameterNullabilityAnnotation_WhenParameterIsPrimitive() throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "public void aMethod(boolean aBoolean) {} \n"
                                       + "}\n"));
        assertEquals("No warnings.", result);
    }

    public void testParameterNullabilityAnnotation_WhenNonNullIsThere() throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "public void aMethod(@android.support.annotation.NonNull String anObject) {} \n"
                                       + "}\n"));
        assertEquals("No warnings.", result);
    }

    public void testParameterNullabilityAnnotation_WhenNullableIsThere() throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "public void aMethod(@android.support.annotation.Nullable String anObject) {} \n"
                                       + "}\n"));
        assertEquals("No warnings.", result);
    }

    public void testReturnTypeNullabilityAnnotation_WhenNullableIsThere() throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "@android.support.annotation.NonNull public String aMethod() { return \"\"; } \n"
                                       + "}\n"));
        assertEquals("No warnings.", result);
    }

    public void testReturnTypeNullabilityAnnotation_WhenNullabilityAnnotationIsNotThere()
            throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "public String aMethod(@android.support.annotation.Nullable String anObject) { return \"\"; } \n"
                                       + "}\n"));
        assertEquals(
                "src/foo/bar/Example.java:3: Warning: Return type is missing either @Nullable "
                + "or @NonNull [MissingNullabilityAnnotation]\n"
                + "public String aMethod(@android.support.annotation.Nullable String anObject) { return \"\"; } \n"
                + "       ~~~~~~\n"
                + "0 errors, 1 warnings\n", result);
    }

    public void testClassPropertyNullabilityAnnotation_WhenNullabilityAnnotationIsNotThere()
            throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "import java.lang.String;"
                                       + "public class Example { \n"
                                       + "public String aString = \"\";"
                                       + "}\n"));

        assertEquals(
                "src/foo/bar/Example.java:3: Warning: Field type is missing either @Nullable "
                + "or @NonNull [MissingNullabilityAnnotation]\n"
                + "public String aString = \"\";}\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "0 errors, 1 warnings\n", result);
    }

    public void testClassPropertyNullabilityAnnotation_WhenNullabilityAnnotationIsThere()
            throws Exception {
        String result = lintFiles(java("package foo.bar;\n"
                                       + "public class Example {\n"
                                       + "@android.support.annotation.NonNull public String aString = \"\";\n"
                                       + "}\n"));

        assertEquals("No warnings.", result);
    }
}