package dependencies

object CacheDep {
    const val kotlin = Dependencies.KotlinDep.kotlin
    const val javax = Dependencies.JavaDep.javax
    const val fireBase = Dependencies.GoogleDep.fireBase
    const val gson= Dependencies.CoreDep.gson
    val room = listOf(Dependencies.RoomDep.room, Dependencies.RoomDep.roomKtx)
    const val roomKapt = Dependencies.RoomDep.roomCompiler

    object Test {
        const val junit = Dependencies.TestDep.junit
        const val coroutines = Dependencies.TestDep.coroutinesTest
        const val assertJ = Dependencies.TestDep.assertJ
        const val testCore = Dependencies.TestDep.testCore
        const val testExtJunit = Dependencies.TestDep.testExtJunit
        const val robolectric = Dependencies.TestDep.robolectric
        const val roomTest = Dependencies.TestDep.roomTest
    }
}