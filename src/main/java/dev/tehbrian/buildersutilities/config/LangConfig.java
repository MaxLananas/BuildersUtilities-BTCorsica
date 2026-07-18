package dev.tehbrian.buildersutilities.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.tehbrian.agna.paper.configurate.AbstractLangConfig;
import net.kyori.adventure.text.Component;
import org.spongepowered.configurate.NodePath;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public final class LangConfig extends AbstractLangConfig<YamlConfigurateWrapper> {

	private String prefix = "";

	@Inject
	public LangConfig(final @Named("dataFolder") Path dataFolder) {
		super(new YamlConfigurateWrapper(dataFolder.resolve("lang.yml")));
	}

	private String prefix() {
		if (this.prefix.isEmpty()) {
			final String p = this.wrapper().rootNode()
					.node("prefix").getString();
			if (p != null) {
				this.prefix = p;
			}
		}
		return this.prefix;
	}

	@Override
	protected String getAndVerifyString(final NodePath path) {
		final String raw = super.getAndVerifyString(path);
		return raw.replace("{prefix}", this.prefix());
	}

	/**
	 * Splits the input string by line and parses each line individually.
	 *
	 * @param path the config path
	 * @return the component list
	 */
	public List<Component> cl(final NodePath path) {
		return this.getAndVerifyString(path).lines()
				.map(miniMessage()::deserialize).toList();
	}

	/**
	 * Reads a YAML list at the given path and parses each entry
	 * as a MiniMessage component with prefix replacement.
	 *
	 * @param path the config path
	 * @return the component list
	 */
	public List<Component> cList(final NodePath path) {
		final var node = this.wrapper().rootNode().node(path);
		final List<String> raw;
		try {
			raw = node.getList(String.class, List.of());
		} catch (final Exception e) {
			return List.of();
		}
		final List<Component> result = new ArrayList<>(raw.size());
		for (final String entry : raw) {
			final String replaced = entry.replace(
					"{prefix}", this.prefix()
			);
			result.add(miniMessage().deserialize(replaced));
		}
		return result;
	}
}
