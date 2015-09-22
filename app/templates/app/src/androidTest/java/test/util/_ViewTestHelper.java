package <%= appPackage %>.test.util;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;

import java.util.Arrays;

import flow.path.Path;
import mortar.MortarScope;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ViewTestHelper {

    public static MortarTestActivity createMortarTestActivity() {
        return spy(Robolectric.buildActivity(MortarTestActivity.class).create().start().get());
    }

    public static <V extends View> V mockView(Class<V> viewType) {
        MortarTestActivity activity = createMortarTestActivity();
        final V view = mock(viewType);
        when(view.getContext()).thenReturn(activity);

        when(view.getContext().getString(anyInt())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return view.getContext().getString((int) invocationOnMock.getArguments()[0]);
            }
        });
        when(view.getContext().getString(anyInt(), anyVararg())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                Object[] formatArgs = Arrays.asList(arguments).subList(1, arguments.length).toArray();
                return view.getContext().getString((int) arguments[0], formatArgs);
            }
        });
        return view;
    }

    public static <V extends View> ViewBuilder<V> createView(Class<V> viewType) {
        return new ViewBuilder<>(viewType);
    }

    public static void clickListViewItemAtPosition(ListView listView, int position) {
        View itemView = listView.getAdapter().getView(position, null, listView);
        listView.performItemClick(itemView, position, listView.getItemIdAtPosition(position));
    }


    public static class ViewBuilder<V extends View> {
        private Class<V> viewType;

        ViewBuilder(Class<V> viewType) {
            this.viewType = viewType;
        }

        public V forScreen(Path screen) {
            return build(screen);
        }

        private V build(Path screen) {
            Context context = createMortarTestActivity();
            MortarScope myScope = MortarScope.getScope(context);
            MortarScope newChildScope = myScope.findChild(myScope.getName());

            Context childContext = newChildScope.createContext(context);
            return spy(viewType.cast(Layouts.createView(childContext, screen)));
        }
    }
}
