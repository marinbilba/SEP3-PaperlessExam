using System.Collections.Generic;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceQuestion : Question
    {
        public MultipleChoiceSet MultipleChoiceSet { get; set; }
        public List<QuestionOption> QuestionOptions = new List<QuestionOption>();

        public MultipleChoiceQuestion()
        {
           
        }

        public MultipleChoiceQuestion(int questionNumber, string question, double score) : base(question, score, questionNumber)
        {
                
        }

        public void AddQuestionOption(QuestionOption questionOption)
        {
            QuestionOptions.Add(questionOption);
            questionOption.MultipleChoiceQuestion = this;
        }

        public void RemoveQuestionOption(QuestionOption questionOption)
        {
            QuestionOptions.Remove(questionOption);
            questionOption.MultipleChoiceQuestion = null;
        }

        public void RemoveQuestionOption(int i)
        {
            QuestionOptions.RemoveAt(i);
        }
        
        public List<QuestionOption> GetQuestions()
        {
            return QuestionOptions;
        }
    }
}