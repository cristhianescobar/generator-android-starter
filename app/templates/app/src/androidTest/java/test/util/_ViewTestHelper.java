package <%= appPackage %>.test.util;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import flow.Layouts;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;
import <%= appPackage %>.util.mortar.BaseView;

public class ViewTestHelper {

	public static MortarTestActivity createMortarTestActivity() {
		return spy(Robolectric.buildActivity(MortarTestActivity.class).create().start().get());
	}

	public static <V extends BaseView> V mockView(Class<V> viewType) {
		MortarTestActivity activity = createMortarTestActivity();
		final V view = mock(viewType);
		when(view.getContext()).thenReturn(activity);

		when(view.getString(anyInt())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocationOnMock) throws Throwable {
				return view.getContext().getString((int) invocationOnMock.getArguments()[0]);
			}
		});
		when(view.getString(anyInt(), anyVararg())).thenAnswer(new Answer<String>() {
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

		public V forScreen(Blueprint screen) {
			return build(screen);
		}

		private V build(Blueprint screen) {
			Context context = createMortarTestActivity();
			MortarScope myScope = Mortar.getScope(context);

			MortarScope newChildScope = myScope.requireChild(screen);

			Context childContext = newChildScope.createContext(context);
			return spy(viewType.cast(Layouts.createView(childContext, screen)));
		}
	}
}
