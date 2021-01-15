package nulp.ua.gamedata.criteria

import nulp.ua.GameMatrix
import nulp.ua.GameVector
import nulp.ua.ICriteria

class WaldCriteria(gameMatrix : GameMatrix) : ICriteria {

    val gameMatrix: GameMatrix

    init{
        this.gameMatrix = gameMatrix
    }

    override fun optimum(): List<GameVector> {
        val mins = gameMatrix.alternatives.map { a -> Pair(a, a.min()) }
        val max =  mins.maxWith( Comparator { o1, o2 ->  o1.second!!.compareTo(o2.second!!)})
        return mins
                .filter { m -> m.second == max!!.second }
                .map { m -> m.first }
    }
}