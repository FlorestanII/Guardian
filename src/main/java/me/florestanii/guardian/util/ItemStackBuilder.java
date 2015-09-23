package me.florestanii.guardian.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.*;

/**
 * A convenient builder for {@link org.bukkit.inventory.ItemStack}s.
 */
public class ItemStackBuilder {
    private int type;
    private int amount = 1;
    private int damage = 0;
    private Integer data;
    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private ArrayList<PotionEffect> potionEffects;

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        if (enchantments == null) {
            enchantments = new HashMap<>();
        }
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment) {
        if (enchantments == null) {
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

    public ItemStackBuilder addPotionEffect(PotionEffect potionEffect) {
        if (potionEffects == null) {
            potionEffects = new ArrayList<>();
        }
        potionEffects.add(potionEffect);
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack;
        if (data != null) {
            itemStack = new ItemStack(type, amount, (short) damage, data.byteValue());
        } else {
            itemStack = new ItemStack(type, amount, (short) damage);
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        if (lore != null) {
            meta.setLore(lore);
        }
        if (meta instanceof PotionMeta && potionEffects != null) {
            for (PotionEffect potionEffect : potionEffects) {
                ((PotionMeta) meta).addCustomEffect(potionEffect, false);
            }
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

    /**
     * Create a potion with the given type. Note that the type only controls the bottle color.
     *
     * @param type type (only controls the bottle color)
     * @return builder for the potion
     */
    public static ItemStackBuilder createPotion(PotionType type) {
        return builder().setType(Material.POTION).setDamage(type.getDamageValue());
    }
}
