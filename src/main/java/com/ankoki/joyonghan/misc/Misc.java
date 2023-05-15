package com.ankoki.joyonghan.misc;

import com.ankoki.sakura.JSON;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Misc {

	/**
	 * Gets the operating system which this device is currently running.
	 *
	 * @return the current operating system. May be null.
	 */
	@Nullable
	public static OperatingSystem getOperatingSystem() {
		return OperatingSystem.getFrom(System.getProperty("os.name"));
	}

	/**
	 * Saves a JSON object async to the given path.
	 *
	 * @param json the json to save.
	 * @param file the file to save it too.
	 */
	public static CompletableFuture<Void> saveJsonTo(JSON json, File file) {
		return CompletableFuture.runAsync(() -> {
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(json.toPrettyString());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}



}
