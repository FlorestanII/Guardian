package me.florestanii.guardian.listerners;

import de.craften.plugins.mcguilib.text.TextBuilder;
import me.florestanii.guardian.util.ItemStackBuilder;
import me.florestanii.guardian.util.merchant.Merchant;
import me.florestanii.guardian.util.merchant.MerchantOffer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerShopHandler implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player && e.getInventory().getName().equals("Shop")) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);
            player.closeInventory();

            switch (e.getCurrentItem().getType()) {
                case IRON_PICKAXE:
                    showPickaxeMerchant(player);
                    break;
                case CHAINMAIL_CHESTPLATE:
                    showArmorMerchant(player);
                    break;
                case BOW:
                    showBowMerchant(player);
                    break;
                case APPLE:
                    showFootMerchant(player);
                    break;
                case ENDER_PEARL:
                    showSpecialMerchant(player);
                    break;
                case POTION:
                    showPotionMerchant(player);
                    break;
                case IRON_SWORD:
                    showWeaponsMerchant(player);
                    break;
            }
        }
    }

    private void showPotionMerchant(Player player) {
        Merchant potionMerchant = new Merchant();
        potionMerchant.setTitle("Potions");

        MerchantOffer potion_strength = new MerchantOffer(
                netherStars(2),
                ItemStackBuilder.createPotion(8201)
                        .setPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 120, 0))
                        .build());

        MerchantOffer potion_speed = new MerchantOffer(
                emeralds(32),
                ItemStackBuilder.createPotion(8194)
                        .setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 0))
                        .build());

        MerchantOffer potion_healing = new MerchantOffer(
                emeralds(32),
                ItemStackBuilder.createPotion(16453)
                        .setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 0, 0))
                        .build());

        MerchantOffer potion_healing2 = new MerchantOffer(
                diamonds(2),
                ItemStackBuilder.createPotion(16453)
                        .setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 0, 1))
                        .build());

        potionMerchant.addOffers(potion_strength, potion_speed, potion_healing, potion_healing2);

        potionMerchant.setCustomer(player);
        potionMerchant.openTrading(player);
    }

    private void showSpecialMerchant(Player player) {
        Merchant specialMerchant = new Merchant();
        specialMerchant.setTitle("Special");

        MerchantOffer enderpearl = new MerchantOffer(
                diamonds(13),
                new ItemStack(Material.ENDER_PEARL, 1));

        MerchantOffer fishing_rod = new MerchantOffer(
                diamonds(2),
                new ItemStack(Material.FISHING_ROD, 1));

        MerchantOffer teleportPowder = new MerchantOffer(
                emeralds(20),
                ItemStackBuilder.createStack(Material.SULPHUR)
                        .setAmount(1)
                        .setDisplayName("Teleport Powder")
                        .setLore(TextBuilder
                                .create("This magic powder will teleport you to your base after 6 seconds.")
                                .gray().getLines())
                        .build());

        specialMerchant.addOffers(enderpearl, fishing_rod, teleportPowder);

        specialMerchant.setCustomer(player);
        specialMerchant.openTrading(player);
    }

    private void showWeaponsMerchant(Player player) {
        Merchant weaponsMerchant = new Merchant();
        weaponsMerchant.setTitle("Weapons");

        MerchantOffer sword1 = new MerchantOffer(
                emeralds(8),
                ItemStackBuilder.createStack(Material.GOLD_SWORD)
                        .setDisplayName("Sword 1")
                        .build());

        MerchantOffer sword2 = new MerchantOffer(
                emeralds(64),
                ItemStackBuilder.createStack(Material.GOLD_SWORD)
                        .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                        .setDisplayName("Sword 2")
                        .build());

        MerchantOffer sword3 = new MerchantOffer(
                diamonds(5),
                ItemStackBuilder.createStack(Material.GOLD_SWORD)
                        .addEnchantment(Enchantment.DAMAGE_ALL, 2)
                        .setDisplayName("Sword 3")
                        .build());


        MerchantOffer sword4 = new MerchantOffer(
                netherStars(2),
                ItemStackBuilder.createStack(Material.GOLD_SWORD)
                        .addEnchantment(Enchantment.DAMAGE_ALL, 3)
                        .setDisplayName("Sword 2")
                        .build());

        weaponsMerchant.addOffers(sword1, sword2, sword3, sword4);

        weaponsMerchant.setCustomer(player);
        weaponsMerchant.openTrading(player);
    }

    private void showFootMerchant(Player player) {
        Merchant foodMerchant = new Merchant();
        foodMerchant.setTitle("Food");

        MerchantOffer apple = new MerchantOffer(emeralds(1), new ItemStack(Material.APPLE, 1));
        MerchantOffer cooked_beef = new MerchantOffer(emeralds(1), new ItemStack(Material.COOKED_BEEF, 1));
        MerchantOffer golden_apple = new MerchantOffer(diamonds(1), new ItemStack(Material.GOLDEN_APPLE, 1));

        foodMerchant.addOffer(apple);
        foodMerchant.addOffer(cooked_beef);
        foodMerchant.addOffer(golden_apple);

        foodMerchant.setCustomer(player);
        foodMerchant.openTrading(player);
    }

    private void showArmorMerchant(Player player) {
        Merchant armorMerchant = new Merchant();
        armorMerchant.setTitle("Armor");

        MerchantOffer boots = new MerchantOffer(emeralds(5), new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        MerchantOffer leggings = new MerchantOffer(emeralds(5), new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));

        MerchantOffer chestplate1 = new MerchantOffer(
                emeralds(16),
                ItemStackBuilder.createStack(Material.IRON_CHESTPLATE)
                        .setDisplayName("Chestplate 1")
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .build());

        MerchantOffer chestplate2 = new MerchantOffer(
                diamonds(3),
                ItemStackBuilder.createStack(Material.IRON_CHESTPLATE)
                        .setDisplayName("Chestplate 2")
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                        .build());

        MerchantOffer chestplate3 = new MerchantOffer(
                diamonds(7),
                ItemStackBuilder.createStack(Material.IRON_CHESTPLATE)
                        .setDisplayName("Chestplate 3")
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                        .build());

        armorMerchant.addOffers(leggings, boots);
        armorMerchant.addOffers(chestplate1, chestplate2, chestplate3);

        armorMerchant.setCustomer(player);
        armorMerchant.openTrading(player);
    }

    private void showPickaxeMerchant(Player player) {
        Merchant pickaxeMerchant = new Merchant();
        pickaxeMerchant.setTitle("Pickaxes");

        MerchantOffer pickaxe1 = new MerchantOffer(
                emeralds(4),
                ItemStackBuilder.createStack(Material.WOOD_PICKAXE)
                        .setDisplayName("Pickaxe 1")
                        .build());

        MerchantOffer pickaxe2 = new MerchantOffer(
                emeralds(16),
                ItemStackBuilder.createStack(Material.STONE_PICKAXE)
                        .setDisplayName("Pickaxe 2")
                        .build());

        MerchantOffer pickaxe3 = new MerchantOffer(
                diamonds(1),
                ItemStackBuilder.createStack(Material.GOLD_PICKAXE)
                        .setDisplayName("Pickaxe 3")
                        .build());

        MerchantOffer pickaxe4 = new MerchantOffer(
                netherStars(1),
                ItemStackBuilder.createStack(Material.IRON_PICKAXE)
                        .addEnchantment(Enchantment.DIG_SPEED, 2)
                        .setDisplayName("Pickaxe 4")
                        .build());

        pickaxeMerchant.addOffers(pickaxe1, pickaxe2, pickaxe3, pickaxe4);

        pickaxeMerchant.setCustomer(player);
        pickaxeMerchant.openTrading(player);
    }

    private void showBowMerchant(Player player) {
        Merchant bowMerchant = new Merchant();
        bowMerchant.setTitle("Bow/Pickaxe");

        MerchantOffer bow1 = new MerchantOffer(
                diamonds(3),
                ItemStackBuilder.createStack(Material.BOW)
                        .setDisplayName("Bow 1")
                        .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                        .build());

        MerchantOffer bow2 = new MerchantOffer(
                diamonds(7),
                ItemStackBuilder.createStack(Material.BOW)
                        .setDisplayName("Bow 2")
                        .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                        .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                        .build());

        MerchantOffer bow3 = new MerchantOffer(
                diamonds(11),
                ItemStackBuilder.createStack(Material.BOW)
                        .setDisplayName("Bow 3")
                        .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                        .addEnchantment(Enchantment.ARROW_DAMAGE, 2)
                        .build());

        MerchantOffer bow4 = new MerchantOffer(
                netherStars(2),
                ItemStackBuilder.createStack(Material.BOW)
                        .setDisplayName("Bow 4")
                        .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                        .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                        .addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
                        .build());

        MerchantOffer arrow = new MerchantOffer(
                diamonds(1),
                ItemStackBuilder.createStack(Material.ARROW).build());

        bowMerchant.addOffers(bow1, bow2, bow3, bow4, arrow);

        bowMerchant.setCustomer(player);
        bowMerchant.openTrading(player);
    }

    private ItemStack diamonds(int amount) {
        return new ItemStack(Material.DIAMOND, amount);
    }

    private ItemStack emeralds(int amount) {
        return new ItemStack(Material.EMERALD, amount);
    }

    private ItemStack netherStars(int amount) {
        return new ItemStack(Material.NETHER_STAR, amount, (short) 192);
    }
}
