package dev.tehbrian.agna.configurate;

import org.spongepowered.configurate.ConfigurateException;

public abstract class AbstractConfig<W extends ConfigurateWrapper<?>> {

	private final W wrapper;

	protected AbstractConfig(final W wrapper) {
		this.wrapper = wrapper;
	}

	public W wrapper() {
		return this.wrapper;
	}

	public abstract void load() throws ConfigurateException;
}
