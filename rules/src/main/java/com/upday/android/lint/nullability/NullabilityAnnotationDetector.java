package com.upday.android.lint.nullability;

import com.android.SdkConstants;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class NullabilityAnnotationDetector extends Detector
        implements Detector.JavaPsiScanner {

    private static final String ID = "MissingNullabilityAnnotation";

    @NonNull
    public static final Issue ISSUE = Issue.create(
            ID,
            String.format("References must be annotated with @%1$sNullable or @%1$sNonNull.",
                          SdkConstants.SUPPORT_ANNOTATIONS_PREFIX),
            "This Rule is used to try to enforce @Nullable and @NonNull annotations",
            Category.CORRECTNESS,
            /* Priority */7,
            Severity.WARNING,
            new Implementation(
                    NullabilityAnnotationDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Arrays.asList(PsiMethod.class, PsiField.class);
    }

    @NonNull
    @Override
    public JavaElementVisitor createPsiVisitor(@NonNull JavaContext context) {
        return new NullabilityAnnotationVisitor(context);
    }
}
