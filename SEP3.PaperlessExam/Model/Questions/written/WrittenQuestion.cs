using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenQuestion : Question
    {
        [JsonPropertyName("questionAnswer")]public string questionAnswer { get; set; }
        [JsonPropertyName("studentScore")] public double StudentScore { get; set; }
        public WrittenQuestion(){}

        public WrittenQuestion(string question, double score, int questionNumber, string questionAnswer) : base(question, score, questionNumber)
        {
            this.questionAnswer = questionAnswer;
        }
    }
}