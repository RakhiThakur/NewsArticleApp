package com.example.myapplication.core

/**
 * Lifted from Plaid 2.0:
 * https://github.com/nickbutcher/plaid/commit/a33ba90e51a4c48fe4acc7d883ed2b160e5b03b8#diff-c311214d70ffbaa6d505fac1f51fd2b9R35
 *
 * Helper to force a when statement to assert all options are matched in a when statement.
 *
 * By default, Kotlin doesn't care if all branches are handled in a when statement. However, if you
 * use the when statement as an expression (with a value) it will force all cases to be handled.
 *
 * This helper is to make a lightweight way to say you meant to match all of them.
 *
 * Usage:
 *
 * ```
 * when(sealedObject) {
 *     is OneType -> //
 *     is AnotherType -> //
 * }.exhaustive
 */

val <T> T.exhaustive: T
    get() = this