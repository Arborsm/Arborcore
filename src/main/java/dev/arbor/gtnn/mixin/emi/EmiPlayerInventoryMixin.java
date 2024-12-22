// package dev.arbor.gtnn.mixin.emi;
//
// import com.gregtechceu.gtceu.api.item.tool.GTToolItem;
// import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
// import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
// import dev.arbor.gtnn.GTNN;
// import dev.emi.emi.api.recipe.EmiPlayerInventory;
// import dev.emi.emi.api.stack.EmiStack;
// import net.minecraft.world.item.ItemStack;
// import org.spongepowered.asm.mixin.Mixin;
// import org.spongepowered.asm.mixin.Unique;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Inject;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
// @Mixin(value = EmiPlayerInventory.class, remap = false)
// public abstract class EmiPlayerInventoryMixin {
//
// @Inject(method = "addStack(Ldev/emi/emi/api/stack/EmiStack;)V", at = @At(value = "INVOKE", target =
// "Ljava/util/Map;merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;"))
// private void modifyAmount(EmiStack stack, CallbackInfo ci) {
// if (GTNN.INSTANCE.getServerConfig().makesEMIBetter) {
// stack.setAmount(gtnn$getDamageAvailable(stack));
// }
// }
//
// @WrapOperation(method = "canCraft(Ldev/emi/emi/api/recipe/EmiRecipe;J)Z", at = @At(value = "INVOKE", target =
// "Ldev/emi/emi/api/stack/EmiStack;getAmount()J", ordinal = 0))
// private long modifyAmount(EmiStack instance, Operation<Long> original) {
// if (GTNN.INSTANCE.getServerConfig().makesEMIBetter) {
// return gtnn$getDamagePerCraft(instance);
// } else {
// return original.call(instance);
// }
// }
//
// @Unique
// private static long gtnn$getDamageAmount(EmiStack emiStack, boolean per) {
// ItemStack newStack = emiStack.getItemStack().copy().split(1);
// ItemStack remainder = newStack.getCraftingRemainingItem();
// if (newStack.getItem() instanceof GTToolItem gtToolItem) {
// int dDamage = gtToolItem.getToolStats().getToolDamagePerCraft(remainder) -
// gtToolItem.getToolStats().getToolDamagePerCraft(newStack);
// if (dDamage <= 0) return emiStack.getAmount();
// return per ? dDamage : (gtToolItem.getMaxDamage(newStack) -
// gtToolItem.getToolStats().getToolDamagePerCraft(emiStack.getItemStack())) / dDamage;
// }
// return emiStack.getAmount();
// }
//
// @Unique
// private static long gtnn$getDamagePerCraft(EmiStack emiStack) {
// return gtnn$getDamageAmount(emiStack, true);
// }
//
// @Unique
// private static long gtnn$getDamageAvailable(EmiStack emiStack) {
// return gtnn$getDamageAmount(emiStack, false);
// }
// }
//
