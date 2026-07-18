package dev.tehbrian.buildersutilities.waypoint;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class WaypointService {

	private final Map<UUID, Map<String, Waypoint>> data = new LinkedHashMap<>();
	private final File file;
	private final JavaPlugin plugin;

	public WaypointService(final JavaPlugin plugin) {
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder(), "waypoints.yml");
		this.load();
	}

	public void add(final UUID uuid, final String name, final Waypoint wp) {
		this.data.computeIfAbsent(uuid, k -> new LinkedHashMap<>())
				.put(name.toLowerCase(), wp);
		this.save();
	}

	public void remove(final UUID uuid, final String name) {
		final var map = this.data.get(uuid);
		if (map != null) {
			map.remove(name.toLowerCase());
			this.save();
		}
	}

	public Waypoint get(final UUID uuid, final String name) {
		final var map = this.data.get(uuid);
		if (map == null) {
			return null;
		}
		return map.get(name.toLowerCase());
	}

	public Map<String, Waypoint> getAll(final UUID uuid) {
		final var map = this.data.get(uuid);
		if (map == null) {
			return Map.of();
		}
		return Collections.unmodifiableMap(map);
	}

	private void load() {
		if (!this.file.exists()) {
			return;
		}
		final YamlConfiguration yaml =
				YamlConfiguration.loadConfiguration(this.file);
		for (final String uuidStr : yaml.getKeys(false)) {
			final UUID uuid;
			try {
				uuid = UUID.fromString(uuidStr);
			} catch (final IllegalArgumentException e) {
				continue;
			}
			final var section = yaml.getConfigurationSection(uuidStr);
			if (section == null) {
				continue;
			}
			final var map = new LinkedHashMap<String, Waypoint>();
			for (final String name : section.getKeys(false)) {
				final var wp = section.getConfigurationSection(name);
				if (wp == null) {
					continue;
				}
				map.put(name.toLowerCase(), new Waypoint(
						wp.getString("world", "world"),
						wp.getDouble("x"),
						wp.getDouble("y"),
						wp.getDouble("z"),
						(float) wp.getDouble("yaw"),
						(float) wp.getDouble("pitch")
				));
			}
			this.data.put(uuid, map);
		}
	}

	private void save() {
		final YamlConfiguration yaml = new YamlConfiguration();
		for (final var entry : this.data.entrySet()) {
			final String uuidStr = entry.getKey().toString();
			for (final var wpEntry : entry.getValue().entrySet()) {
				final String path = uuidStr + "." + wpEntry.getKey();
				final Waypoint wp = wpEntry.getValue();
				yaml.set(path + ".world", wp.world());
				yaml.set(path + ".x", wp.x());
				yaml.set(path + ".y", wp.y());
				yaml.set(path + ".z", wp.z());
			}
		}
		try {
			yaml.save(this.file);
		} catch (final IOException e) {
			this.plugin.getSLF4JLogger().error(
					"Failed to save waypoints", e
			);
		}
	}
}
