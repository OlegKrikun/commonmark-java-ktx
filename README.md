# commonmark-java-ktx

Little library that provide kotlin extension functions for [commonmark-java](https://github.com/atlassian/commonmark-java)

[![Download](https://api.bintray.com/packages/olegkrikun/maven/commonmark-ktx/images/download.svg)](https://bintray.com/olegkrikun/maven/commonmark-ktx/_latestVersion)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.61-orange.svg)](https://kotlinlang.org/)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-green.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

## Get

```kotlin
repositories {
    jcenter()
}

dependencies {
    implementation("ru.krikun.commonmark:commonmark-ktx:0.1.0")
}
```

## Usage

```kotlin
"...".parse().renderHtml()
```

```kotlin
inputStream
    .parse { extensions(extensionList) }
    .renderHtml { extensions(extensionList) }
```

```kotlin
file
    .parseBuffered(bufferSize = 4 * 1024) { extensions(extensionList) }
    .renderTextContent { extensions(extensionList) }
```

```kotlin
buildString { 
    ...
    renderHtml(content.parse())
    ...
}
```

```kotlin
val sequence = sequenceOf<File>(...)

val parser = buildParser()
val renderer = buildTextContentRenderer { stripNewlines(true)}

val result = sequence.map { parser.parse(it) }.map { renderer.render(it }
```

### Dependencies
Dependency      | Version
--------------- | :----:
kotlin-jvm      | 1.3.61
commonmark-java | 0.13.0

## License

```
Copyright 2019 OlegKrikun

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
