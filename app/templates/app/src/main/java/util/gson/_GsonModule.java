package <%= appPackage %>.util.gson;

import java.util.Map;

import javax.inject.Singleton;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import flow.Parcer;

/**
 * Module providing dependencies for Google's GSON library.
 */
@Module(library = true)
public class GsonModule {
	@Provides
	@Singleton
	GsonBuilder provideDefaultGsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Money.class, new MoneyTypeConverter());
		gsonBuilder.registerTypeAdapter(CurrencyUnit.class, new CurrencyUnitTypeConverter());
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeConvertor());
		gsonBuilder.registerTypeAdapter(DateTimeZone.class, new DateTimeZoneTypeConvertor());
		gsonBuilder.registerTypeAdapter(Map.class, new JsonToMapDeserializer());
		return gsonBuilder;
	}

	@Provides
	@Singleton
	Parcer<Object> provideGsonParcer(GsonBuilder gsonBuilder) {
		return new GsonParcer<>(gsonBuilder.create());
	}

}
