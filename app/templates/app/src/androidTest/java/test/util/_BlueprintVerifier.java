package <%= appPackage %>.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import android.view.View;
import dagger.Module;
import flow.Layout;
import mortar.Blueprint;

public class BlueprintVerifier {

	private Blueprint screen;
	private String scopeName;
	private Object daggerModule;
	private Class<?> viewType;
	private Class<?> addsToModuleClass;
	private Integer layoutResId;

	public static BlueprintVerifier forScreen(Blueprint screen) {
		return new BlueprintVerifier(screen);
	}

	BlueprintVerifier(Blueprint screen) {
		this.screen = screen;
		scopeName = screen.getClass().getName();
		daggerModule = screen.getDaggerModule();
		viewType = null;
		addsToModuleClass = null;
		layoutResId = null;
	}

	public BlueprintVerifier hasScopeName(String scopeName) {
		this.scopeName = scopeName;
		return this;
	}

	public <V extends View> BlueprintVerifier injectsView(Class<V> viewType) {
		this.viewType = viewType;
		return this;
	}

	public BlueprintVerifier addsToModule(Class<?> moduleClass) {
		this.addsToModuleClass = moduleClass;
		return this;
	}

	public BlueprintVerifier hasLayout(int layoutResId) {
		this.layoutResId = layoutResId;
		return this;
	}

	public void verify() {
		assertThat(screen.getMortarScopeName()).isEqualTo(scopeName);

		assertThat(daggerModule).isNotNull();
		Module moduleAnnotation = daggerModule.getClass().getAnnotation(Module.class);
		assertThat(moduleAnnotation).isNotNull();
		if (viewType != null) {
			assertThat(moduleAnnotation.injects()).contains(viewType);
		}
		if (addsToModuleClass != null) {
			assertThat(moduleAnnotation.addsTo()).isEqualTo(addsToModuleClass);
		}

		if (layoutResId != null) {
			Layout layoutAnnotation = screen.getClass().getAnnotation(Layout.class);
			assertThat(layoutAnnotation).isNotNull();
			assertThat(layoutAnnotation.value()).isEqualTo(layoutResId);
		}
	}
}
