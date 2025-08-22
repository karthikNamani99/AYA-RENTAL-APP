package com.sapotos.ayarental.utils

class Event<out T>(private val content: T) {
    private var handled = false
    fun getIfNotHandled(): T? = if (handled) null else { handled = true; content }
    fun peek(): T = content


}
