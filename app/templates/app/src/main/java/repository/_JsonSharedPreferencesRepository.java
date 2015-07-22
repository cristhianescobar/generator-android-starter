package <%= appPackage %>.repository;

import com.google.gson.GsonBuilder;

import android.content.SharedPreferences;

/**
 * Repository allowing objects to be stored in {@link android.content.SharedPreferences} as JSON.
 */
public class JsonSharedPreferencesRepository {
	private GsonBuilder gsonBuilder;
	private SharedPreferences sharedPreferences;

	public JsonSharedPreferencesRepository(GsonBuilder gsonBuilder, SharedPreferences sharedPreferences) {
		this.gsonBuilder = gsonBuilder;
		this.sharedPreferences = sharedPreferences;
	}

	public void putObject(String key, Object object) {
		String json = gsonBuilder.create().toJson(object);
		sharedPreferences.edit().putString(key, json).apply();
	}

	public <T> T getObject(String key, Class<T> type) {
		String json = sharedPreferences.getString(key, null);
		if (json == null) {
			return null;
		}

		return gsonBuilder.create().fromJson(json, type);
	}
}
