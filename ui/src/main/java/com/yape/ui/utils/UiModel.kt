package com.yape.ui.utils

interface UiModel

open class UiAwareModel : UiModel {
    var isRedelivered: Boolean = false
}
