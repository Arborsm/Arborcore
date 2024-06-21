package dev.arbor.gtnn.api.lang

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateLangProvider
import dev.arbor.gtnn.api.registry.CNLangProvider
import dev.arbor.gtnn.data.GTNNDataGen
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotations


internal annotation class LanguageRoot(val prefix: String)

/**
 * Utility for language entries with keys being generated from their property name and path.
 * Entries are added to datagen via Registrate.
 *
 * The root is always the next object with a `@.LanguageRoot` annotation.
 * From there on, the key is built in the following format:
 * - `<language-root>.<property-name>`
 * - `<language-root>.<nested-object-path>.<property-name>`
 *
 * In case a nested object needs to have its own language root, it can be given its own `@LanguageRoot` annotation.
 */
internal object LangGenerators {
    /**
     * Automatically generates a key from the field's path.
     */
    class GeneratorDelegate<T : Any>(private val entryConstructor: (String) -> T) {
        private lateinit var entry: T

        operator fun getValue(thisRef: Any, property: KProperty<*>): T {
            if (!this::entry.isInitialized) {
                val name = resolvePath(thisRef::class) + '.' + property.name.lowercase()
                entry = entryConstructor(name)
            }

            return entry
        }
    }

    private val languageRootsCache = mutableMapOf<KClass<*>, String>()

    /**
     * Resolves the path of an object, starting at the next `@LanguageRoot`-annotated object in its nested hierarchy.
     */
    private fun resolvePath(kClass: KClass<*>, resolutionTarget: KClass<*> = kClass): String {
        languageRootsCache[kClass]?.let {
            return it
        }

        val rootAnnotation = kClass.findAnnotations<LanguageRoot>().firstOrNull()
        rootAnnotation?.prefix?.let { rootPrefix ->
            languageRootsCache[kClass] = rootPrefix
            return rootPrefix
        }

        val enclosingClass = kClass.java.enclosingClass?.kotlin
            ?: error("Cannot find @LanguageRoot annotation on ${resolutionTarget.qualifiedName} or any of its enclosing classes")

        return "${resolvePath(enclosingClass, resolutionTarget)}.${kClass.simpleName!!.camelToSnakeCase()}".also {
            languageRootsCache[kClass] = it
        }
    }

// --------------------------------------------------------------------------------------------------------------------

    private val GENERATORS = mutableListOf<RegistrateLangProvider.() -> Unit>()
    private val CNLANG = mutableListOf<CNLangProvider.() -> Unit>()

    /**
     * Registers a new single-line language entry with the given value.
     */
    fun entry(en: String): GeneratorDelegate<SingleLangEntry> = GeneratorDelegate { key ->
        GENERATORS += { add(key, en) }
        SingleLangEntry(key)
    }

    fun tsl(en: String, cn: String): GeneratorDelegate<SingleLangEntry> = GeneratorDelegate { key ->
        GENERATORS += { add(key, en) }
        CNLANG += { add(key, cn) }
        SingleLangEntry(key)
    }

    fun cnEntry(cn: String): GeneratorDelegate<SingleLangEntry> = GeneratorDelegate { key ->
        CNLANG += { add(key, cn) }
        SingleLangEntry(key)
    }

    /**
     * Registers a new multiline language entry with the given value. Lines are separated by `\n`.
     */
    fun multilineEntry(value: String): GeneratorDelegate<MultiLangEntry> = GeneratorDelegate { key ->
        var index = 0
        val entries = value.trimMargin().trim().split('\n').associateBy { "${key}.${index++}" }

        entries.forEach { (k, v) ->
            GENERATORS += { add(k, v) }
        }

        MultiLangEntry(entries.keys)
    }

    /**
     * Adds all registered entries to datagen
     */
    private fun generate(provider: RegistrateLangProvider) {
        GENERATORS.forEach { it.invoke(provider) }
    }

    private fun generateCN(provider: CNLangProvider) {
        CNLANG.forEach { it.invoke(provider) }
    }

    private val initDatagenGuard = SingleCallGuard()

    /**
     * Initializes datagen for all supplied [classes] containing language entries.
     *
     * Can only be called once!
     */
    internal fun initDatagen(registrate: AbstractRegistrate<*>, vararg classes: KClass<*>) {
        initDatagenGuard.check { "Datagen is already initialized." }
        registrate.addDataGenerator(ProviderType.LANG, this::generate)
        registrate.addDataGenerator(GTNNDataGen.CN_LANG, this::generateCN)


        fun initNestedObjects(cls: KClass<*>) {
            cls.nestedClasses.forEach(::initNestedObjects)

            cls.objectInstance?.let { instance ->
                cls.declaredMemberProperties.forEach { member ->
                    @Suppress("UNCHECKED_CAST") (member as KProperty1<Any, *>).get(instance)
                }
            }
        }

        classes.forEach { initNestedObjects(it) }
    }
}