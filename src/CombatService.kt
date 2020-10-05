package io.arnonuem

import java.lang.RuntimeException
import kotlin.math.ceil

object CombatService {
    fun simulateFight(my: Character, enemy: Character): CombatResult {
        if( my.type != CharacterType.ME || enemy.type != CharacterType.ENEMY)
            throw RuntimeException("Invalid input")

        val myHits = ceil( enemy.life.toDouble() / my.damage.toDouble() )
        val enemyHits = ceil( my.life.toDouble() / enemy.damage.toDouble() )
        val winFactor = 100 - myHits * 100 / enemyHits

        return CombatResult(myHits, enemyHits, winFactor)
    }


}
