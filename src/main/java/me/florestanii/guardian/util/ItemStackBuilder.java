package me.florestanii.guardian.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A convenient builder for {@link org.bukkit.inventory.ItemStack}s.
 */
public class ItemStackBuilder {
    private int type;
    private int amount = 1;
    private int damage = 0;
    private int data = 0;
    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private PotionEffect potionEffect;

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment) {
        if (enchantment == null) {
            enchantments = new HashMap<>();
        }
        return addEnchantment(enchantment, 1);
    }

    public ItemStackBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public ItemStackBuilder setType(Material type) {
        this.type = type.getId();
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public ItemStackBuilder setData(int data) {
        this.data = data;
        return this;
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemStackBuilder setLore(String lore) {
        this.lore = Collections.singletonList(lore);
        return this;
    }

    public ItemStackBuilder setPotionEffect(PotionEffect potionEffect) {
        this.potionEffect = potionEffect;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(type, amount, (short) damage, (byte) data);

        ItemMeta meta = itemStack.getItemMeta();
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        if (lore != null) {
            meta.setLore(lore);
        }
        if (meta instanceof PotionMeta && potionEffect != null) {
            ((PotionMeta) meta).addCustomEffect(potionEffect, false);
        }
        itemStack.setItemMeta(meta);

        if (enchantments != null) {
            itemStack.addUnsafeEnchantments(enchantments);
        }

        return itemStack;
    }

    public static ItemStackBuilder builder() {
        return new ItemStackBuilder();
    }

    public static ItemStackBuilder createStack(Material material) {
        return builder().setType(material);
    }

    public static ItemStackBuilder createPotion(int metadata) {
        return builder().setType(Material.POTION).setDamage(metadata);
    }
}
