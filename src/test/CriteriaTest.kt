package ru.s03.game.test


import org.junit.jupiter.api.Test
import nulp.ua.GameMatrix
import nulp.ua.ICriteria
import nulp.ua.ProbabilityGameMatrix
import nulp.ua.gamedata.criteria.HurwitzCriteria
import nulp.ua.gamedata.criteria.LaplaceCriteria
import nulp.ua.gamedata.criteria.WaldCriteria
import java.io.File
import java.io.InputStream
import java.util.stream.Collectors

internal class CriteriaTest {

    private fun matrix(): GameMatrix {
        val alternativeNames: List<String> = listOf("First culture", "Second culture", "Third culture")
        val stateNames: List<String> = listOf("Working every day", "Working nine to five evey day", "Working nine to five except weekends")

        val inputStream: InputStream = File("matrix.txt").inputStream()
        val lineList = arrayListOf<String>()

        inputStream.bufferedReader().forEachLine { lineList.add(it) }
        var matrix = lineList.stream()
                .map { it.split(" ") }
                .map { it -> it.map { it.toDouble() } }
                .collect(Collectors.toList())

        val gm = GameMatrix(matrix, alternativeNames, stateNames)
        return gm;
    }

    private fun probabilityMatrix(): ProbabilityGameMatrix {
        val alternativeNames: List<String> = listOf("First culture", "Second culture", "Third culture")
        val stateNames: List<String> = listOf("Working every day", "Working nine to five evey day", "Working nine to five except weekends")
        val matrix: List<List<Double>> = listOf(
                listOf(100.0, 80.0, 50.0),
                listOf(90.0, 100.0, 70.0),
                listOf(60.0, 90.0, 80.0)
        )
        val probabilities: List<Double> = listOf(0.5, 0.35, 0.15)
        val gm = ProbabilityGameMatrix(matrix, alternativeNames, stateNames, probabilities)
        return gm;
    }

    private fun testCriteria(gameMatrix: GameMatrix, criteria: ICriteria, name: String) {
        println(gameMatrix.toString())
        val optimum = criteria.optimum()
        println("$name. Best strategy: ")
        optimum.forEach { o -> println(o.toString()) }
    }

    @Test
    fun testWaldCriteria() {
        val matrix = matrix();
        val criteria = WaldCriteria(matrix)
        testCriteria(matrix, criteria, "Valda criterion")
    }

    @Test
    fun testHurwitzCriteria() {
        val matrix = matrix();
        val opt = 0.6
        val criteria = HurwitzCriteria(matrix, opt)
        testCriteria(matrix, criteria, "Hurwitz criterion with optimism koef $opt")
    }

    @Test
    fun testLaplaceCriteria() {
        val matrix = matrix();
        val criteria = LaplaceCriteria(matrix)
        testCriteria(matrix, criteria, "Laplace criterion")
    }
}