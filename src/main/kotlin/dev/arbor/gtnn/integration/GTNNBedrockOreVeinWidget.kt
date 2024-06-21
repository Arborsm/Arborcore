package dev.arbor.gtnn.integration

import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import com.lowdragmc.lowdraglib.jei.IngredientIO
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.neoforged.neoforge.items.ItemStackHandler

class GTNNBedrockOreVeinWidget(oreVeins: Pair<ResourceKey<Level>, HashMap<ItemStack, Int>>) :
    WidgetGroup(0, 0, 166, 160) {
    private val dimension: String = oreVeins.first.location().toString()
    private val ores: HashMap<ItemStack, Int> = oreVeins.second

    init {
        setClientSideWidget()
        setupGui(ores)
        //setupText()
    }

    private fun setupGui(ores: HashMap<ItemStack, Int>) {
        val oresList: NonNullList<ItemStack> = NonNullList.create()
        val chances = ores.entries.map { it.value }
        oresList.addAll(ores.keys)
        var x = 4
        var y = 4
        val n = oresList.size
        val scrollableWidgetGroup = DraggableScrollableWidgetGroup(4, 4, 158, 120)
            .setYScrollBarWidth(4)
            .setYBarStyle(GuiTextures.SLIDER_BACKGROUND_VERTICAL, GuiTextures.BUTTON)
            .setScrollable(true)
            .setDraggable(true)
        addWidget(scrollableWidgetGroup)
        for (i in 0 until n) {
            val chance: Float = chances[i].toFloat() / 100f
            val oreSlot = SlotWidget(ItemStackHandler(oresList), i, x, y, false, false)
            oreSlot.setIngredientIO(IngredientIO.OUTPUT)
            oreSlot.setOverlay { graphics: GuiGraphics, _: Int, _: Int, xOff: Float, yOff: Float, width: Int, height: Int ->
                graphics.pose().pushPose()
                graphics.pose().translate(0f, 0f, 400f)
                graphics.pose().scale(0.5f, 0.5f, 1f)

                val s = "${chance}%"
                val color = 0xFFFF00
                val fontRenderer = Minecraft.getInstance().font
                graphics.drawString(
                    fontRenderer,
                    s,
                    ((xOff + (width / 3f)) * 2 - fontRenderer.width(s) + 23).toInt(),
                    ((yOff + (height / 3f) + 6) * 2 - height).toInt(),
                    color,
                    true
                )
                graphics.pose().popPose()
            }
            scrollableWidgetGroup.addWidget(oreSlot)
            x += 18
            if (x > 144) {
                x = 4
                y += 18
            }
        }
        addWidget(LabelWidget(4, 140, Component.translatable("gtnn.jei.bedrock_ores.dimension", dimension)))
    }
}