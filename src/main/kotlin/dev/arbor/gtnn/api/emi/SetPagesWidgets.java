package dev.arbor.gtnn.api.emi;

import dev.arbor.gtnn.mixin.emi.AEmiApi;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.runtime.EmiSidebars;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SetPagesWidgets extends Widget {
    protected final Bounds bounds;
    public final Map<EmiRecipeCategory, List<EmiRecipe>> recipes;
    public final Collection<EmiIngredient> stacks;
    public final int startX;
    public final int startY;
    public final List<Integer> turns;
    public SetPagesWidgets(Bounds bounds, Map<EmiRecipeCategory, List<EmiRecipe>> recipes, Collection<EmiIngredient> stacks, int startX, int startY, List<Integer> turns) {
        this.bounds = bounds;
        this.recipes = recipes;
        this.startX = startX;
        this.startY = startY;
        if (stacks.isEmpty()) {
            throw new IllegalArgumentException("stacks.isEmpty()");
        }
        this.stacks = stacks;
        this.turns = turns;
    }
    @Override
    public Bounds getBounds() {
        return bounds;
    }
    @Override
    public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
        if (!getBounds().contains(mouseX, mouseY)) return;
        int x = startX;
        int y = startY;
        boolean vertical = true;
        int color = 0xFF66CCFF;
        for (int turn : turns) {
            if (vertical) {
                int y2 = y + turn;
                if (turn < 0) {
                    draw.vLine(x, y2, y, color);
                } else {
                    draw.vLine(x, y, y2, color);
                }
                y = y2;
            } else {
                int x2 = x + turn;
                if (turn < 0) {
                    draw.hLine(x2, x, y, color);
                } else {
                    draw.hLine(x, x2, y, color);
                }
                x = x2;
            }
            vertical ^= true;
        }
    }
    @Override
    public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
        List<ClientTooltipComponent> tooltip = new ArrayList<>(super.getTooltip(mouseX, mouseY));
        tooltip.add(ClientTooltipComponent.create(Component.literal("中键以查看具体配方").getVisualOrderText()));
        return tooltip;
    }
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && getBounds().contains(mouseX, mouseY)) {
            var iter = stacks.iterator();
            AEmiApi.invokeSetPages(recipes, iter.next());
            while (iter.hasNext()) {
                EmiSidebars.lookup(iter.next());
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
