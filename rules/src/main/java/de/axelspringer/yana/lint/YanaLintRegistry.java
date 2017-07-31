package de.axelspringer.yana.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import de.axelspringer.yana.lint.nullability.NullabilityAnnotationDetector;

public class YanaLintRegistry extends IssueRegistry {

    @NonNull
    @Override
    public List<Issue> getIssues() {
        return Collections.singletonList(NullabilityAnnotationDetector.ISSUE);
    }
}
