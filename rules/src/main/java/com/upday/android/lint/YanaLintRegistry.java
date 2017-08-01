package com.upday.android.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.upday.android.lint.nullability.NullabilityAnnotationDetector;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class YanaLintRegistry extends IssueRegistry {

    @NonNull
    @Override
    public List<Issue> getIssues() {
        return Collections.singletonList(NullabilityAnnotationDetector.ISSUE);
    }
}
