package com.upday.android.lint.nullability;

import com.android.SdkConstants;
import com.android.tools.lint.detector.api.JavaContext;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiTypeElement;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class NullabilityAnnotationVisitor extends JavaElementVisitor {

    @NonNull
    private static final String ANNOTATION_NON_NULL = SdkConstants.SUPPORT_ANNOTATIONS_PREFIX
                                                      + "NonNull";
    @NonNull
    private static final String ANNOTATION_NULLABLE = SdkConstants.SUPPORT_ANNOTATIONS_PREFIX
                                                      + "Nullable";

    @NonNull
    private final JavaContext mJavaContext;

    NullabilityAnnotationVisitor(@NonNull JavaContext javaContext) {
        mJavaContext = javaContext;
    }

    @Override
    public void visitMethod(final PsiMethod method) {
        handleMethodsParameterList(method.getParameterList());
        handleMethodReturnType(method);
    }

    @Override
    public void visitField(final PsiField field) {
        if (field.getModifierList() == null
            || isPrimitive(field.getTypeElement())
            || isEnumConstant(field)) {
            return;
        }

        boolean hasNullable = field.getModifierList().findAnnotation(ANNOTATION_NULLABLE) != null;
        boolean hasNonNull = field.getModifierList().findAnnotation(ANNOTATION_NON_NULL) != null;

        if (!hasNullable && !hasNonNull) {
            mJavaContext.report(NullabilityAnnotationDetector.ISSUE,
                                field,
                                mJavaContext.getLocation(field),
                                "Field type is missing either @Nullable or @NonNull");
        }
    }

    private void handleMethodsParameterList(@NonNull PsiParameterList parameterList) {
        for (PsiParameter parameter : parameterList.getParameters()) {
            handleSingleParameter(parameter);
        }
    }

    private void handleMethodReturnType(PsiMethod method) {
        if (isPrimitive(method.getReturnTypeElement())) {
            return;
        }

        boolean hasNullable = method.getModifierList().findAnnotation(ANNOTATION_NULLABLE) != null;
        boolean hasNonNull = method.getModifierList().findAnnotation(ANNOTATION_NON_NULL) != null;

        if (!hasNullable && !hasNonNull) {
            mJavaContext.report(NullabilityAnnotationDetector.ISSUE,
                                method.getReturnTypeElement(),
                                mJavaContext.getLocation(method.getReturnTypeElement()),
                                "Return type is missing either @Nullable or @NonNull");
        }
    }

    private void handleSingleParameter(@NonNull PsiParameter parameter) {
        if (parameter.getModifierList() == null || isPrimitive(parameter.getTypeElement())) {
            return;
        }

        boolean hasNullable = parameter.getModifierList().findAnnotation(ANNOTATION_NULLABLE)
                              != null;
        boolean hasNonNull = parameter.getModifierList().findAnnotation(ANNOTATION_NON_NULL)
                             != null;

        if (!hasNullable && !hasNonNull) {
            mJavaContext.report(NullabilityAnnotationDetector.ISSUE,
                                parameter,
                                mJavaContext.getLocation(parameter),
                                "Parameter is missing either @Nullable or @NonNull");
        }
    }

    private static boolean isPrimitive(@Nullable PsiTypeElement psiTypeElement) {
        return psiTypeElement != null
               && psiTypeElement.getInnermostComponentReferenceElement() == null;
    }

    private static boolean isEnumConstant(@NonNull PsiField psiField) {
        return psiField instanceof PsiEnumConstant;
    }
}
