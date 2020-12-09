using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceQuestion : Question
    {
        [JsonPropertyName("multiplechoiceset")] public MultipleChoiceSet MultipleChoiceSet { get; set; }
       
       

        public MultipleChoiceQuestion()
        {
           
        }

        public MultipleChoiceQuestion(string question, double score, int questionNumber, MultipleChoiceSet multipleChoiceSet) : base(question, score, questionNumber)
        {
            MultipleChoiceSet = multipleChoiceSet;
        }

    }
}