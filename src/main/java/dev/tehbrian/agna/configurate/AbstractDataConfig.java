package dev.tehbrian.agna.configurate;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurateException;

public abstract class AbstractDataConfig<W extends ConfigurateWrapper<?>, D>
		extends AbstractConfig<W> {

	private @Nullable D data;

	protected AbstractDataConfig(final W wrapper) {
		super(wrapper);
	}

	protected abstract Class<D> dataClass();

	@Override
	public final void load() throws ConfigurateException {
		this.wrapper().load();
		this.data = this.wrapper().rootNode().get(this.dataClass());
	}

	public final D data() {
		if (this.data == null) {
			throw new IllegalStateException("Config has not been loaded yet");
		}
		return this.data;
	}
}
