package cn.rubintry.nbui.core

import androidx.core.content.FileProvider

class NBFileProvider : FileProvider() {

    override fun onCreate(): Boolean {
        return true
    }
}