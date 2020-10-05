package io.arnonuem

enum class CharacterType {
    ME, ENEMY
}

data class Character(val type: CharacterType, val life: Int, val damage: Int)
