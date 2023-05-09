package com.ankoki.joyonghan;

import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.joyonghan.misc.Misc.OperatingSystem;
import com.ankoki.sakura.JSON;
import com.ankoki.sakura.JSON.MalformedJsonException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AppData {

	private static final String FOLDER_NAME = File.separator + "JOYONGHAN" + File.separator;

	private final File root;
	private final boolean success;
	private JSON data;

	/**
	 * Creates a new app data around this operating system.
	 *
	 * @param operatingSystem the operating system that the current device is running.
	 *
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws MalformedJsonException
	 */
	public AppData(OperatingSystem operatingSystem) throws IOException, IllegalAccessException, MalformedJsonException {
		if (operatingSystem == null)
			throw new IllegalStateException("Current operating system is unsupported. '" + System.getProperty("os.name", "<none>") + "'");
		String path = null;
		switch (operatingSystem) {
			case WINDOWS -> path = System.getenv("AppData") + AppData.FOLDER_NAME;
			case MAC, LINUX -> path = System.getProperty("user.home") + "/Library/Application Support" + AppData.FOLDER_NAME;
			default -> {
				assert false : "No found operating system is covered above.";
			}
		}
		System.out.println("Searching for 'data.json' in '" + path + "'.");
		this.root = new File(path + File.separator + "data.json");
		if (!root.exists()) {
			root.mkdirs();
			root.createNewFile();
			this.copyDefaultData();
			System.out.println("Default data file has been created.");
		} else
			System.out.println("Data was found at '" + this.root.getAbsolutePath() + "'.");
		this.parseDataFromRoot().thenRun(() -> {
			try {
				this.matchKeys();
			} catch (IllegalAccessException | MalformedJsonException ex) {
				ex.printStackTrace();
			}
		});
		this.success = true;
	}

	/**
	 * Returns true if the app data was enabled successfully.
	 *
	 * @return true if enabled.
	 */
	public boolean isEnabled() {
		return success;
	}

	/**
	 * Retrieves the data of this app.
	 *
	 * @return the data.
	 */
	@NotNull
	public JSON getData() {
		return this.data;
	}

	/**
	 * Copies the default local data to the root path.
	 *
	 * @throws IOException
	 * @throws IllegalAccessException if the data.json was not found locally.
	 */
	private void copyDefaultData() throws IOException, IllegalAccessException {
		InputStream stream = this.getClass().getResourceAsStream("/data.json");
		if (stream == null)
			throw new IllegalAccessException("The resource '/data.json' was not found in the jar.");
		Files.copy(stream, this.root.toPath(), StandardCopyOption.REPLACE_EXISTING);
		System.out.println("Default resource '/data.json' has been created.");
	}

	/**
	 * Match the keys from the current JSON. Must not be called before the field <code>data</code> is set.
	 *
	 * @throws IllegalAccessException if the data field is not set, or data.json was not found locally.
	 * @throws MalformedJsonException if the JSON is malformed.
	 */
	private void matchKeys() throws IllegalAccessException, MalformedJsonException {
		if (this.data == null)
			throw new IllegalAccessException("The field 'data' is not set in the current AppData object.");
		InputStream stream = this.getClass().getResourceAsStream("/data.json");
		if (stream == null)
			throw new IllegalAccessException("The resource '/data.json' was not found in the jar.");
		JSON shallow = new JSON(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining()));
		for (Entry<String, Object> entry : shallow.entrySet()) {
			if (!this.data.containsKey(entry.getKey())) {
				this.data.put(entry.getKey(), entry.getValue());
				System.out.println("The key '" + entry.getKey() + "' was not found, and set to the default value '" + entry.getValue() + "'.");
			}
		}
		Misc.saveJsonTo(this.data, this.root);
	}

	/**
	 * Parses the stored file into cache.
	 */
	private CompletableFuture<Void> parseDataFromRoot() {
		return CompletableFuture.runAsync(() -> {
			try {
				this.data = new JSON(this.root);
				System.out.println("Data has been successfully parsed from '" + this.root.getAbsolutePath() + "'");
			} catch (MalformedJsonException | IOException ex) {
				ex.printStackTrace();
			}
		});
	}

}
