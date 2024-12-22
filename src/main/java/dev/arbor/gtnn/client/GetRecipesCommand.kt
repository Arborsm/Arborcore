package dev.arbor.gtnn.client

import com.gregtechceu.gtceu.api.GTValues
import com.gregtechceu.gtceu.api.recipe.GTRecipe
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer
import com.gregtechceu.gtceu.api.recipe.RecipeHelper
import com.lowdragmc.lowdraglib.Platform
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.serialization.JsonOps
import kotlinx.coroutines.*
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.literal
import net.minecraft.network.chat.Component
import java.io.File
import kotlin.io.path.exists
import kotlin.io.path.pathString


object GetRecipesCommand {
    @OptIn(DelicateCoroutinesApi::class)
    @JvmStatic
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(literal("gtnn")
            .requires { it.hasPermission(3) }
            .then(literal("getRecipes")
                .executes {
                it.source.sendSystemMessage(Component.literal("Starting recipe processing..."))
                GlobalScope.launch {
                    try {
                        processRecipes(it)
                    } catch (e: Exception) {
                        it.source.sendFailure(Component.literal("An error occurred: ${e.message}"))
                    }
                }
                return@executes 1
            })
        )
    }

    private suspend fun processRecipes(context: CommandContext<CommandSourceStack>) {
        val source = context.source.level
        withContext(Dispatchers.IO) {
            val tierToPath: Map<Int, String> = GTValues.ALL_TIERS.associateWith { tier -> getPath(GTValues.VN[tier]) }
            source.recipeManager.recipes.forEach { recipe ->
                if (recipe is GTRecipe) {
                    GTRecipeSerializer.CODEC.encodeStart(JsonOps.INSTANCE, recipe).resultOrPartial { s ->
                        throw RuntimeException(s)
                    }.ifPresent { json ->
                        val tier = RecipeHelper.getRecipeEUtTier(recipe)
                        val file = File(tierToPath[tier] + ".json")
                        val text = "^" + recipe.id.path.replace("/", "_") + "^n:" + json
                        if (!file.parentFile.exists()) file.parentFile.mkdirs()
                        if (file.exists()) {
                            file.appendText("\n$text")
                        } else {
                            file.writeText(text)
                        }
                    }
                    context.source.sendSystemMessage(Component.literal("Processed recipe: ${recipe.id}"))
                }
            }
        }
        context.source.sendSuccess({ Component.literal("Recipes processing completed.") }, false)
    }

    private fun getPath(v: String) : String {
        val lvDir = Platform.getGamePath().resolve("gtnn_recipes/$v")
        if (!lvDir.parent.exists()) lvDir.parent.toFile().mkdirs()
        return lvDir.pathString
    }
}