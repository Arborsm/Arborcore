package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import java.util.function.BooleanSupplier

import java.util.function.Supplier

class APartAbility(name: String) : PartAbility(name) {
    companion object {
        val NEUTRON_ACCELERATOR = PartAbility("neutron_accelerator")
        val CATALYST: PartAbility = PartAbility("catalyst")
        val NEUTRON_SENSOR = PartAbility("neutron_sensor")

        fun <T> getOrDefault(value: T, defaultSupplier: Supplier<T>): T {
            if (value == null) return defaultSupplier.get()
            return value
        }

        fun <T> getOrDefault(canGet: BooleanSupplier, getter: ()->T, defaultValue: T): T {
            return if (canGet.asBoolean) getter() else defaultValue
        }
    }
}
