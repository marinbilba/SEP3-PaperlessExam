using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class QuestionOption
    {
        [JsonPropertyName("id")] public long Id { get; set; }
        [JsonPropertyName("correctAnswer")] public bool CorrectAnswer { get; set; }
        [JsonPropertyName("answer")] public string Answer { get; set; }

     
        
        public QuestionOption() {}

        public QuestionOption(bool correctAnswer, string answer)
        {
            CorrectAnswer = correctAnswer;
            Answer = answer;
        }
        
    }
}