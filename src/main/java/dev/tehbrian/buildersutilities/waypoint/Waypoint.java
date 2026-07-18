package dev.tehbrian.buildersutilities.waypoint;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class Waypoint {

	private final String world;
	private final double x;
	private final double y;
	private final double z;
	private final float yaw;
	private final float pitch;

	public Waypoint(
			final String world,
			final double x,
			final double y,
			final double z,
			final float yaw,
			final float pitch
	) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public static Waypoint fromLocation(final Location loc) {
		return new Waypoint(
				loc.getWorld().getName(),
				loc.getX(), loc.getY(), loc.getZ(),
				loc.getYaw(), loc.getPitch()
		);
	}

	public Location toLocation() {
		final World w = Bukkit.getWorld(this.world);
		if (w == null) {
			return null;
		}
		return new Location(
				w, this.x, this.y, this.z, this.yaw, this.pitch
		);
	}

	public String world() {
		return this.world;
	}

	public double x() {
		return this.x;
	}

	public double y() {
		return this.y;
	}

	public double z() {
		return this.z;
	}
}
