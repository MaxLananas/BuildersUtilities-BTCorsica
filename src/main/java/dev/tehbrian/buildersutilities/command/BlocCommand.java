package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.special.SpecialMenuProvider;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.description.Description.description;

public final class BlocCommand {

	private final SpecialMenuProvider specialMenuProvider;

	@Inject
	public BlocCommand(
			final SpecialMenuProvider specialMenuProvider
	) {
		this.specialMenuProvider = specialMenuProvider;
	}

	public void register(final PaperCommandManager<Source> commandManager) {
		final var main = commandManager.commandBuilder("bloc")
				.commandDescription(description(
						"Opens the special items menu."
				))
				.permission(Permissions.BLOC)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					sender.openInventory(
							this.specialMenuProvider.generate()
					);
				});

		commandManager.command(main);
	}
}
