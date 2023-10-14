import dependencies.DomainDep

plugins {
    id(Config.Plugins.kotlin)
    id(Config.Plugins.javaLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(DomainDep.kotlin)
    implementation(DomainDep.coroutineCore)

    // JavaX
    implementation(DomainDep.javax)

    // Test Dependencies
    testImplementation(DomainDep.Test.junit)
    testImplementation(DomainDep.Test.assertJ)
    testImplementation(DomainDep.Test.mockitoKotlin)
    testImplementation(DomainDep.Test.mockitoInline)
    testImplementation(DomainDep.Test.coroutines)

}