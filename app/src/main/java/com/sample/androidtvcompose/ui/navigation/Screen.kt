package com.sample.androidtvcompose.ui.navigation

const val ARG_LIST_GROUP_ID = "arg_list_group_id"
const val ARG_LIST_ID = "arg_list_id"

sealed class Screen(val route: String) {
    class MenuPage(route: String): Screen("$route?listGroupId={$ARG_LIST_GROUP_ID}&listId={$ARG_LIST_ID}")
}

inline val String.argumentCount: Int get() = arguments().count()

@Suppress("RegExpRedundantEscape")
fun String.arguments(): Sequence<MatchResult> {
    val argumentRegex = "\\{(.*?)\\}".toRegex()
    return argumentRegex.findAll(this)
}

fun Screen.createPath(vararg args: Any): String {
    var route = this.route
    require(args.size == route.argumentCount) {
        "Provided ${args.count()} parameters, was expected ${route.argumentCount} parameters!"
    }
    route.arguments().forEachIndexed { index, matchResult ->
        route = route.replace(matchResult.value, args[index].toString())
    }
    return route
}
