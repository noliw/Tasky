package com.nolawiworkineh.tasky.logging

import timber.log.Timber


class TimberDebugTree : Timber.DebugTree() {
    private var httpMessageBuffer = mutableListOf<String>()
    private var isCollectingHttpMessage = false

    override fun createStackElementTag(element: StackTraceElement): String {
        val className = element.className.substringAfterLast('.')
        return buildString {
            append("\uD83C\uDF32") // Tree emoji
            append(" (")
            append(className)
            append(".kt:")
            append(element.lineNumber)
            append(") [")
            append(element.methodName)
            append("]")
        }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (tag?.contains("Tasky-Network") == true) {
            // Start collecting for a new HTTP message
            if (message.startsWith("-->") || message.startsWith("<--")) {
                if (!message.contains("END")) {
                    isCollectingHttpMessage = true
                    httpMessageBuffer.clear()
                }
                httpMessageBuffer.add(message)
                if (!message.contains("END")) return
            }

            if (isCollectingHttpMessage) {
                httpMessageBuffer.add(message)

                // If this is the end of the message, format and print
                if (message.startsWith("--> END") || message.startsWith("<-- END")) {
                    val formattedMessage = formatHttpMessage(httpMessageBuffer)
                    super.log(priority, "Tasky-Network", formattedMessage, t)
                    httpMessageBuffer.clear()
                    isCollectingHttpMessage = false
                }
                return
            }
        }

        // For non-HTTP logs
        val priorityChar = when (priority) {
            2 -> "💫 V" // Verbose
            3 -> "🔍 D" // Debug
            4 -> "ℹ️ I" // Info
            5 -> "⚠️ W" // Warn
            6 -> "⛔️ E" // Error
            7 -> "🚨 A" // Assert
            else -> "🔧 N" // None
        }

        val messageLines = message.split('\n')
        val formattedMessage = buildString {
            append("┌─────────────────────────────────────────────────\n")
            append("│ $priorityChar/$tag\n")
            append("├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n")
            messageLines.forEach { line ->
                append("│ $line\n")
            }
            append("└─────────────────────────────────────────────────")
        }

        super.log(priority, tag, formattedMessage, t)
    }

    private fun formatHttpMessage(messages: List<String>): String {
        return buildString {
            append("┌─────────────────────────────────────────────────\n")
            append("│ 🌐 HTTP Tasky-Network\n")
            append("├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n")

            // Print the request/response line first
            messages.firstOrNull { it.startsWith("-->") || it.startsWith("<--") }?.let {
                append("│ $it\n│\n")
            }

            // Print headers
            messages.filter {
                it.contains(":") &&
                        !it.contains("{") &&
                        !it.startsWith("-->") &&
                        !it.startsWith("<--")
            }.forEach { header ->
                append("│ $header\n")
            }

            // Add newline before body if we have one
            messages.firstOrNull { it.contains("{") }?.let {
                append("│\n")
                append("│ $it\n")
            }

            // Print the END line with a newline before it
            messages.firstOrNull { it.contains("END") }?.let {
                append("│\n")
                append("│ $it\n")
            }

            append("└─────────────────────────────────────────────────")
        }
    }
}