package dev.arbor.gtnn.client

import com.mojang.blaze3d.systems.RenderSystem
import dev.arbor.gtnn.GTNN.id
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.player.Player
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import java.util.*

// Code Source: https://github.com/SlimeKnights/Mantle
@OnlyIn(Dist.CLIENT)
class ExtraHeartRenderHandler {
    private val mc: Minecraft = Minecraft.getInstance()

    private var playerHealth = 0
    private var lastPlayerHealth = 0
    private var healthUpdateCounter: Long = 0
    private var lastSystemTime: Long = 0
    private val rand = Random()

    private var regen = 0

    /**
     * Draws a texture to the screen
     * @param guiGraphics  gui graphics instance
     * @param texture      The texture to draw
     * @param y            Y position
     * @param textureX     Texture X
     * @param textureY     Texture Y
     * @param width        Width to draw
     * @param height       Height to draw
     */
    @Suppress("SameParameterValue")
    private fun blit(
        guiGraphics: GuiGraphics,
        texture: ResourceLocation,
        x: Int,
        y: Int,
        textureX: Int,
        textureY: Int,
        width: Int,
        height: Int
    ) {
        guiGraphics.blit(texture, x, y, textureX, textureY, width, height)
    }

    /* HUD
    /**
     * Event listener
     * @param event  Event instance
     */
    @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
    fun renderHealthbar(event: RenderGuiLayerEvent.Pre) {
        val guiGraphics = event.guiGraphics
        if (event.isCanceled || !getClientConfig().extraHeartRenderer || event.name !== VanillaGuiLayers.PLAYER_HEALTH) {
            return
        }
        val gui = mc.gui
        // ensure its visible
        if (gui !is ForgeGui || mc.options.hideGui || !gui.shouldDrawSurvivalElements()) {
            return
        }
        val player = mc.getCameraEntity() as? Player ?: return
        gui.setupOverlayRenderState(true, false)

        mc.profiler.push("health")

        val leftHeight: Int = gui.leftHeight
        val width = mc.window.guiScaledWidth
        val height = mc.window.guiScaledHeight
        val updateCounter = mc.gui.guiTicks

        // start default forge/mc rendering
        // changes are indicated by comment
        var health = Mth.ceil(player.health)
        val highlight =
            this.healthUpdateCounter > updateCounter.toLong() && (this.healthUpdateCounter - updateCounter.toLong()) / 3L % 2L == 1L

        if (health < this.playerHealth && player.invulnerableTime > 0) {
            this.lastSystemTime = Util.getMillis()
            this.healthUpdateCounter = (updateCounter + 20).toLong()
        } else if (health > this.playerHealth && player.invulnerableTime > 0) {
            this.lastSystemTime = Util.getMillis()
            this.healthUpdateCounter = (updateCounter + 10).toLong()
        }

        if (Util.getMillis() - this.lastSystemTime > 1000L) {
            this.playerHealth = health
            this.lastPlayerHealth = health
            this.lastSystemTime = Util.getMillis()
        }

        this.playerHealth = health
        val healthLast = this.lastPlayerHealth

        val attrMaxHealth: AttributeInstance? = player.getAttribute(Attributes.MAX_HEALTH)
        var healthMax = attrMaxHealth?.value?.toFloat() ?: 0f
        var absorb = Mth.ceil(player.absorptionAmount).toFloat()

        // CHANGE: simulate 10 heart max if there's more, so vanilla only renders one row max
        healthMax = min(healthMax.toDouble(), 20.0).toFloat()
        health = min(health.toDouble(), 20.0).toInt()
        absorb = min(absorb.toDouble(), 20.0).toFloat()

        val healthRows = Mth.ceil((healthMax + absorb) / 2.0f / 10.0f)
        val rowHeight = max((10 - (healthRows - 2)).toDouble(), 3.0).toInt()

        rand.setSeed(updateCounter * 312871L)

        val left = width / 2 - 91
        val top = height - leftHeight

        // change: these are unused below, unneeded? should these adjust the Forge variable?
        //left_height += (healthRows * rowHeight);
        //if (rowHeight != 10) left_height += 10 - rowHeight;
        this.regen = -1
        if (player.hasEffect(MobEffects.REGENERATION)) {
            this.regen = updateCounter % 25
        }

        assert(mc.level != null)
        val textureY = 9 * (if (mc.level!!.levelData.isHardcore) 5 else 0)
        val textureX = (if (highlight) 25 else 16)
        var margin = 16
        if (player.hasEffect(MobEffects.POISON)) margin += 36
        else if (player.hasEffect(MobEffects.WITHER)) margin += 72
        var absorbRemaining = absorb

        for (i in Mth.ceil((healthMax + absorb) / 2.0f) - 1 downTo 0) {
            val row = Mth.ceil((i + 1).toFloat() / 10.0f) - 1
            val x = left + i % 10 * 8
            var y = top - row * rowHeight

            if (health <= 4) y += rand.nextInt(2)
            if (i == this.regen) y -= 2

            this.blit(guiGraphics, ICON_VANILLA, x, y, textureX, textureY, 9, 9)

            if (highlight) {
                if (i * 2 + 1 < healthLast) {
                    this.blit(guiGraphics, ICON_VANILLA, x, y, margin + 54, textureY, 9, 9) //6
                } else if (i * 2 + 1 == healthLast) {
                    this.blit(guiGraphics, ICON_VANILLA, x, y, margin + 63, textureY, 9, 9) //7
                }
            }

            if (absorbRemaining > 0.0f) {
                if (absorbRemaining == absorb && absorb % 2.0f == 1.0f) {
                    this.blit(guiGraphics, ICON_VANILLA, x, y, margin + 153, textureY, 9, 9) //17
                    absorbRemaining -= 1.0f
                } else {
                    this.blit(guiGraphics, ICON_VANILLA, x, y, margin + 144, textureY, 9, 9) //16
                    absorbRemaining -= 2.0f
                }
            } else {
                if (i * 2 + 1 < health) {
                    this.blit(guiGraphics, ICON_VANILLA, x, y, margin + 36, textureY, 9, 9) //4
                } else if (i * 2 + 1 == health) {
                    this.blit(guiGraphics, ICON_VANILLA, x, y, margin + 45, textureY, 9, 9) //5
                }
            }
        }

        this.renderExtraHearts(guiGraphics, left, top, player)
        this.renderExtraAbsorption(guiGraphics, left, top - rowHeight, player)

        RenderSystem.setShaderTexture(0, ICON_VANILLA)
        gui.leftHeight += 10
        if (absorb > 0) {
            gui.leftHeight += 10
        }

        event.isCanceled = true
        RenderSystem.disableBlend()
        mc.profiler.pop()
        @Suppress("UnstableApiUsage")
        NeoForge.EVENT_BUS.post(
            RenderGuiLayerEvent.Post(
                event.window, guiGraphics, event.partialTick, VanillaGuiLayers.PLAYER_HEALTH
            )
        )
    } */

    /**
     * Gets the texture from potion effects
     * @param player  Player instance
     * @return  Texture offset for potion effects
     */
    private fun getPotionOffset(player: Player): Int {
        var potionOffset = 0
        var potion = player.getEffect(MobEffects.WITHER)
        if (potion != null) {
            potionOffset = 18
        }
        potion = player.getEffect(MobEffects.POISON)
        if (potion != null) {
            potionOffset = 9
        }
        assert(mc.level != null)
        if (mc.level!!.levelData.isHardcore) {
            potionOffset += 27
        }
        return potionOffset
    }

    /**
     * Renders the health above 10 hearts
     * @param guiGraphics  Gui graphics instance
     * @param xBasePos     Health bar top corner
     * @param yBasePos     Health bar top corner
     * @param player       Player instance
     */
    private fun renderExtraHearts(guiGraphics: GuiGraphics, xBasePos: Int, yBasePos: Int, player: Player) {
        val potionOffset = this.getPotionOffset(player)

        // Extra hearts
        RenderSystem.setShaderTexture(0, ICON_HEARTS)
        val hp = Mth.ceil(player.health)
        this.renderCustomHearts(guiGraphics, ICON_HEARTS, xBasePos, yBasePos, potionOffset, hp, false)
    }

    /**
     * Renders the absorption health above 10 hearts
     * @param guiGraphics  Matrix stack instance
     * @param xBasePos     Health bar top corner
     * @param yBasePos     Health bar top corner
     * @param player       Player instance
     */
    private fun renderExtraAbsorption(guiGraphics: GuiGraphics, xBasePos: Int, yBasePos: Int, player: Player) {
        val potionOffset = this.getPotionOffset(player)

        // Extra hearts
        RenderSystem.setShaderTexture(0, ICON_ABSORB)
        val absorb = Mth.ceil(player.absorptionAmount)
        this.renderCustomHearts(guiGraphics, ICON_ABSORB, xBasePos, yBasePos, potionOffset, absorb, true)
    }

    /**
     * Gets the texture offset from the regen effect
     * @param i       Heart index
     * @param offset  Current offset
     */
    private fun getYRegenOffset(i: Int, offset: Int): Int {
        return if (i + offset == this.regen) -2 else 0
    }

    /**
     * Shared logic to render custom hearts
     * @param guiGraphics  Gui graphics instance
     * @param texture      Texture to draw
     * @param xBasePos     Health bar top corner
     * @param yBasePos     Health bar top corner
     * @param potionOffset Offset from the potion effect
     * @param count        Number to render
     * @param absorb       If true, render absorption hearts
     */
    private fun renderCustomHearts(
        guiGraphics: GuiGraphics,
        texture: ResourceLocation,
        xBasePos: Int,
        yBasePos: Int,
        potionOffset: Int,
        count: Int,
        absorb: Boolean
    ) {
        val regenOffset = if (absorb) 10 else 0
        for (iter in 0 until count / 20) {
            var renderHearts = (count - 20 * (iter + 1)) / 2
            val heartIndex = iter % 11
            if (renderHearts > 10) {
                renderHearts = 10
            }
            for (i in 0 until renderHearts) {
                val y = this.getYRegenOffset(i, regenOffset)
                if (absorb) {
                    this.blit(guiGraphics, texture, xBasePos + 8 * i, yBasePos + y, 0, 54, 9, 9)
                }
                this.blit(guiGraphics, texture, xBasePos + 8 * i, yBasePos + y, 18 * heartIndex, potionOffset, 9, 9)
            }
            if (count % 2 == 1 && renderHearts < 10) {
                val y = this.getYRegenOffset(renderHearts, regenOffset)
                if (absorb) {
                    this.blit(guiGraphics, texture, xBasePos + 8 * renderHearts, yBasePos + y, 0, 54, 9, 9)
                }
                this.blit(
                    guiGraphics,
                    texture,
                    xBasePos + 8 * renderHearts,
                    yBasePos + y,
                    9 + 18 * heartIndex,
                    potionOffset,
                    9,
                    9
                )
            }
        }
    }

    companion object {
        private val ICON_HEARTS = id("textures/gui/hearts.png")
        private val ICON_ABSORB = id("textures/gui/absorb.png")
        private val ICON_VANILLA = ResourceLocation.withDefaultNamespace("textures/gui/icons.png")
    }
}