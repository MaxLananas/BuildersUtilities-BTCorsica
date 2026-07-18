package dev.tehbrian.agna.paper.configurate;

import dev.tehbrian.agna.configurate.AbstractConfig;
import dev.tehbrian.agna.configurate.ConfigurateWrapper;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ConfigLoader {

	private final JavaPlugin plugin;

	public ConfigLoader(final JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unchecked")
	public boolean load(final List<Loadable<?>> loadables) {
		boolean success = true;
		for (final Loadable<?> loadable : loadables) {
			try {
				final Path filePath = this.plugin.getDataFolder().toPath()
						.resolve(loadable.fileName());
				if (!Files.exists(filePath)) {
					this.plugin.saveResource(loadable.fileName(), false);
				}
				((AbstractConfig<?>) loadable.config()).load();
				if (loadable.expectedVersion() >= 0) {
					final var root = loadable.config().wrapper().rootNode();
					if (root != null) {
						final int actual = root.node("version").getInt(-1);
						if (actual != loadable.expectedVersion()) {
							this.plugin.getSLF4JLogger().error(
									"{} has version {} but expected {}. "
											+ "Delete to regenerate.",
									loadable.fileName(), actual,
									loadable.expectedVersion()
							);
							success = false;
						}
					}
				}
			} catch (final ConfigurateException e) {
				this.plugin.getSLF4JLogger().error(
						"Failed to load {}", loadable.fileName(), e
				);
				success = false;
			}
		}
		return success;
	}

	public record Loadable<W extends ConfigurateWrapper<?>>(
			String fileName,
			AbstractConfig<W> config,
			int expectedVersion
	) {
		public static <W extends ConfigurateWrapper<?>> Loadable<W> ofVersioned(
				final String fileName,
				final AbstractConfig<W> config,
				final int expectedVersion
		) {
			return new Loadable<>(fileName, config, expectedVersion);
		}
	}
}
