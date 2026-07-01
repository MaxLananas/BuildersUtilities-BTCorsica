package dev.tehbrian.buildersutilities.setting;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.util.Permissions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

public final class SettingsListener implements Listener {

	private static final Set<Material> NECESSARY_PHYSICS_BLOCK_TYPES = new HashSet<>();
	private static final Set<Material> REDSTONE_PHYSICS_BLOCK_TYPES = new HashSet<>();

	private final ConfigConfig configConfig;
	private final Logger logger;

	@Inject
	public SettingsListener(
			final ConfigConfig configConfig,
			final Logger logger
	) {
		this.configConfig = configConfig;
		this.logger = logger;
	}

	public boolean isBlockTypeNecessaryPhysics(final Material material) {
		if (NECESSARY_PHYSICS_BLOCK_TYPES.isEmpty()) {
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.CHEST);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.TRAPPED_CHEST);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.ENDER_CHEST);
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.COPPER_CHESTS.getValues());
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.STAIRS.getValues());
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.FENCES.getValues());
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.FENCE_GATES.getValues());
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.BLACK_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.BLUE_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.BROWN_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.CYAN_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.GRAY_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.GREEN_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.LIME_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.MAGENTA_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.ORANGE_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.PINK_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.PURPLE_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.RED_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.WHITE_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.add(Material.YELLOW_STAINED_GLASS_PANE);
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.WALLS.getValues());
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.BARS.getValues());
			NECESSARY_PHYSICS_BLOCK_TYPES.addAll(Tag.DOORS.getValues());
		}

		return NECESSARY_PHYSICS_BLOCK_TYPES.contains(material);
	}

	public boolean isBlockTypeRedstonePhysics(final Material material) {
		if (REDSTONE_PHYSICS_BLOCK_TYPES.isEmpty()) {
			REDSTONE_PHYSICS_BLOCK_TYPES.addAll(Tag.REDSTONE_ORES.getValues());
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REDSTONE_BLOCK);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REDSTONE_WIRE);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REDSTONE_LAMP);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REDSTONE_TORCH);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REDSTONE_WALL_TORCH);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.DAYLIGHT_DETECTOR);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REPEATER);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.COMPARATOR);
			REDSTONE_PHYSICS_BLOCK_TYPES.addAll(Tag.DOORS.getValues());
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.TARGET);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.STRUCTURE_BLOCK);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.JUKEBOX);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.CRAFTER);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.POWERED_RAIL);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.DETECTOR_RAIL);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.ACTIVATOR_RAIL);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.HOPPER);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.NOTE_BLOCK);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.LEVER);
			REDSTONE_PHYSICS_BLOCK_TYPES.addAll(Tag.BUTTONS.getValues());
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.COMMAND_BLOCK);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.CHAIN_COMMAND_BLOCK);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.REPEATING_COMMAND_BLOCK);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.TRIPWIRE);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.TRIPWIRE_HOOK);
			REDSTONE_PHYSICS_BLOCK_TYPES.addAll(Tag.PRESSURE_PLATES.getValues());
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.STRING);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.PISTON);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.STICKY_PISTON);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.MOVING_PISTON);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.PISTON_HEAD);
			REDSTONE_PHYSICS_BLOCK_TYPES.add(Material.OBSERVER);
		}

		return REDSTONE_PHYSICS_BLOCK_TYPES.contains(material);
	}

	@EventHandler
	public void onSpectatorTeleport(final PlayerTeleportEvent event) {
		if (event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE
				&& !event.getPlayer().hasPermission(Permissions.SPECTATE_TELEPORT)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpectate(final PlayerStartSpectatingEntityEvent event) {
		if (event.getNewSpectatorTarget().getType() == EntityType.PLAYER) {
			if (!event.getPlayer().hasPermission(Permissions.SPECTATE_PLAYER)) {
				event.setCancelled(true);
			}
		} else {
			if (!event.getPlayer().hasPermission(Permissions.SPECTATE_ENTITY)) {
				event.setCancelled(true);
			}
		}
	}

	private boolean shouldCheckPhysics() {
		return this.configConfig.data().settings().disableRedstone()
				|| this.configConfig.data().settings().disableGravityPhysics()
				|| this.configConfig.data().settings().disablePhysics();
	}

	@EventHandler
	public void onBlockPhysics(final BlockPhysicsEvent event) {
		// don't perform any relatively expensive checks if we don't need to.
		if (!this.shouldCheckPhysics()) {
			return;
		}

		final Block block = event.getBlock();
		final Material blockType = block.getType();

		// allow grass blocks to turn snowy.
		if (Tag.SNOW.isTagged(blockType)
				&& block.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.GRASS_BLOCK)) {
			return;
		}

		if (this.isBlockTypeNecessaryPhysics(blockType)) {
			return;
		}

		if (this.isBlockTypeRedstonePhysics(blockType)) {
			if (this.configConfig.data().settings().disableRedstone()) {
				event.setCancelled(true);
				this.logger.debug("Physics were cancelled because disable-redstone: true");
			}
		} else if (event.getChangedType().hasGravity()) {
			if (this.configConfig.data().settings().disableGravityPhysics()) {
				event.setCancelled(true);
				this.logger.debug("Gravity physics were cancelled because disable-gravity-physics: true");
			}
		} else {
			if (this.configConfig.data().settings().disablePhysics()) {
				event.setCancelled(true);
				this.logger.debug("Physics were cancelled because disable-physics: true");
			}
		}
	}

	@EventHandler
	public void onEntityExplode(final EntityExplodeEvent event) {
		if (this.configConfig.data().settings().disableEntityExplode()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntityExplode(final EntityDamageEvent event) {
		if (this.configConfig.data().settings().disableEntityExplode()) {
			if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockExplode(final BlockExplodeEvent event) {
		if (this.configConfig.data().settings().disableBlockExplode()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByBlockExplode(final EntityDamageEvent event) {
		if (this.configConfig.data().settings().disableBlockExplode()
				&& event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onLeavesDecay(final LeavesDecayEvent event) {
		if (this.configConfig.data().settings().disableLeavesDecay()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onFarmlandTrample(final PlayerInteractEvent event) {
		if (this.configConfig.data().settings().disableFarmlandTrample()
				&& event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null
				&& event.getClickedBlock().getType().equals(Material.FARMLAND)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDragonEggTeleport(final BlockFromToEvent event) {
		if (this.configConfig.data().settings().disableDragonEggTeleport()
				&& event.getBlock().getType().equals(Material.DRAGON_EGG)) {
			event.setCancelled(true);
		}
	}

}
