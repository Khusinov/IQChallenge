package uz.khusinov.iqchallenge.models

data class Quiz(
    var id: Int,
    var question: String?,
    var questionImg: String?,
    var level: Int,
    var answer1: String?,
    var answer2: String?,
    var answer3: String?,
    var answer4: String?,
    var answer5: String?,
    var answer6: String?,
    var correctAnswer: String,
)
