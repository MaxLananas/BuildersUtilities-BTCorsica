package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.World;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.spongepowered.configurate.NodePath;

import static org.incendo.cloud.description.Description.description;
import static org.incendo.cloud.parser.standard.EnumParser.enumParser;

public final class BuildCommand {

	private final LangConfig langConfig;

	@Inject
	public BuildCommand(
			final LangConfig langConfig
	) {
		this.langConfig = langConfig;
	}

	public void register(
			final PaperCommandManager<Source> commandManager
	) {
		final var root = commandManager.commandBuilder("build")
				.commandDescription(description(
						"Build helper commands."
				))
				.permission(Permissions.BUILD_CMD)
				.senderType(PlayerSource.class);

		final var time = root.literal(
				"time", description("Set time of day.")
		)
				.required("value", enumParser(TimeValue.class))
				.handler(c -> {
					final var sender = c.sender().source();
					final World world = sender.getWorld();
					final TimeValue val = c.get("value");
					world.setTime(val.ticks());
					sender.sendMessage(this.langConfig.c(
							NodePath.path("commands", "time", "set")
					).replaceText(
							b -> b.match("{time}")
									.replacement(val.displayName())
					));
				});

		final var weather = root.literal(
				"weather", description("Set weather.")
		)
				.required("value", enumParser(WeatherValue.class))
				.handler(c -> {
					final var sender = c.sender().source();
					final World world = sender.getWorld();
					final WeatherValue val = c.get("value");
					world.setStorm(val.storm());
					world.setThundering(val.thunder());
					sender.sendMessage(this.langConfig.c(
							NodePath.path(
									"commands", "weather", "set"
							)
					).replaceText(
							b -> b.match("{weather}")
									.replacement(val.displayName())
					));
				});

		commandManager.command(time);
		commandManager.command(weather);
	}

	enum TimeValue {
		DAY(1000, "Day"),
		NOON(6000, "Noon"),
		SUNSET(12000, "Sunset"),
		NIGHT(13000, "Night"),
		MIDNIGHT(18000, "Midnight"),
		SUNRISE(23000, "Sunrise");

		private final long ticks;
		private final String displayName;

		TimeValue(final long ticks, final String displayName) {
			this.ticks = ticks;
			this.displayName = displayName;
		}

		public long ticks() {
			return this.ticks;
		}

		public String displayName() {
			return this.displayName;
		}
	}

	enum WeatherValue {
		CLEAR(false, false, "Clear"),
		RAIN(true, false, "Rain"),
		THUNDER(true, true, "Thunder");

		private final boolean storm;
		private final boolean thunder;
		private final String displayName;

		WeatherValue(
				final boolean storm,
				final boolean thunder,
				final String displayName
		) {
			this.storm = storm;
			this.thunder = thunder;
			this.displayName = displayName;
		}

		public boolean storm() {
			return this.storm;
		}

		public boolean thunder() {
			return this.thunder;
		}

		public String displayName() {
			return this.displayName;
		}
	}
}
