package dev.arbor.gtnn.data.lang

object MiscLang {
    fun init() {
        blocks()
        items()
        config()
        tsl("gtnn.jei.bedrock_ores.dimension", "维度: %s", "Dimension: %s")
        tsl("gtnn.jei.bedrock_ores", "基岩矿石", "Bedrock Ores")
        tsl("cover.ender_item_link.title", "末影物品连接", "Ender Item Link")
        tsl("cover.ender_fluid_link.tooltip.channel_description", "设置频道描述", "Set channel description with input text")
        tsl("cover.ender_fluid_link.tooltip.channel_name", "设置频道名称", "Set channel name with input text")
        tsl("cover.ender_fluid_link.tooltip.list_button", "显示频道列表", "Show channel list")
        tsl("cover.ender_fluid_link.tooltip.clear_button", "清除频道描述", "Clear channel description")
    }

    private fun config() {
        tsl("config.gtnn.option.Client", "客户端", "Client")
        tsl("config.gtnn.option.Server", "服务器", "Server")
        tsl(
            "config.gtnn.option.enableHarderNaquadahLine", "开启更难的硅岩处理", "Enable Harder Naquadah Process Line"
        )
        tsl(
            "config.gtnn.option.enableHarderPlatinumLine", "开启更难的铂处理", "Enable Harder Platinum Process Line"
        )
        tsl("config.gtnn.option.extraHeartRenderer", "启用血条渲染", "Enable extra Heart renderer")
        tsl(
            "config.gtnn.option.banCreateFanBlasting", "禁用机械动力风扇熔炼", "Disable Create Fan Blasting"
        )
        tsl("config.gtnn.option.makesEMIBetter", "使EMI更好用", "Makes EMI Better")
        tsl("config.gtnn.option.killToast", "禁用弹窗", "Disable toast")
        tsl("config.gtnn.option.addChatAnimation", "启用聊天动画", "Enable Chat Animation")
        tsl("config.gtnn.option.enableRemakeGTMEMI", "启用重制GTM-EMI支持", "Enable Remake GTM-EMI support")
        tsl("config.gtnn.option.skyblock", "开启空岛模式", "Enable SkyBlock mode")
        tsl("config.gtnn.option.gtOresMultiplyNum", "GT矿脉大小倍数", "GT OreVeins Size Multiplier")
        tsl("config.gtnn.option.timesOreVeins", "单区块GT矿脉生成数量", "GT OreVeins Num Per Chunk")
        tsl("config.gtnn.option.isTurnOnEnderFluidCover", "启用末影流体覆盖板", "Enable Ender Fluid Link Cover")
        tsl("config.gtnn.option.isTurnOnEnderItemCover", "启用末影物品覆盖板", "Enable Ender Item Link Cover")
    }

    private fun blocks() {
        tsl("block.gtnn.clean_machine_casing", "洁净机械方块")
        tsl("block.gtnn.field_restriction_casing", "立场约束机械方块")
        tsl("block.gtnn.radiation_proof_machine_casing", "防辐射机械方块")
        tsl("block.gtnn.high_speed_pipe_block", "高速管道方块")
    }

    private fun items() {
        tsl(
            "item.gtnn.heavy_ingot_t1.tooltip", "§7用于制作T1重型合金板", "§7Used for making Heavy Alloy Plate T1"
        )
        tsl(
            "item.gtnn.heavy_ingot_t2.tooltip", "§7用于制作T2重型合金板", "§7Used for making Heavy Alloy Plate T2"
        )
        tsl(
            "item.gtnn.heavy_ingot_t3.tooltip", "§7用于制作T3重型合金板", "§7Used for making Heavy Alloy Plate T3"
        )
        tsl(
            "item.gtnn.heavy_ingot_t4.tooltip", "§7用于制作T4重型合金板", "§7Used for making Heavy Alloy Plate T4"
        )
        tsl("item.gtnn.heavy_plate_t1.tooltip", "§71阶", "§7T1")
        tsl("item.gtnn.heavy_plate_t2.tooltip", "§72阶", "§7T2")
        tsl("item.gtnn.heavy_plate_t3.tooltip", "§73阶", "§7T3")
        tsl("item.gtnn.heavy_plate_t4.tooltip", "§74阶", "§7T4")
        tsl("item.gtnn.chip_t1.tooltip", "§7§o用于制作1阶火箭", "§7Used for making Rocket T1")
        tsl("item.gtnn.chip_t2.tooltip", "§7§o用于制作2阶火箭", "§7Used for making Rocket T2")
        tsl("item.gtnn.chip_t3.tooltip", "§7§o用于制作3阶火箭", "§7Used for making Rocket T3")
        tsl("item.gtnn.chip_t4.tooltip", "§7§o用于制作4阶火箭", "§7Used for making Rocket T4")
        tsl("item.gtnn.encapsulated_plutonium", "封装钚")
        tsl("item.gtnn.encapsulated_thorium", "封装钍")
        tsl("item.gtnn.encapsulated_uranium", "封装铀")
        tsl("item.gtnn.enriched_plutonium", "浓缩钚")
        tsl("item.gtnn.enriched_plutonium_nugget", "浓缩钚粒")
        tsl("item.gtnn.enriched_thorium", "浓缩钍")
        tsl("item.gtnn.enriched_thorium_nugget", "浓缩钍粒")
        tsl("item.gtnn.enriched_uranium", "浓缩铀")
        tsl("item.gtnn.enriched_uranium_nugget", "浓缩铀粒")
        tsl("item.gtnn.heavy_ingot_t1", "T1重型锭")
        tsl("item.gtnn.heavy_ingot_t2", "T2重型锭")
        tsl("item.gtnn.heavy_ingot_t3", "T3重型锭")
        tsl("item.gtnn.heavy_ingot_t4", "T4重型锭")
        tsl("item.gtnn.heavy_plate_t1", "T1重型合金板")
        tsl("item.gtnn.heavy_plate_t2", "T2重型合金板")
        tsl("item.gtnn.heavy_plate_t3", "T3重型合金板")
        tsl("item.gtnn.heavy_plate_t4", "T4重型合金板")
        tsl("item.gtnn.inverter", "逆变器")
        tsl("item.gtnn.neutron_source", "中子源")
        tsl("item.gtnn.plate_radiation_protection", "防辐射板")
        tsl("item.gtnn.quark_core", "夸克核心")
        tsl("item.gtnn.radioactive_waste", "放射性废料")
        tsl("item.gtnn.t1_chip", "T1芯片")
        tsl("item.gtnn.t2_chip", "T2芯片")
        tsl("item.gtnn.t3_chip", "T3芯片")
        tsl("item.gtnn.t4_chip", "T4芯片")
        tsl("item.gtnn.computer_circuit", "计算机芯片")
        tsl("item.gtnn.computer_advanced_circuit", "高级计算机芯片")
        tsl("item.gtnn.ender_fluid_link_cover", "末影流体覆盖板")
        tsl("item.gtnn.ender_item_link_cover", "末影物品覆盖板")
        tsl("item.gtnn.ender_fluid_link_cover.tooltip", "§7作§f覆盖板§7时利用§f无线§7§d末影§f连接§7传输§f流体§7。",
            "§7Transports §fFluids§7 with a §fWireless §dEnder§f Connection§7 as §fCover§7.")
        tsl("item.gtnn.ender_item_link_cover.tooltip", "§7作§f覆盖板§7时利用§f无线§7§d末影§f连接§7传输§f物品§7。",
            "§7Transports §fItems§7 with a §fWireless §dEnder§f Connection§7 as §fCover§7.")
        tsl("tooltip.gtnn.banItem", "§4已禁用", "§4Banned")
    }

    private fun tsl(key: String?, cn: String?) {
        LangHandler.tsl(key, cn)
    }

    private fun tsl(key: String?, cn: String, en: String) {
        LangHandler.tsl(key, cn, en)
    }
}
