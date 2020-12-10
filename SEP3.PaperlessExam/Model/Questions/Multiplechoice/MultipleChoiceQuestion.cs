using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceQuestion : Question
    {
        [JsonPropertyName("multipleChoiceSet")] public MultipleChoiceSet MultipleChoiceSet { get; set; }
        public List<QuestionOption> QuestionOptions = new List<QuestionOption>();

       

        public MultipleChoiceQuestion()
        {
           
        }

        public MultipleChoiceQuestion(string question, double score, int questionNumber, MultipleChoiceSet multipleChoiceSet) : base(question, score, questionNumber)
        {
            MultipleChoiceSet = multipleChoiceSet;
        }

        public void RemoveQuestionOption(QuestionOption questionOption)
        {
            QuestionOptions.Remove(questionOption);
        }
        public void AddQuestionOption(QuestionOption questionOption)
        {
            QuestionOptions.Add(questionOption);
            
        }
    }
}