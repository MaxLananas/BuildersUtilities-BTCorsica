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

	private static final Set<Material> necessaryPhysicsBlockTypes = new HashSet<>();
	private static final Set<Material> redstonePhysicsBlockTypes = new HashSet<>();
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
		if (necessaryPhysicsBlockTypes.isEmpty()) {
			necessaryPhysicsBlockTypes.add(Material.CHEST);
			necessaryPhysicsBlockTypes.add(Material.TRAPPED_CHEST);
			necessaryPhysicsBlockTypes.add(Material.ENDER_CHEST);
			necessaryPhysicsBlockTypes.addAll(Tag.COPPER_CHESTS.getValues());
			necessaryPhysicsBlockTypes.addAll(Tag.STAIRS.getValues());
			necessaryPhysicsBlockTypes.addAll(Tag.FENCES.getValues());
			necessaryPhysicsBlockTypes.addAll(Tag.FENCE_GATES.getValues());
			necessaryPhysicsBlockTypes.add(Material.GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.BLACK_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.BLUE_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.BROWN_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.CYAN_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.GRAY_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.GREEN_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.LIME_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.MAGENTA_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.ORANGE_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.PINK_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.PURPLE_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.RED_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.WHITE_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.add(Material.YELLOW_STAINED_GLASS_PANE);
			necessaryPhysicsBlockTypes.addAll(Tag.WALLS.getValues());
			necessaryPhysicsBlockTypes.addAll(Tag.BARS.getValues());
			necessaryPhysicsBlockTypes.addAll(Tag.DOORS.getValues());
		}

		return necessaryPhysicsBlockTypes.contains(material);
	}

	public boolean isBlockTypeRedstonePhysics(final Material material) {
		if (redstonePhysicsBlockTypes.isEmpty()) {
			redstonePhysicsBlockTypes.addAll(Tag.REDSTONE_ORES.getValues());
			redstonePhysicsBlockTypes.add(Material.REDSTONE_BLOCK);
			redstonePhysicsBlockTypes.add(Material.REDSTONE_WIRE);
			redstonePhysicsBlockTypes.add(Material.REDSTONE_LAMP);
			redstonePhysicsBlockTypes.add(Material.REDSTONE_TORCH);
			redstonePhysicsBlockTypes.add(Material.REDSTONE_WALL_TORCH);
			redstonePhysicsBlockTypes.add(Material.DAYLIGHT_DETECTOR);
			redstonePhysicsBlockTypes.add(Material.REPEATER);
			redstonePhysicsBlockTypes.add(Material.COMPARATOR);
			redstonePhysicsBlockTypes.addAll(Tag.DOORS.getValues());
			redstonePhysicsBlockTypes.add(Material.TARGET);
			redstonePhysicsBlockTypes.add(Material.STRUCTURE_BLOCK);
			redstonePhysicsBlockTypes.add(Material.JUKEBOX);
			redstonePhysicsBlockTypes.add(Material.CRAFTER);
			redstonePhysicsBlockTypes.add(Material.POWERED_RAIL);
			redstonePhysicsBlockTypes.add(Material.DETECTOR_RAIL);
			redstonePhysicsBlockTypes.add(Material.ACTIVATOR_RAIL);
			redstonePhysicsBlockTypes.add(Material.HOPPER);
			redstonePhysicsBlockTypes.add(Material.NOTE_BLOCK);
			redstonePhysicsBlockTypes.add(Material.LEVER);
			redstonePhysicsBlockTypes.addAll(Tag.BUTTONS.getValues());
			redstonePhysicsBlockTypes.add(Material.COMMAND_BLOCK);
			redstonePhysicsBlockTypes.add(Material.CHAIN_COMMAND_BLOCK);
			redstonePhysicsBlockTypes.add(Material.REPEATING_COMMAND_BLOCK);
			redstonePhysicsBlockTypes.add(Material.TRIPWIRE);
			redstonePhysicsBlockTypes.add(Material.TRIPWIRE_HOOK);
			redstonePhysicsBlockTypes.addAll(Tag.PRESSURE_PLATES.getValues());
			redstonePhysicsBlockTypes.add(Material.STRING);
			redstonePhysicsBlockTypes.add(Material.PISTON);
			redstonePhysicsBlockTypes.add(Material.STICKY_PISTON);
			redstonePhysicsBlockTypes.add(Material.MOVING_PISTON);
			redstonePhysicsBlockTypes.add(Material.PISTON_HEAD);
			redstonePhysicsBlockTypes.add(Material.OBSERVER);
		}

		return redstonePhysicsBlockTypes.contains(material);
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

		if (event.getSourceBlock().getType().isAir() || blockType.isAir()) {
			return;
		}

		// allow grass blocks to turn snowy.
		if (Tag.SNOW.isTagged(blockType)
				&& block.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.GRASS_BLOCK)) {
			return;
		}

		if (isBlockTypeNecessaryPhysics(blockType)) {
			return;
		}

		if (isBlockTypeRedstonePhysics(blockType)) {
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
