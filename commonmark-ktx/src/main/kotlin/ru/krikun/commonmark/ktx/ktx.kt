@file:Suppress("unused")

package ru.krikun.commonmark.ktx

import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.Renderer
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset

inline fun buildParser(
    block: Parser.Builder.() -> Unit = {}
): Parser = Parser.builder().apply(block).build()

inline fun buildHtmlRenderer(
    block: HtmlRenderer.Builder.() -> Unit = {}
): HtmlRenderer = HtmlRenderer.builder().apply(block).build()

inline fun buildTextContentRenderer(
    block: TextContentRenderer.Builder.() -> Unit = {}
): TextContentRenderer = TextContentRenderer.builder().apply(block).build()

fun Parser.parse(
    inputStream: InputStream,
    charset: Charset = Charsets.UTF_8
): Node = inputStream.reader(charset).use { parseReader(it) }

fun Parser.parseBuffered(
    inputStream: InputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    charset: Charset = Charsets.UTF_8
): Node = inputStream.reader(charset).buffered(bufferSize).use { parseReader(it) }

fun Parser.parse(file: File, charset: Charset = Charsets.UTF_8): Node = file.reader(charset).use { parseReader(it) }

fun Parser.parseBuffered(
    file: File,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    charset: Charset = Charsets.UTF_8
): Node = file.reader(charset).buffered(bufferSize).use { parseReader(it) }

inline fun String.parse(block: Parser.Builder.() -> Unit = {}): Node = buildParser(block).parse(this)

inline fun Reader.parse(block: Parser.Builder.() -> Unit = {}): Node = buildParser(block).parseReader(this)

inline fun InputStream.parse(
    charset: Charset = Charsets.UTF_8,
    block: Parser.Builder.() -> Unit = {}
): Node = reader(charset).use { it.parse(block) }

inline fun InputStream.parseBuffered(
    charset: Charset = Charsets.UTF_8,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    block: Parser.Builder.() -> Unit = {}
): Node = reader(charset).buffered(bufferSize).use { it.parse(block) }

inline fun File.parse(
    charset: Charset = Charsets.UTF_8,
    block: Parser.Builder.() -> Unit = {}
): Node = reader(charset).use { it.parse(block) }

inline fun File.parseBuffered(
    charset: Charset = Charsets.UTF_8,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    block: Parser.Builder.() -> Unit = {}
): Node = reader(charset).buffered(bufferSize).use { it.parse(block) }

fun Node.render(renderer: Renderer): String = renderer.render(this)

inline fun Node.renderHtml(block: HtmlRenderer.Builder.() -> Unit = {}): String = buildHtmlRenderer(block).render(this)

inline fun Node.renderTextContent(
    block: TextContentRenderer.Builder.() -> Unit = {}
): String = buildTextContentRenderer(block).render(this)

inline fun Appendable.appendHtml(
    node: Node,
    block: HtmlRenderer.Builder.() -> Unit = {}
): Appendable = apply { buildHtmlRenderer(block).render(node, this) }

inline fun Appendable.appendTextContent(
    node: Node,
    block: TextContentRenderer.Builder.() -> Unit = {}
): Appendable = apply { buildTextContentRenderer(block).render(node, this) }
