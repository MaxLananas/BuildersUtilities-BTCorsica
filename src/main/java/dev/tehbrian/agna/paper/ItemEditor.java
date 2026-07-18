package dev.tehbrian.agna.paper;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public final class ItemEditor {

	private final ItemStack itemStack;

	private ItemEditor(final ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public static ItemEditor edit(final ItemType type) {
		return new ItemEditor(type.createItemStack());
	}

	public static ItemEditor edit(final ItemStack itemStack) {
		return new ItemEditor(itemStack.clone());
	}

	public ItemEditor itemName(final Component name) {
		this.itemStack.editMeta(meta -> meta.displayName(name));
		return this;
	}

	public ItemEditor lore(final List<Component> lore) {
		this.itemStack.editMeta(meta -> meta.lore(lore));
		return this;
	}

	public ItemEditor amount(final int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}

	public ItemEditor hideTooltip(final boolean hide) {
		if (hide) {
			this.itemStack.editMeta(meta -> meta.displayName(Component.empty()));
		}
		return this;
	}

	public ItemEditor dyedColor(final Color color) {
		this.itemStack.editMeta(
				LeatherArmorMeta.class, meta -> meta.setColor(color)
		);
		return this;
	}

	public ItemEditor bannerPatterns(final List<Pattern> patterns) {
		this.itemStack.editMeta(
				BannerMeta.class, meta -> meta.setPatterns(patterns)
		);
		return this;
	}

	public ItemEditor textures(final String texturesBase64) {
		this.itemStack.editMeta(SkullMeta.class, meta -> {
			final PlayerProfile profile = Bukkit.createProfile(
					UUID.randomUUID(), ""
			);
			profile.setProperty(
					new ProfileProperty("textures", texturesBase64)
			);
			meta.setPlayerProfile(profile);
		});
		return this;
	}

	public ItemEditor resetName() {
		this.itemStack.editMeta(meta -> meta.displayName(null));
		return this;
	}

	public ItemEditor resetLore() {
		this.itemStack.editMeta(meta -> meta.lore(null));
		return this;
	}

	public List<Pattern> getBannerPatterns() {
		if (this.itemStack.getItemMeta() instanceof final BannerMeta bm) {
			return bm.getPatterns();
		}
		return List.of();
	}

	public ItemStack item() {
		return this.itemStack;
	}
}
