package dev.arbor.gtnn.data.lang

import dev.arbor.gtnn.api.lang.LangGenerators.entry
import dev.arbor.gtnn.api.lang.LanguageRoot
import dev.arbor.gtnn.api.lang.SingleLangEntry

@LanguageRoot("gtnn")
object MachineLang {
    val TEST: SingleLangEntry by entry("test")

    fun init() {
        TEST()
        machineNames()
        tsl("block.gtnn.large_dehydrator", "大型脱水机")
        tsl("block.gtnn.homemade_bedrock_ore_machine", "土制基岩矿机")
        tsl("block.gtnn.neutron_sensor", "中子传感器")
        tsl("block.gtnn.exxonmobil_chemical_plant", "埃克森美孚化工厂")
        tsl("block.gtnn.neutron_activator", "中子活化器")
        tsl("block.gtnn.large_naquadah_reactor", "大型硅岩发电堆")
        tsl("block.gtnn.catalyst_hatch",  "催化剂仓")
        tsl("gtceu.chemical_plant", "化工厂", "Chemical Plant")
        tsl("gtceu.neutron_activator", "中子活化", "Neutron Activator")
        tsl("gtceu.naquadah_reactor", "硅岩发电", "Naquadah Reactor")
        tsl("gtceu.large_naquadah_reactor", "液态硅岩发电", "Large Naquadah Reactor")
        tsl("gtceu.dehydrator", "脱水", "Dehydrator")
        tsl("gtceu.rocket_engine", "火箭燃料发电", "Rocket Fuel Engine")
        tsl("gtceu.precision_assembly", "精密组装", "Precision Assembly")
        tsl(
            "gtnn.precision_assembly.tooltip.1",
            "其他可用配方类型: 精密组装 (GT--)",
            "Other available recipe type: Precision Assembly (GT--)"
        )
        tsl(
            "gtnn.precision_assembly.tooltip.2",
            "注意：在精密组装模式下无法并行",
            "NOTE: Parallelization is not possible in precision assembly mode"
        )
        tsl("gtnn.recipe.condition.plant_casing.tooltip", "外壳等级: %s (%s)", "Casing: %s (%s)")
        tsl("gtnn.multiblock.pattern.error.plant_casings", "所有外壳必须相同", "All casings must be same")
        tsl("gtnn.multiblock.pattern.error.pipe", "所有管道必须相同", "All pipes must be same")
        tsl("gtnn.multiblock.pattern.error.machine_casing", "所有机械方块必须相同", "All machine casings must be same")
        tsl(
            "gtnn.multiblock.chemical_plant.tooltip1",
            "§o§7重工业，现在就在你家门口！",
            "§o§7Heavy industry, right at your doorstep now!"
        )
        tsl("gtnn.multiblock.chemical_plant.tooltip2", "§6线圈：§e+50%§6 速度/级", "§6Coil：§e+50%§6 speed/tier")
        tsl("gtnn.multiblock.chemical_plant.tooltip3",
            "§b管道方块：§e+2§b 并行 及 §e-20%§b 催化剂消耗概率/级", "§bPipe：§e+2§b parallel and §e-20%§b catalyst consumption/tier")
        tsl(
            "gtnn.multiblock.chemical_plant.tooltip4",
            "§5机械方块：配方电压支持等级",
            "§5MachineCasing：Recipe voltage support level"
        )
        tsl(
            "gtnn.multiblock.chemical_plant.energy_hatch",
            "§e支持能源仓: %s §e及以下",
            "§eSupport Energy Hatch: %s §eand following"
        )
        tsl("gtnn.multiblock.chemical_plant.parallel_level", "§5并行: %s", "§bParallel: %s")
        tsl("gtnn.multiblock.chemical_plant.heating_coil", "§6提速: %s%%", "§6Speed: %s%%")
        tsl("gtnn.multiblock.chemical_plant.tier", "§e配方电压最大支持: %s", "§eRecipe voltage maximum support:\n%s")
        tsl("gtnn.multiblock.chemical_plant.chance", "§6催化剂消耗概率: %s%%", "§6Catalyst consumption probability:\n%s%%")
        tsl(
            "gtnn.machine.neutron_accelerator.tooltip1",
            "§o§7输入EU，加速中子!",
            "§o§7Input EU to Accelerate the Neutron!"
        )
        tsl("gtnn.machine.neutron_accelerator.tooltip2", "§6最大EU输入: %s", "§6Max EU Input: %s")
        tsl("gtnn.machine.neutron_accelerator.tooltip3", "§6最大EU消耗: %s", "§6Max EU Cost: %s")
        tsl(
            "gtnn.machine.neutron_accelerator.tooltip4",
            "§b每点EU都会转化为§e10~20-eV§b中子动能.",
            "§bEach EU will be converted to §e10~20-eV§b of neutron kinetic energy."
        )
        tsl("gtnn.multiblock.neutron_activator.tooltip1", "§o§7超光速运动!", "§o§7Faster-Light Movement!")
        tsl(
            "gtnn.multiblock.neutron_activator.tooltip2",
            "§6额外的高速管道方块提供配方时间减免，同时降低中子加速器的效率",
            "§6Extra high-speed pipe blocks provide recipe time reduction, and lower the efficiency of the neutron accelerator"
        )
        tsl(
            "gtnn.multiblock.neutron_activator.tooltip3",
            "§6没有中子加速器运行时，中子动能每秒降低§e72KeV§6中子动能",
            "§6Without a neutron accelerator running, neutron kinetic energy decreases by §e72KeV §6neutron kinetic energy per second"
        )
        tsl(
            "gtnn.multiblock.neutron_activator.tooltip4",
            "§6输入石墨/铍粉可以立即吸收§e10MeV§6中子动能",
            "§6Absorb §e10MeV §6neutron kinetic energy immediately when input cesium or beryllium dust"
        )
        tsl(
            "gtnn.multiblock.neutron_activator.tooltip5",
            "§6当中子动能超过§41200MeV§6后将会爆炸！",
            "§6When the neutron kinetic energy exceeds §41200MeV§6, it will explode!"
        )
        tsl("gtnn.multiblock.large_naquadah_reactor.tooltip1", "§o§7环境友好型!", "§o§7Environment Friendly!")
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip2",
            "§6从高能流体中获取能量",
            "§6Get energy from high-power fluid"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip3",
            "§6运行时需要消耗§e2400L/s§6液态空气， 否则你的燃料将会被销毁",
            "§6When the reactor is running, it needs to consume §e2400L/s§6 liquid air, otherwise your fuel will be destroyed"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip4",
            "§6输入液态燃料, 输入仓内出现不止§4一种§6燃料时，反应堆将会爆炸",
            "§6Input liquid fuel, if there are more than §4one §6fuel in the input hatch, the reactor will explode"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip5",
            "§6可以消耗§e1000L/s§6冷却液获得§e150%效率提升",
            "§6Can consume §e1000L/s§6 cooling fluid to get §e150% efficiency"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip6",
            "§6消耗激发流体以提升输出功率",
            "§6Consume igniting fluid to increase output power"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip7",
            "熔融铯        | §e2x功率 | §6180L/s",
            "Cesium             | §e2x power | §6180L/s"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip8",
            "熔融铀-235  | §e3x功率 | §6180L/s",
            "Uranium-235   | §e3x power | §6180L/s"
        )
        tsl(
            "gtnn.multiblock.large_naquadah_reactor.tooltip9",
            "熔融硅岩      | §e4x功率 | §620L/s",
            "Naquadah       | §e4x power | §620L/s"
        )
        tsl("gtnn.multiblock.large_naquadah_reactor.power", "发电倍率: %s", "Power: %s")
        tsl("gtnn.multiblock.neutronactivator.ev", "当前中子动能: %deV", "Current Neutron Kinetic Energy: %deV")
        tsl("gtnn.multiblock.neutronactivator.height", "高度: %s", "Height: %s")
        tsl("gtnn.multiblock.neutronactivator.efficiency", "耗时: %s%%", "Efficiency: %s%%")
        tsl(
            "gtnn.recipe.condition.neutron_activator_condition_tooltip",
            "最小中子动能:\n%s MeV\n最大中子动能:\n%s MeV",
            "Min Neutron Kinetic Energy:\n%s MeV\nMax Neutron Kinetic Energy:\n%s MeV"
        )
        tsl(
            "block.gtnn.neutron_sensor.tooltip1",
            "§7可安装在§b中子活化器§7上",
            "§7Could be installed on §bNeutron Activator§7"
        )
        tsl(
            "block.gtnn.neutron_sensor.tooltip2",
            "基于§6中子动能§7输出红石信号，右键以打开GUI进行设置。",
            "Based on §6neutron kinetic energy §7output red stone signal, right-click to open the GUI for settings."
        )
        tsl("gtnn.universal.desc.neutron_kinetic_energy.min", "最小中子动能\n(%s)", "Min Neutron Kinetic Energy\n(%s)")
        tsl("gtnn.universal.desc.neutron_kinetic_energy.max", "最大中子动能\n(%s)", "Max Neutron Kinetic Energy\n(%s)")
        tsl(
            "gui.gtnn.neutron_sensor.invert.enabled",
            "输出：反转\n\n切换以反转红石逻辑\n默认情况下，中子动能介于所设定的最小值和最大值之间时传感器将发出红石信号，小于最小值时则停止发出红石信号",
            "Output: Reverse\n\nSwitch to reverse redstone logic\nBy default, the sensor will emit a redstone signal when the neutron kinetic energy is between the set minimum and maximum values, and stop emitting a redstone signal when it is less than the minimum value."
        )
        tsl(
            "gui.gtnn.neutron_sensor.invert.disabled",
            "输出：正常\n\n切换以反转红石逻辑\n默认情况下，中子动能介于所设定的最小值和最大值之间时传感器将发出红石信号，小于最小值时则停止发出红石信号",
            "Output: Normal\n\nSwitch to reverse redstone logic\nBy default, the sensor will emit a redstone signal when the neutron kinetic energy is between the set minimum and maximum values, and stop emitting a redstone signal when it is less than the minimum value."
        )
        tsl("gtnn.machine.naquadah_reactor.tooltip", "效率: %s%%", "Efficiency: %s%%")
        tsl("gtnn.machine.rocket_engine.tooltip", "效率: %s%%", "Efficiency: %s%%")
        tsl("gtnn.recipe.condition.plant_casing.tier.bronze", "青铜", "Bronze")
        tsl("gtnn.recipe.condition.plant_casing.tier.steel", "钢", "Steel")
        tsl("gtnn.recipe.condition.plant_casing.tier.aluminium", "铝", "Aluminium")
        tsl("gtnn.recipe.condition.plant_casing.tier.stainless_steel", "不锈钢", "Stainless Steel")
        tsl("gtnn.recipe.condition.plant_casing.tier.titanium", "钛", "Titanium")
        tsl("gtnn.recipe.condition.plant_casing.tier.tungsten_steel", "钨钢", "Tungsten Steel")
    }

    private fun machineNames() {
        tsl("block.gtnn.mv_dehydrator", "§b高级脱水机 §r")
        tsl("block.gtnn.hv_dehydrator", "§6高级脱水机 II§r")
        tsl("block.gtnn.ev_dehydrator", "§5高级脱水机 III§r")
        tsl("block.gtnn.iv_dehydrator", "§9精英脱水机 §r")
        tsl("block.gtnn.luv_dehydrator", "§d精英脱水机 II§r")
        tsl("block.gtnn.zpm_dehydrator", "§c精英脱水机 III§r")
        tsl("block.gtnn.ev_naquadah_reactor", "§5高级硅岩发电机 I")
        tsl("block.gtnn.iv_naquadah_reactor", "§9精英硅岩发电机 II")
        tsl("block.gtnn.luv_naquadah_reactor", "§d精英硅岩发电机 III")
        tsl("block.gtnn.zpm_naquadah_reactor", "§c精英硅岩发电机 IV")
        tsl("block.gtnn.uv_naquadah_reactor", "§3终极硅岩发电机 V")
        tsl("block.gtnn.ev_rocket_engine", "§5高级火箭引擎发电机 I")
        tsl("block.gtnn.iv_rocket_engine", "§9精英火箭引擎发电机 II")
        tsl("block.gtnn.luv_rocket_engine", "§d精英火箭引擎发电机 III")
        tsl("block.gtnn.ulv_neutron_accelerator", "§8ULV 中子加速器")
        tsl("block.gtnn.lv_neutron_accelerator", "§7LV 中子加速器")
        tsl("block.gtnn.mv_neutron_accelerator", "§bMV 中子加速器")
        tsl("block.gtnn.hv_neutron_accelerator", "§6HV 中子加速器")
        tsl("block.gtnn.ev_neutron_accelerator", "§5EV 中子加速器")
        tsl("block.gtnn.iv_neutron_accelerator", "§9IV 中子加速器")
        tsl("block.gtnn.luv_neutron_accelerator", "§dLuV 中子加速器")
        tsl("block.gtnn.zpm_neutron_accelerator", "§cZPM 中子加速器")
        tsl("block.gtnn.uv_neutron_accelerator", "§3UV 中子加速器")
    }

    private fun tsl(key: String?, cn: String?) {
        LangHandler.tsl(key, cn)
    }

    private fun tsl(key: String?, cn: String, en: String) {
        LangHandler.tsl(key, cn, en)
    }
}
