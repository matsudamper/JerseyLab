plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
}

repositories {
    mavenCentral()
}

sourceSets {
    val java = sourceSets.getByName("main").java
    java.srcDirs(java.srcDirs + "src/main/kotlin")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.glassfish.jersey.inject:jersey-hk2")
    implementation("org.reflections:reflections")
}
