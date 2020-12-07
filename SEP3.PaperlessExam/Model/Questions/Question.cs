using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions
{
    public class Question
    {
        [JsonPropertyName("questionNumber")]public int QuestionNumber { get; set; }
        [JsonPropertyName("question")]public string question { get; set; }
        [JsonPropertyName("score")]public double Score { get; set; }

        public Question(){}

        public Question(string question, double score, int questionNumber)
        {
            this.question = question;
            this.Score = score;
            this.QuestionNumber = questionNumber;
        }
    }
}