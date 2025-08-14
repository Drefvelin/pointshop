package net.tfminecraft.PointShop.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.tfminecraft.PointShop.PointShop;
import net.tfminecraft.PointShop.loaders.TradeLoader;
import net.tfminecraft.PointShop.objects.PlayerData;
import net.tfminecraft.PointShop.objects.Trade;

public class InventoryManager {
	public void shopView(Player player, Inventory i, boolean update) {
		if(!update) {
			i = PointShop.plugin.getServer().createInventory(null, 27, "§7Point Shop");
		}
		for(int y = 0; y<TradeLoader.get().size();y++) {
			Trade t = TradeLoader.get().get(y);
			i.setItem(y, createTradeItem(t, PlayerManager.getByPlayer(player)));
		}
		if(!update) {
			player.openInventory(i);
		}
	}
	public ItemStack createTradeItem(Trade t, PlayerData pd) {
		ItemStack i = t.getMenuItem();
		ItemMeta meta = i.getItemMeta();
		List<String> lore = new ArrayList<>();
		if(meta.getLore().size() > 0) {
			lore.addAll(meta.getLore());
			lore.add("");
		}
		String cost = "§6"+t.getCost() +" "+ t.getType().getName();
		if(t.getCost() > 1) cost = cost+"s";
		lore.add("§eCost: "+cost);
		lore.add(" ");
		int amount = pd.getPoints(t.getType());
		String current = "§6"+amount+" "+t.getType().getName();
		if(amount > 1 || amount == 0) current = current+"s";
		lore.add("§eYou have: "+current);
		meta.setLore(lore);
		NamespacedKey id = new NamespacedKey(PointShop.plugin, "id");
		meta.getPersistentDataContainer().set(id, PersistentDataType.STRING, t.getId());
		i.setItemMeta(meta);
		return i;
	}
}
