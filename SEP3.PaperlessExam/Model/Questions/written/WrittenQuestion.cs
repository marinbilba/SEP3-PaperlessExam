using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenQuestion : Question
    {
        [JsonPropertyName("writtenset")] public WrittenSet WrittenSet { get; set; }

        
        
        public WrittenQuestion(){}

        public WrittenQuestion(string question, double score, int questionNumber, WrittenSet writtenSet) : base(question, score, questionNumber)
        {
            WrittenSet = writtenSet;
        }
        
        
    }
}