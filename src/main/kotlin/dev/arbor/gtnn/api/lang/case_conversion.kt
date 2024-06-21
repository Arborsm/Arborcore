package dev.arbor.gtnn.api.lang

fun String.camelToSnakeCase(): String {
    val output = StringBuilder()
    var isStart = true
    for (char in this) {
        when {
            char == '.' -> {
                output.append(char)
                isStart = true
            }

            isStart -> {
                output.append(char)
                isStart = false
            }

            char.isUpperCase() -> {
                output.append('_').append(char)
            }

            else -> {
                output.append(char)
            }
        }
    }

    return output.toString().lowercase()
}
