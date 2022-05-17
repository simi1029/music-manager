package hu.simda.musicmanagerserver.domain

enum class Rank(val value: Int) {
    UNRANKED(0),
    POOR(0),
    FAIR(1),
    QUITE_GOOD(2),
    GOOD(3),
    MORE_THAN_GOOD(4),
    VERY_GOOD(5),
    EXCELLENT(7),
    MASTERPIECE(10)
}
