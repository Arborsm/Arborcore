package dev.arbor.gtnn.api.machine.multiblock

import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility

import java.util.function.Supplier

class APartAbility(name: String): PartAbility(name) {
    companion object{
        @JvmField
        val NEUTRON_ACCELERATOR = PartAbility("neutron_accelerator")
        @JvmField
        val NEUTRON_SENSOR = PartAbility("neutron_sensor")
        @JvmStatic
        fun <T> getOrDefault(value: T, defaultSupplier: Supplier<T>): T{
            if (value == null) return defaultSupplier.get()
            return value
        }
    }
}
