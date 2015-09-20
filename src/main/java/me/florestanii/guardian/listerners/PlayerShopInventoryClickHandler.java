package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.util.Util;
import me.florestanii.guardian.util.merchant.Merchant;
import me.florestanii.guardian.util.merchant.MerchantOffer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerShopInventoryClickHandler implements Listener {
    private final Guardian plugin;

    public PlayerShopInventoryClickHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getName().equals("Shop")) {
            e.setCancelled(true);
            p.closeInventory();

            switch (e.getCurrentItem().getType()) {
                case IRON_PICKAXE:

                    Merchant pickaxeMerchant = new Merchant();
                    pickaxeMerchant.setTitle("Pickaxes");

                    MerchantOffer pickaxe1 = new MerchantOffer(getItemStackForOffer(Material.EMERALD, 4, (byte) 0, null, null), getItemStackForOffer(Material.WOOD_PICKAXE, 1, (byte) 0, "Spitzhacke 1", null));
                    MerchantOffer pickaxe2 = new MerchantOffer(getItemStackForOffer(Material.EMERALD, 16, (byte) 0, null, null), getItemStackForOffer(Material.STONE_PICKAXE, 1, (byte) 0, "Spitzhacke 2", null));
                    MerchantOffer pickaxe3 = new MerchantOffer(getItemStackForOffer(Material.DIAMOND, 1, (byte) 0, null, null), getItemStackForOffer(Material.GOLD_PICKAXE, 1, (byte) 0, "Spitzhacke 3", null));
                    HashMap<Enchantment, Integer> enchsPickaxe4 = new HashMap<Enchantment, Integer>();
                    enchsPickaxe4.put(Enchantment.DIG_SPEED, 2);
                    MerchantOffer pickaxe4 = new MerchantOffer(getItemStackForOffer(Material.NETHER_STAR, 1, (byte) 246, null, null), getItemStackForOffer(Material.IRON_PICKAXE, 1, "Spitzhacke 4", enchsPickaxe4));

                    pickaxeMerchant.addOffer(pickaxe1);
                    pickaxeMerchant.addOffer(pickaxe2);
                    pickaxeMerchant.addOffer(pickaxe3);
                    pickaxeMerchant.addOffer(pickaxe4);

                    pickaxeMerchant.setCustomer(p);
                    pickaxeMerchant.openTrading(p);

                    break;
                case CHAINMAIL_CHESTPLATE:

                    Merchant armorMerchant = new Merchant();
                    armorMerchant.setTitle("Armor");

                    MerchantOffer boots = new MerchantOffer(new ItemStack(Material.EMERALD, 5), new ItemStack(Material.CHAINMAIL_BOOTS, 1));
                    MerchantOffer leggings = new MerchantOffer(new ItemStack(Material.EMERALD, 5), new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));

                    HashMap<Enchantment, Integer> enchsChestplate1 = new HashMap<Enchantment, Integer>();
                    enchsChestplate1.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    MerchantOffer chestplate1 = new MerchantOffer(new ItemStack(Material.EMERALD, 16), getItemStackForOffer(Material.IRON_CHESTPLATE, 1, "Chestplate 1", enchsChestplate1));

                    HashMap<Enchantment, Integer> enchsChestplate2 = new HashMap<Enchantment, Integer>();
                    enchsChestplate2.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    MerchantOffer chestplate2 = new MerchantOffer(new ItemStack(Material.DIAMOND, 3), getItemStackForOffer(Material.IRON_CHESTPLATE, 1, "Chestplate 2", enchsChestplate2));

                    HashMap<Enchantment, Integer> enchsChestplate3 = new HashMap<Enchantment, Integer>();
                    enchsChestplate3.put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                    MerchantOffer chestplate3 = new MerchantOffer(new ItemStack(Material.DIAMOND, 7), getItemStackForOffer(Material.IRON_CHESTPLATE, 1, "Chestplate 3", enchsChestplate3));

                    armorMerchant.addOffer(leggings);
                    armorMerchant.addOffer(boots);

                    armorMerchant.addOffer(chestplate1);
                    armorMerchant.addOffer(chestplate2);
                    armorMerchant.addOffer(chestplate3);

                    armorMerchant.setCustomer(p);
                    armorMerchant.openTrading(p);

                    break;
                case BOW:

                    Merchant bowMerchant = new Merchant();
                    bowMerchant.setTitle("Bow/Pickaxe");

                    HashMap<Enchantment, Integer> enchsBow1 = new HashMap<Enchantment, Integer>();
                    enchsBow1.put(Enchantment.ARROW_INFINITE, 1);
                    MerchantOffer bow1 = new MerchantOffer(getItemStackForOffer(Material.DIAMOND, 3, (byte) 0, null, null), getItemStackForOffer(Material.BOW, 1, "Bow 1", enchsBow1));
                    HashMap<Enchantment, Integer> enchsBow2 = new HashMap<Enchantment, Integer>();
                    enchsBow2.put(Enchantment.ARROW_INFINITE, 1);
                    enchsBow2.put(Enchantment.ARROW_DAMAGE, 1);
                    MerchantOffer bow2 = new MerchantOffer(getItemStackForOffer(Material.DIAMOND, 7, (byte) 0, null, null), getItemStackForOffer(Material.BOW, 1, "Bow 2", enchsBow2));
                    HashMap<Enchantment, Integer> enchsBow3 = new HashMap<Enchantment, Integer>();
                    enchsBow3.put(Enchantment.ARROW_INFINITE, 1);
                    enchsBow3.put(Enchantment.ARROW_DAMAGE, 2);
                    MerchantOffer bow3 = new MerchantOffer(getItemStackForOffer(Material.DIAMOND, 11, (byte) 0, null, null), getItemStackForOffer(Material.BOW, 1, "Bow 3", enchsBow3));
                    HashMap<Enchantment, Integer> enchsBow4 = new HashMap<Enchantment, Integer>();
                    enchsBow4.put(Enchantment.ARROW_INFINITE, 1);
                    enchsBow4.put(Enchantment.ARROW_DAMAGE, 3);
                    enchsBow4.put(Enchantment.ARROW_KNOCKBACK, 1);
                    MerchantOffer bow4 = new MerchantOffer(getItemStackForOffer(Material.NETHER_STAR, 2, (byte) 192, null, null), getItemStackForOffer(Material.BOW, 1, "Bow 4", enchsBow4));
                    MerchantOffer arrow = new MerchantOffer(getItemStackForOffer(Material.DIAMOND, 1, (byte) 0, null, null), getItemStackForOffer(Material.ARROW, 1, (byte) 0, null, null));

                    bowMerchant.addOffer(bow1);
                    bowMerchant.addOffer(bow2);
                    bowMerchant.addOffer(bow3);
                    bowMerchant.addOffer(bow4);
                    bowMerchant.addOffer(arrow);

                    bowMerchant.setCustomer(p);
                    bowMerchant.openTrading(p);

                    break;
                case APPLE:

                    Merchant foodMerchant = new Merchant();
                    foodMerchant.setTitle("Food/Special");

                    MerchantOffer apple = new MerchantOffer(new ItemStack(Material.EMERALD, 1), new ItemStack(Material.APPLE, 1));
                    MerchantOffer cooked_beef = new MerchantOffer(new ItemStack(Material.EMERALD, 2), new ItemStack(Material.COOKED_BEEF, 1));
                    MerchantOffer golden_apple = new MerchantOffer(new ItemStack(Material.DIAMOND, 1), new ItemStack(Material.GOLDEN_APPLE, 1));

                    foodMerchant.addOffer(apple);
                    foodMerchant.addOffer(cooked_beef);
                    foodMerchant.addOffer(golden_apple);

                    foodMerchant.setCustomer(p);
                    foodMerchant.openTrading(p);

                    break;
                case ENDER_PEARL:

                    Merchant specialMerchant = new Merchant();
                    specialMerchant.setTitle("Special");

                    MerchantOffer enderpearl = new MerchantOffer(new ItemStack(Material.DIAMOND, 13), new ItemStack(Material.ENDER_PEARL, 1));
                    MerchantOffer fishing_rod = new MerchantOffer(new ItemStack(Material.DIAMOND, 2), new ItemStack(Material.FISHING_ROD, 1));
                    MerchantOffer teleportPowder = new MerchantOffer(new ItemStack(Material.EMERALD, 20), Util.renameItemStack(Util.addLoreLineToItemStack(new ItemStack(Material.SULPHUR, 1), ChatColor.GRAY + "This item teleports you after 6 seconds to your own base!"), "Teleport Powder"));

                    specialMerchant.addOffer(enderpearl);
                    specialMerchant.addOffer(fishing_rod);
                    specialMerchant.addOffer(teleportPowder);

                    specialMerchant.setCustomer(p);
                    specialMerchant.openTrading(p);

                    break;
                case POTION:

                    Merchant potionMerchant = new Merchant();
                    potionMerchant.setTitle("Potions");

                    MerchantOffer potion_strength = new MerchantOffer(new ItemStack(Material.NETHER_STAR, 2), Util.getPotion(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 120, 0), 8201));

                    MerchantOffer potion_speed = new MerchantOffer(new ItemStack(Material.EMERALD, 32), Util.getPotion(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 0), 8194));

                    MerchantOffer potion_healing = new MerchantOffer(new ItemStack(Material.EMERALD, 32), Util.getPotion(new PotionEffect(PotionEffectType.HEAL, 20 * 0, 0), 16453));
                    MerchantOffer potion_healing2 = new MerchantOffer(new ItemStack(Material.DIAMOND, 2), Util.getPotion(new PotionEffect(PotionEffectType.HEAL, 20 * 0, 1), 16453));

                    potionMerchant.addOffer(potion_strength);
                    potionMerchant.addOffer(potion_speed);
                    potionMerchant.addOffer(potion_healing);
                    potionMerchant.addOffer(potion_healing2);

                    potionMerchant.setCustomer(p);
                    potionMerchant.openTrading(p);

                    break;
                case IRON_SWORD:

                    Merchant weaponsMerchant = new Merchant();
                    weaponsMerchant.setTitle("Weapons");

                    MerchantOffer sword1 = new MerchantOffer(getItemStackForOffer(Material.EMERALD, 8, (byte) 0, null, null), getItemStackForOffer(Material.GOLD_SWORD, 1, "Sword 1", null));

                    HashMap<Enchantment, Integer> enchsSword2 = new HashMap<Enchantment, Integer>();
                    enchsSword2.put(Enchantment.DAMAGE_ALL, 1);
                    MerchantOffer sword2 = new MerchantOffer(getItemStackForOffer(Material.EMERALD, 64, (byte) 0, null, null), getItemStackForOffer(Material.GOLD_SWORD, 1, "Sword 2", enchsSword2));

                    HashMap<Enchantment, Integer> enchsSword3 = new HashMap<Enchantment, Integer>();
                    enchsSword3.put(Enchantment.DAMAGE_ALL, 2);
                    MerchantOffer sword3 = new MerchantOffer(getItemStackForOffer(Material.DIAMOND, 5, (byte) 0, null, null), getItemStackForOffer(Material.GOLD_SWORD, 1, "Sword 3", enchsSword3));

                    HashMap<Enchantment, Integer> enchsSword4 = new HashMap<Enchantment, Integer>();
                    enchsSword4.put(Enchantment.DAMAGE_ALL, 3);
                    MerchantOffer sword4 = new MerchantOffer(getItemStackForOffer(Material.NETHER_STAR, 2, (byte) 0, null, null), getItemStackForOffer(Material.GOLD_SWORD, 1, "Sword 4", enchsSword4));

                    weaponsMerchant.addOffer(sword1);
                    weaponsMerchant.addOffer(sword2);
                    weaponsMerchant.addOffer(sword3);
                    weaponsMerchant.addOffer(sword4);

                    weaponsMerchant.setCustomer(p);
                    weaponsMerchant.openTrading(p);

                    break;

                default:
                    break;
            }
        }

    }

    public ItemStack getItemStackForOffer(Material mat, int amount, byte damage, String displayname, List<String> lore) {

        ItemStack item = new ItemStack(mat, amount, (short) damage);
        ItemMeta meta = item.getItemMeta();

        if (displayname != null) {
            meta.setDisplayName(displayname);
        }

        if (lore != null) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack getItemStackForOffer(Material mat, int amount, String displayname, Map<Enchantment, Integer> enchs) {

        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        if (displayname != null) {
            meta.setDisplayName(displayname);
            item.setItemMeta(meta);
        }

        if (enchs != null) {
            item.addUnsafeEnchantments(enchs);
        }

        return item;

    }
}
