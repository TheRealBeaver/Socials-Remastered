package me.justalorenzo.socials.gui;


import com.google.common.collect.BiMap;

import com.google.inject.Inject;
import me.justalorenzo.socials.Socials;
import me.justalorenzo.socials.customheads.CustomHeads;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.Collections;
import java.util.Map;


public class CreateGUI {


    private Socials plugin;

    @Inject
    public CreateGUI(Socials plugin) {
        this.plugin = plugin;
    }


    Inventory socialsInventory;
    int GUISize;







    //sets basic GUI information
    public void setGUI(int GUISize, String GUITitle) {
        if (GUISize % 9 != 0) {
            this.GUISize = 9;
            //default it
        } else {
            this.GUISize = GUISize;
        }

        this.socialsInventory = Bukkit.createInventory(null, GUISize, GUITitle);

    }


    public void addItem(BiMap<String, String> name_value) {
        int position = 8;

        /*Get mapping value by key,
        myBiMap.get("key");
        Get mapping by value,
        myBiMap.inverse().get("value");*/
        boolean isLast = false;
        for (Map.Entry<String, String> content : name_value.entrySet()) {

            String base64 = content.getValue();
            String commandName = ChatColor.stripColor(content.getKey());
            String availabilityPath = commandName.toLowerCase() + ".available";
            boolean isAvailable = plugin.getConfig().getBoolean(availabilityPath);

            //for example .create YouTube
            ItemStack head = CustomHeads.create(base64);
            ItemMeta headMeta = head.getItemMeta();
            headMeta.setDisplayName(content.getKey());
            if(isAvailable) {
                headMeta.setLore(Collections.singletonList(ChatColor.GREEN + "Available!"));
            }
            else {
                headMeta.setLore(Collections.singletonList(ChatColor.RED + "Unavailable!"));
            }

            head.setItemMeta(headMeta);
            position += 2;
            if (position == 18) {
                position += 10;
                socialsInventory.setItem(position, head);
            } else {
                socialsInventory.setItem(position, head);
                if (position == 32) {
                    isLast = true;
                }
            }


        }
        if (isLast) {
            ItemStack head = CustomHeads.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQzYTNiY2JiZTRmZGEwY2ZjNTM4MGJhOGRiMThkMGRiZGU5NTNmODAzNTM3NmY2YjRkMzk3OWY2ODRkNWIwZCJ9fX0=");
            ItemMeta headMeta = head.getItemMeta();
            headMeta.setDisplayName(ChatColor.WHITE +"Linktree");
            headMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Check out our other links..."));
            head.setItemMeta(headMeta);
            socialsInventory.setItem(34, head);
        }


    }

    public void fillGUI() {

        ItemStack black_glass_pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta black_glass_paneMeta = black_glass_pane.getItemMeta();

        black_glass_paneMeta.setDisplayName(ChatColor.stripColor("Pane"));
        black_glass_pane.setItemMeta(black_glass_paneMeta);
        for (int i = 0; i < 54; i++) {
            socialsInventory.setItem(i, black_glass_pane);
        }

    }


    public Inventory showGUI() {
        return socialsInventory;
    }


}
