# PagineDown
[![Discord](https://img.shields.io/discord/818135932103557162?color=7289da&logo=discord)](https://discord.gg/tVYhJfyDWG)
[![](https://jitpack.io/v/net.william278/PagineDown.svg)](https://jitpack.io/#net.william278/PagineDown)

PagineDown is a library for generating paginated [MineDown](https://github.com/Phoenix616/MineDown)-formatted chat menu lists.

![](images/list-example-in-game.png)

Requires Java 11+. PagineDown is a library that is intended to be used with MineDown-adventure (not standalone) -- your project will need to shade MineDown-adventure to use it.

PagineDown (v1.1+) runs on the [adventure](https://docs.adventure.kyori.net/) platform, or on platforms with adventure adapters.

## Installation
PagineDown is available on JitPack. You can browse the Javadocs [here](https://javadoc.jitpack.io/net/william278/PagineDown/latest/javadoc/).

Note that your plugin will also need to shade (or shade a library that includes) [MineDown](https://github.com/Phoenix616/MineDown).

### Maven
To use the library on Maven, in your `pom.xml` file, first add the JitPack repository:
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

Then, add the dependency in your `<dependencies>` section. Remember to replace `Tag` with the current PagineDown version.
```xml
    <dependency>
        <groupId>net.william278</groupId>
        <artifactId>PagineDown</artifactId>
        <version>Tag</version>
        <scope>compile</scope>
    </dependency>
```

### Gradle & others
JitPack has a [handy guide](https://jitpack.io/#net.william278/PagineDown/#How_to) for how to use the dependency with other build platforms.

## License
PagineDown is licensed under Apache-2.0.
