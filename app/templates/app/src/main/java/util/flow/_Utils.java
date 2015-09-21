/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package <%= appPackage %>.util.flow;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import flow.Flow;
import flow.History;

public final class Utils {
    public interface OnMeasuredCallback {
        void onMeasured(View view, int width, int height);
    }

    public static void waitForMeasure(final View view, final OnMeasuredCallback callback) {
        int width = view.getWidth();
        int height = view.getHeight();

        if (width > 0 && height > 0) {
            callback.onMeasured(view, width, height);
            return;
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final ViewTreeObserver observer = view.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.removeOnPreDrawListener(this);
                }

                callback.onMeasured(view, view.getWidth(), view.getHeight());

                return true;
            }
        });
    }

    public static void goBack(Context context, int numScreens) {
        History.Builder builder = Flow.get(context).getHistory().buildUpon();

        for (int i = numScreens; i > 0; i--) {
            builder.pop();
        }

        History newHistory = builder.build();
        Flow.get(context).setHistory(newHistory, Flow.Direction.BACKWARD);
    }

    public static void goBack(View view, int numScreens) {
        goBack(view.getContext(), numScreens);
    }

    public static void replaceTop(Context context, Object newTop) {
        History.Builder builder = Flow.get(context).getHistory().buildUpon();
        builder.pop();
        History newHistory = builder.push(newTop).build();
        Flow.get(context).setHistory(newHistory, Flow.Direction.REPLACE);
    }

    public static void replaceTop(View view, Object newTop) {
        replaceTop(view.getContext(), newTop);
    }

    private Utils() {
    }
}
