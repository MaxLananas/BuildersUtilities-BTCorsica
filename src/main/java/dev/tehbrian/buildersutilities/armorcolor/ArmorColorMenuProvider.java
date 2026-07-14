package dev.tehbrian.buildersutilities.armorcolor;

import com.google.inject.Inject;
import dev.tehbrian.buildersutilities.config.ConfigConfig;
import dev.tehbrian.buildersutilities.config.LangConfig;
import dev.tehbrian.buildersutilities.util.ChestSize;
import dev.tehbrian.buildersutilities.util.ItemEditor;
import dev.tehbrian.buildersutilities.util.MenuItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemType;
import org.spongepowered.configurate.NodePath;

import java.util.List;

import static dev.tehbrian.buildersutilities.util.ItemEditor.edit;
import static java.util.Objects.requireNonNull;

public final class ArmorColorMenuProvider {

	private final LangConfig langConfig;
	private final ConfigConfig configConfig;

	@Inject
	public ArmorColorMenuProvider(
			final LangConfig langConfig,
			final ConfigConfig configConfig
	) {
		this.langConfig = langConfig;
		this.configConfig = configConfig;
	}

	public Inventory generate() {
		final Inventory inv = Bukkit.createInventory(
				null,
				ChestSize.DOUBLE,
				this.langConfig.c(NodePath.path("menus", "armor-color", "inventory-name"))
		);

		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, MenuItems.BACKGROUND);
		}

		inv.setItem(
				10,
				edit(ItemType.LEATHER_HELMET)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "get-helmet")))
						.item()
		);
		inv.setItem(
				19,
				edit(ItemType.LEATHER_CHESTPLATE)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "get-chestplate")))
						.item()
		);
		inv.setItem(
				28,
				edit(ItemType.LEATHER_LEGGINGS)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "get-leggings")))
						.item()
		);
		inv.setItem(
				37,
				edit(ItemType.LEATHER_BOOTS)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "get-boots")))
						.item()
		);

		final List<Component> changeLore = this.langConfig.cl(NodePath.path("menus", "armor-color", "change"));

		inv.setItem(
				22,
				edit(ItemType.PLAYER_HEAD)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-red")))
						.textures(this.configConfig.data().heads().armorColor().randomizeRed())
						.item()
		);
		inv.setItem(
				23,
				edit(ItemType.PLAYER_HEAD)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-green")))
						.textures(this.configConfig.data().heads().armorColor().randomizeGreen())
						.item()
		);
		inv.setItem(
				24,
				edit(ItemType.PLAYER_HEAD)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "randomize-blue")))
						.textures(this.configConfig.data().heads().armorColor().randomizeBlue())
						.item()
		);
		inv.setItem(
				31,
				edit(ItemType.PLAYER_HEAD).amount(16)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "red")))
						.lore(changeLore)
						.textures(this.configConfig.data().heads().armorColor().red())
						.item()
		);
		inv.setItem(
				32,
				edit(ItemType.PLAYER_HEAD).amount(16)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "green")))
						.lore(changeLore)
						.textures(this.configConfig.data().heads().armorColor().green())
						.item()
		);
		inv.setItem(
				33,
				edit(ItemType.PLAYER_HEAD).amount(16)
						.itemName(this.langConfig.c(NodePath.path("menus", "armor-color", "blue")))
						.lore(changeLore)
						.textures(this.configConfig.data().heads().armorColor().blue())
						.item()
		);

		this.update(inv);

		return inv;
	}

	public void update(final Inventory inv) {
		int r = (requireNonNull(inv.getItem(31)).getAmount() - 1) * 8;
		int g = (requireNonNull(inv.getItem(32)).getAmount() - 1) * 8;
		int b = (requireNonNull(inv.getItem(33)).getAmount() - 1) * 8;

		if (r == 256) {
			r = 255;
		}

		if (g == 256) {
			g = 255;
		}

		if (b == 256) {
			b = 255;
		}

		final Color finalColor = Color.fromRGB(r, g, b);

		inv.setItem(10, ItemEditor.edit(requireNonNull(inv.getItem(10))).dyedColor(finalColor).item());
		inv.setItem(19, ItemEditor.edit(requireNonNull(inv.getItem(19))).dyedColor(finalColor).item());
		inv.setItem(28, ItemEditor.edit(requireNonNull(inv.getItem(28))).dyedColor(finalColor).item());
		inv.setItem(37, ItemEditor.edit(requireNonNull(inv.getItem(37))).dyedColor(finalColor).item());
	}

}
