using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenQuestion : Question
    {
        public WrittenQuestion(){}

        public WrittenQuestion(string question, double score, int questionNumber ) : base(question, score, questionNumber)
        {
            
        }
        
        
    }
}