using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceQuestion : Question
    {
        [JsonPropertyName("questionOptions")] public List<QuestionOption> MultipleChoiceQuestionsOption { get; set; }

       

        public MultipleChoiceQuestion()
        {
            MultipleChoiceQuestionsOption=new List<QuestionOption>();
        }

        public MultipleChoiceQuestion(string question, double score, int questionNumber) : base(question, score, questionNumber)
        {
            MultipleChoiceQuestionsOption=new List<QuestionOption>();
        }

        public void AddQuestionOption(QuestionOption questionOption)
        {
            MultipleChoiceQuestionsOption.Add(questionOption);
        }
        public void RemoveQuestionOption(QuestionOption questionOption)
        {
            MultipleChoiceQuestionsOption.Remove(questionOption);
        }
        
    }
}