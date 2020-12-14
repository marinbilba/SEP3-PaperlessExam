using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions
{
    public class Question
    {
        [JsonPropertyName("questionNumber")]public int QuestionNumber { get; set; }
        [JsonPropertyName("question")]public string question { get; set; }
        [JsonPropertyName("questionAnswer")]public string questionAnswer { get; set; }
        [JsonPropertyName("score")]public double Score { get; set; }

        public Question(){}

        public Question(string question, double score, int questionNumber)
        {
            this.question = question;
            this.Score = score;
            this.QuestionNumber = questionNumber;
        }

        public Question(int questionNumber, string question, string questionAnswer, double score)
        {
            QuestionNumber = questionNumber;
            this.question = question;
            this.questionAnswer = questionAnswer;
            Score = score;
        }
    }
}