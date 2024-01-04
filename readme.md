
# Rabid Rider


[![](https://jitpack.io/v/mmathijs/rabid-rider.svg)](https://jitpack.io/#mmathijs/rabid-rider) [![](https://jitci.com/gh/mmathijs/rabid-rider/svg)](https://jitci.com/gh/mmathijs/rabid-rider) ![GitHub](https://raw.githubusercontent.com/mmathijs/rabid-rider/badges/jacoco.svg)

Rabid Rider is a extension for RoadRunner that adds some extra (ease of use) features.

## Installation

Add the JitPack repository to your build.dependencies.gradle file:

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

Add the dependency:

```groovy
dependencies {
    ...
    implementation 'com.github.mmathijs:rabid-rider:0.1.2'
}
```

!! Make sure you have the latest version of RoadRunner installed. Rabid Rider is build on top of RoadRunner and requires it to be installed.



## Changelog

## 0.1.1
* Added extra intersection options by @mmathijs in https://github.com/mmathijs/rabid-rider/pull/2


### 0.1.0
* Added Rectangle2d and Line2d, with some basic intersection methods by @mmathijs
* Initial release by @mmathijs
