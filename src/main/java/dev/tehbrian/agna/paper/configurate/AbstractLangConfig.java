package dev.tehbrian.agna.paper.configurate;

import dev.tehbrian.agna.configurate.AbstractConfig;
import dev.tehbrian.agna.configurate.ConfigurateWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.NodePath;

public abstract class AbstractLangConfig<W extends ConfigurateWrapper<?>>
		extends AbstractConfig<W> {

	protected AbstractLangConfig(final W wrapper) {
		super(wrapper);
	}

	@Override
	public void load() throws ConfigurateException {
		this.wrapper().load();
	}

	public Component c(final NodePath path) {
		return MiniMessage.miniMessage().deserialize(this.getAndVerifyString(path));
	}

	protected String getAndVerifyString(final NodePath path) {
		final String value = this.wrapper().rootNode().node(path).getString();
		return value != null ? value : "";
	}
}
