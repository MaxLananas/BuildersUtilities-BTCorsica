package dev.tehbrian.agna.configurate;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.nio.file.Path;

public final class ConfigurateWrapper<T extends ConfigurationLoader<CommentedConfigurationNode>> {

	private final Path path;
	private final T loader;
	private CommentedConfigurationNode rootNode;

	public ConfigurateWrapper(final Path path, final T loader) {
		this.path = path;
		this.loader = loader;
	}

	public void load() throws ConfigurateException {
		this.rootNode = this.loader.load();
	}

	public void save() throws ConfigurateException {
		if (this.rootNode != null) {
			this.loader.save(this.rootNode);
		}
	}

	public CommentedConfigurationNode rootNode() {
		return this.rootNode;
	}

	public T loader() {
		return this.loader;
	}

	public Path path() {
		return this.path;
	}
}
