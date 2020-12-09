using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class QuestionOption
    {
        [JsonPropertyName("id")] public long Id { get; set; }
        [JsonPropertyName("correctAnswer")] public bool CorrectAnswer { get; set; }
        [JsonPropertyName("answer")] public string Answer { get; set; }

        [JsonPropertyName("multiplechoicequestion")]public MultipleChoiceQuestion MultipleChoiceQuestion { get; set; }
        
        public QuestionOption() {}

        public QuestionOption(bool correctAnswer, string answer, MultipleChoiceQuestion multipleChoiceQuestion)
        {
            CorrectAnswer = correctAnswer;
            Answer = answer;
            MultipleChoiceQuestion = multipleChoiceQuestion;
        }
    }
}