package dev.tehbrian.buildersutilities.command;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.BuildersUtilities;
import dev.tehbrian.buildersutilities.ability.AbilityMenuProvider;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.user.UserService;
import dev.tehbrian.buildersutilities.util.Permissions;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.spongepowered.configurate.NodePath;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.incendo.cloud.description.Description.description;

public final class BuildersUtilitiesCommand {

	private final BuildersUtilities buildersUtilities;
	private final UserService userService;
	private final LangConfig langConfig;
	private final AbilityMenuProvider abilityMenuProvider;

	@Inject
	public BuildersUtilitiesCommand(
			final BuildersUtilities buildersUtilities,
			final UserService userService,
			final LangConfig langConfig,
			final AbilityMenuProvider abilityMenuProvider
	) {
		this.buildersUtilities = buildersUtilities;
		this.userService = userService;
		this.langConfig = langConfig;
		this.abilityMenuProvider = abilityMenuProvider;
	}

	private static int min(final int a, final int b, final int c) {
		return Math.min(Math.min(a, b), c);
	}

	private static Collection<Chunk> around(
			final Chunk origin,
			final int radius
	) {
		final World world = origin.getWorld();
		final int length = (radius * 2) + 1;
		final Set<Chunk> chunks = new HashSet<>(length * length);
		final int cX = origin.getX();
		final int cZ = origin.getZ();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				chunks.add(world.getChunkAt(cX + x, cZ + z));
			}
		}
		return chunks;
	}

	public void register(
			final PaperCommandManager<Source> commandManager
	) {
		final var root = commandManager.commandBuilder(
				"buildersutilities", "butils", "bu"
		);

		final var ability = root
				.commandDescription(description(
						"Opens the ability menu."
				))
				.permission(Permissions.ABILITY)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					sender.openInventory(
							this.abilityMenuProvider.generate(
									this.userService.getUser(sender)
							)
					);
				});

		final var help = root.literal(
				"help", description("Shows help.")
		).handler(c -> {
			final var sender = c.sender().source();
			sender.sendMessage(this.langConfig.c(
					NodePath.path("commands", "help", "header")
			));
			for (final Component line : this.langConfig.cl(
					NodePath.path("commands", "help", "commands")
			)) {
				sender.sendMessage(line);
			}
		});

		final var rc = root.literal(
				"rc", description("Reloads the chunks around you.")
		)
				.permission(Permissions.RC)
				.senderType(PlayerSource.class)
				.handler(c -> {
					final var sender = c.sender().source();
					final Collection<Chunk> chunksToReload = around(
							sender.getLocation().getChunk(),
							min(
									8,
									sender.getViewDistance(),
									sender.getServer().getViewDistance()
							)
					);
					final ServerPlayer nmsPlayer =
							((CraftPlayer) sender).getHandle();
					final ServerLevel nmsLevel =
							((CraftWorld) sender.getWorld()).getHandle();
					for (final Chunk chunk : chunksToReload) {
						final long key = ChunkPos.asLong(
								chunk.getX(), chunk.getZ()
						);
						final ChunkHolder nmsChunk = nmsLevel
								.getChunkSource().chunkMap
								.getVisibleChunkIfPresent(key);
						if (nmsChunk == null) {
							continue;
						}
						final LevelChunk nmsChunkToSend =
								nmsChunk.getChunkToSend();
						if (nmsChunkToSend == null) {
							continue;
						}
						final var packet =
								new ClientboundLevelChunkWithLightPacket(
										nmsChunkToSend,
										nmsLevel.getLightEngine(),
										null, null, false
								);
						nmsPlayer.connection.send(packet);
					}
					sender.sendMessage(this.langConfig.c(
							NodePath.path("commands", "rc")
					));
				});

		final var reload = root.literal(
				"reload", description("Reloads the config.")
		)
				.permission(Permissions.RELOAD)
				.handler(c -> {
					if (this.buildersUtilities.loadConfiguration()) {
						c.sender().source().sendMessage(
								this.langConfig.c(NodePath.path(
										"commands", "reload", "success"
								))
						);
					} else {
						c.sender().source().sendMessage(
								this.langConfig.c(NodePath.path(
										"commands", "reload", "failure"
								))
						);
					}
				});

		commandManager.command(ability);
		commandManager.command(help);
		commandManager.command(rc);
		commandManager.command(reload);
	}
}
