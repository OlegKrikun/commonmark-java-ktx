import com.jfrog.bintray.gradle.BintrayExtension
import java.util.Properties

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10" apply false
    id("com.jfrog.bintray") version "1.8.5" apply false
}

group = "ru.krikun.commonmark"
version = "0.1.2"

val properties = properties("bintray.properties")
val bintrayUser: String = properties.getProperty("user")
val bintrayKey: String = properties.getProperty("key")

subprojects.onEach {
    it.repositories { jcenter() }

    it.group = group
    it.version = version

    it.apply(plugin = "com.jfrog.bintray")
    it.apply(plugin = "org.gradle.maven-publish")
}.forEachAfterEvaluate { subproject ->
    val sourcesJar = subproject.task<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(subproject.sourceSets["main"].allSource)
    }

    subproject.configure<PublishingExtension> {
        publications.create<MavenPublication>("maven") {
            from(subproject.components["java"])
            artifact(sourcesJar) { classifier = "sources" }
        }
    }

    subproject.configure<BintrayExtension> {
        user = bintrayUser
        key = bintrayKey

        pkg.apply {
            repo = "maven"
            name = subproject.name
            setLicenses("Apache-2.0")
            websiteUrl = "https://github.com/OlegKrikun/commonmark-java-ktx"
            issueTrackerUrl = "https://github.com/OlegKrikun/commonmark-java-ktx/issues"
            vcsUrl = "https://github.com/OlegKrikun/commonmark-java-ktx.git"

            setPublications("maven")
        }
    }
}

val Project.sourceSets get() = the<JavaPluginConvention>().sourceSets

fun Iterable<Project>.forEachAfterEvaluate(action: (Project) -> Unit) = forEach { it.afterEvaluate(action) }

fun properties(path: String) = Properties().apply { file(path).inputStream().use { load(it) } }

tasks.wrapper { distributionType = Wrapper.DistributionType.ALL }
