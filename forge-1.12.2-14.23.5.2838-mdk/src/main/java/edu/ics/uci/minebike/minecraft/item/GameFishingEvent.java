package edu.ics.uci.minebike.minecraft.item;

import edu.ics.uci.minebike.minecraft.client.AI.FishingAI;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.theawesomegem.fishingmadebetter.BetterFishUtil;
import net.theawesomegem.fishingmadebetter.common.capability.fishing.IFishingData;
import net.theawesomegem.fishingmadebetter.common.event.FishingEventHandler;
import net.theawesomegem.fishingmadebetter.util.ItemStackUtil;
import net.theawesomegem.fishingmadebetter.util.MathUtil;
import net.theawesomegem.fishingmadebetter.util.RandomUtil;

import java.util.List;
import java.util.Random;

public class GameFishingEvent extends FishingEventHandler {
    public FishingAI testing = new FishingAI();
    public GameFishingEvent(){
        super();
    }

    @Override
    @SubscribeEvent
    public void onPlayerFish(ItemFishedEvent e) {
        System.out.println("OnPlayerFish.......");
        EntityPlayer player = e.getEntityPlayer();
//      if (player != null && player.isUser().func_70089_S()) {
        if (player != null ) {
            System.out.println("null"+player.world.provider.getDimension());
            System.out.println("worldp"+WorldProviderFishing.dimID);
            World world = player.world;
            System.out.println("remote"+ world.isRemote);

            if (!world.isRemote && player.world.provider.getDimension()== WorldProviderFishing.DIM_ID) {

//                    IFishingData fishingData = (IFishingData)player.getCapability(FishingCapabilityProvider.FISHING_DATA_CAP, (EnumFacing)null);
//                    if (fishingData == null) {
//                        e.setCanceled(true);
//                    } else {
//                        IChunkFishingData chunkFishingData = (IChunkFishingData)world.getChunkFromBlockCoords(e.getHookEntity().getPosition()).getCapability(ChunkCapabilityProvider.CHUNK_FISHING_DATA_CAP, (EnumFacing)null);
//                        if (chunkFishingData == null) {
//                            e.setCanceled(true);
//                        } else if (!fishingData.isFishing()) {
//                            e.setCanceled(true);
//                        } else {
//                            FishCaughtData fishCaughtData = fishingData.getFishCaughtData();
//                            if (fishCaughtData == null) {
//                                e.setCanceled(true);
//                            } else {
//                                e.setCanceled(true);
//                                boolean canFish = true;
//                                if (ConfigurationManager.requireCorrectLine) {
//                                    int reelDiff = fishingData.getReelTarget() - fishingData.getReelAmount();
//                                    reelDiff = Math.abs(reelDiff);
//                                    canFish = reelDiff <= fishingData.getErrorVariance();
//                                }
//
//                                if (canFish && fishingData.getFishDistance() >= fishingData.getFishDeepLevel()) {
                                    System.out.println("Spawn fish.........");
                                    //TODO: test spawn_fish(), use one Item_stack, rather than the list
                                    ItemStack itemStack = testing.change_fish().getValue();
                                    System.out.println(itemStack.getDisplayName());
                                    EntityFishHook hook = e.getHookEntity();
                                    EntityItem entityitem = new EntityItem(player.world, hook.posX, hook.posY, hook.posZ, itemStack);
//                                    if (ConfigurationManager.magneticFishing) {
//                                        entityitem.setPosition(player.posX, player.posY+ 1.0D, player.posZ);
//                                    } else {
                                        double d0 = player.posX- hook.posX;
                                        double d1 = player.posY - hook.posY;
                                        double d2 = player.posZ - hook.posZ;
                                        double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                                        entityitem.posX = d0 * 0.1D;
                                        entityitem.posY = d1 * 0.1D + (double)MathHelper.sqrt(d3) * 0.08D;
                                        entityitem.posZ = d2 * 0.1D;
                                    //}

                                    //chunkFishingData.reducePopulation(fishCaughtData.fishId, 1, world.func_82737_E(), true);
                                    player.world.spawnEntity(entityitem);
                                    //player.field_70170_p.func_72838_d(entityitem);
                                    player.world.spawnEntity(new EntityXPOrb(player.world,player.posX,  player.posY + 0.5D, player.posZ+ 0.5D, 10));
//                                }
//
//                                fishingData.reset();
//                            }
//                        }
                    }
                }
            }
       // }

    //@Override
    //@SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
//        //System.out.println("onPlayerTick..........");
//
//        EntityPlayer player = e.player;
//        EntityFishHook hook = player.fishEntity;
//        if (!player.world.isRemote) {
//            //this.checkForFishInventory(player);
//            IFishingData fishingData = (IFishingData) player.getCapability(FishingCapabilityProvider.FISHING_DATA_CAP, (EnumFacing) null);
//            //System.out.println("IFishing Data"+fishingData);
////            if (fishingData != null) {
////                this.updateFishingData(player, fishingData);
//                if (hook != null && !hook.isDead) {
////                    if (this.usingVanillaFishingRod(player)) {
////                            fishingData.reset();
////                    } else {
////                        if (fishingData.getTimeBeforeFishSwimToHook() == 0) {
////                            if (this.getDelayBeforeSwimmingToHook(hook) > 20) {
////                                this.setLureSpeed(hook, 20);
////                            }
////                        } else {
//                            //this.setLureSpeed(hook, 200);
////                        }
//
////                        Enum hookState = this.getHookState(hook);
////                        if (hookState == null) {
////                            fishingData.reset();
////                        } else if (!hookState.equals(FishingEventHandler.HookState.BOBBING)) {
////                            fishingData.reset();
////                        } else {
//                            if (fishingData.isFishing()) {
//                                if (fishingData.getFishDistanceTime() <= 0) {
//                                    int distanceTime = this.getDistanceTime(fishingData.getFishWeight());
//                                    //ItemStack betterFishingRodStack = this.getBetterFishingRod(player);
//                                    ItemStack fish_testing= testing.fish_testing();
//                                    if (!fish_testing.isEmpty()) {
//                                        distanceTime -= ((ItemBetterFishingRod) fish_testing.func_77973_b()).getDragSpeed();
//                                    }
//
//                                    fishingData.setFishDistanceTime(distanceTime);
//                                    int reelDiff = fishingData.getReelTarget() - fishingData.getReelAmount();
//                                    reelDiff = Math.abs(reelDiff);
//                                    if (reelDiff <= fishingData.getErrorVariance()) {
//                                        fishingData.setFishDistance(fishingData.getFishDistance() + 1);
//                                    }
//                                } else {
//                                    fishingData.setFishDistanceTime(fishingData.getFishDistanceTime() - 1);
//                                }
//                            }
//
//                            World world = hook.field_70170_p;
//                            Chunk chunk = world.func_175726_f(hook.func_180425_c());
//                            IChunkFishingData chunkFishingData = this.getChunkFishingData(chunk);
//                            if (fishingData.getFishPopulation() == null) {
//                                fishingData.setFishPopulation(this.getSelectedFishPop(player, chunkFishingData));
//                                if (fishingData.getFishPopulation() == null) {
//                                    fishingData.reset();
//                                    return;
//                                }
//                            }
//
//                            if (fishingData.getTimeBeforeFishSwimToHook() == -1 && !fishingData.isFishing()) {
//                                fishingData.setTimeBeforeFishSwimToHook(this.getLureSpeed(player, fishingData.getFishPopulation()));
//                            }
//
//                            int delayBeforeBite = this.getDelayBeforeBite(hook);
//                            if (delayBeforeBite > 0 && delayBeforeBite <= 10) {
//                                if (fishingData.getLastFailedFishing() <= 0) {
//                                    if (!fishingData.isFishing()) {
//                                        player.field_70170_p.func_184148_a((EntityPlayer) null, player.field_70165_t, player.field_70163_u, player.field_70161_v, RegistryManager.RegistryHandler.FISH_SPLASHING_EVENT, SoundCategory.PLAYERS, 1.0F, 1.0F);
//                                    }
//
//                                    if (ConfigurationManager.looseBait) {
//                                        ItemStack fishingRodItem = this.getBetterFishingRod(player);
//                                        ItemBetterFishingRod.removeBait(fishingRodItem);
//                                    }
//
//                                    fishingData.startFishing(player.func_70681_au());
//                                }
//
//                                if (!this.canFishEscape(player, hook, fishingData)) {
//                                    this.spawnParticleBasedOnFishSpeed(player.field_70170_p, hook, fishingData);
//                                    this.setBiteInterval(hook, 20);
//                                } else {
//                                    this.setBiteInterval(hook, 0);
//                                    fishingData.setLastFailedFishing(20);
//                                    fishingData.reset();
//                                }
//                            } else {
//                                fishingData.reset(false);
//                            }
//
//                        }
//                    }
//                } else {
//                    fishingData.reset();
//                }
//            }
//        }

    private boolean shouldChangeReelAmount(Random random, int lastFishTime) {

        byte chance;
        if (MathUtil.inRange(lastFishTime, 560, 680)) {
            chance = 60;
        } else if (MathUtil.inRange(lastFishTime, 681, 860)) {
            chance = 40;
        } else if (MathUtil.inRange(lastFishTime, 861, 1025)) {
            chance = 28;
        } else if (MathUtil.inRange(lastFishTime, 1026, 1294)) {
            chance = 12;
        } else if (MathUtil.inRange(lastFishTime, 1295, 1470)) {
            chance = 6;
        } else {
            chance = 2;
        }

        int randPercChance = RandomUtil.getRandomInRange(random, 0, 100);
        return chance >= randPercChance;
    }
//    private FishingEventHandler.HookState getHookState(EntityFishHook hook) {
//        currentStateField.setAccessible(true);
//
//        try {
//            Enum hookEnum = (Enum)currentStateField.get(hook);
//            int hookStateOrdinal = hookEnum.ordinal();
//            currentStateField.setAccessible(false);
//            return FishingEventHandler.HookState.values()[hookStateOrdinal];
//        } catch (IllegalAccessException var4) {
//            var4.printStackTrace();
//            currentStateField.setAccessible(false);
//            return null;
//        }
//    }
//    private ItemStack getBetterFishingRod(EntityPlayer player) {
//        if (this.usingVanillaFishingRod(player)) {
//            return ItemStack.field_190927_a;
//        } else {
//            ItemStack mainHandItem = player.func_184614_ca();
//            ItemStack offHandItem = player.func_184592_cb();
//            if (mainHandItem.func_190926_b() && offHandItem.func_190926_b()) {
//                return ItemStack.field_190927_a;
//            } else if (mainHandItem.func_77973_b() instanceof ItemBetterFishingRod) {
//                return mainHandItem;
//            } else {
//                return offHandItem.func_77973_b() instanceof ItemBetterFishingRod ? offHandItem : ItemStack.field_190927_a;
//            }
//        }
//    }
    private void updateReelAmount(Random random, IFishingData fishingData, int tugging) {
        if (this.shouldChangeReelAmount(random, fishingData.getLastFishTime())) {
            boolean shouldAdd = random.nextBoolean();
            if (shouldAdd) {
                fishingData.setReelAmount(fishingData.getReelAmount() + tugging);
            } else {
                fishingData.setReelAmount(fishingData.getReelAmount() - tugging);
            }

        }
    }
//    private void updateFishingData(EntityPlayer player, IFishingData fishingData) {
//        if (fishingData.getLastFailedFishing() > 0) {
//            fishingData.setLastFailedFishing(fishingData.getLastFailedFishing() - 1);
//        }
//
//        if (fishingData.getTimeBeforeFishSwimToHook() > 0) {
//            fishingData.setTimeBeforeFishSwimToHook(fishingData.getTimeBeforeFishSwimToHook() - 1);
//        }
//
//        if (fishingData.isFishing()) {
//            int tugging = 3;
//            ItemStack betterFishingRodStack = this.getBetterFishingRod(player);
//            if (!betterFishingRodStack.func_190926_b()) {
//                tugging = ((ItemBetterFishingRod)betterFishingRodStack.func_77973_b()).getTuggingAmount();
//            }
//
//            this.updateReelAmount(player.func_70681_au(), fishingData, tugging);
//            fishingData.setFishTime(fishingData.getFishTime() - 1);
//            if (fishingData.getFishTime() < 0) {
//                fishingData.setFishTime(0);
//            }
//        }
//
//        PrimaryPacketHandler.INSTANCE.sendTo(new PacketReelingC(fishingData.getReelAmount(), fishingData.getReelTarget(), fishingData.getFishDistance(), fishingData.getFishDeepLevel(), fishingData.isFishing()), (EntityPlayerMP)player);
//    }
    private void checkForFishInventory(EntityPlayer player) {
        InventoryPlayer playerInventory = player.inventory;
        long currentTime = player.world.getWorldTime();

        for(int i = 0; i < playerInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = playerInventory.getStackInSlot(i);
            if (BetterFishUtil.isBetterFish(itemStack) && BetterFishUtil.isDead(itemStack, currentTime)) {
                List<String> loreList = ItemStackUtil.getToolTip(itemStack);
                if (loreList.size() > 0) {
                    loreList.remove(loreList.size() - 1);
                }

                ItemStackUtil.appendToolTip(itemStack, loreList);
                BetterFishUtil.setFishCaughtTime(itemStack, 0);
            }
        }

    }
//    private void setLureSpeed(EntityFishHook hook, int ticks) {
//        if (delayBeforeSwimmingToHookField != null) {
//            delayBeforeSwimmingToHookField.setAccessible(true);
//
//            try {
//                delayBeforeSwimmingToHookField.setInt(hook, ticks);
//                delayBeforeSwimmingToHookField.setAccessible(false);
//            } catch (IllegalAccessException | IllegalArgumentException var4) {
//                delayBeforeSwimmingToHookField.setAccessible(false);
//                var4.printStackTrace();
//                delayBeforeSwimmingToHookField.setAccessible(false);
//            }
//        }
//    }

//    static enum HookState {
//        FLYING,
//        HOOKED_IN_ENTITY,
//        BOBBING;
//
//        private HookState() {
//        }
//    }
}
