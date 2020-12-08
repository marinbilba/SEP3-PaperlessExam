using System.Collections.Generic;
using SEP3.PaperlessExam.Pages.TeacherView.QuestionManagement.CreateQuestionSet;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceQuestion : Question
    {
        
        public MultipleChoiceSet MultipleChoiceSet { get; set; }
        public List<QuestionOption> QuestionOptions = new List<QuestionOption>();

        public MultipleChoiceQuestion()
        {
           
        }

        public MultipleChoiceQuestion(int questionNumber, string question, double score, MultipleChoiceSet multipleChoiceSet) : base(question, score, questionNumber)
        {
            MultipleChoiceSet = multipleChoiceSet;
        }

        public MultipleChoiceQuestion(MultipleChoiceSet multipleChoiceSet)
        {
            MultipleChoiceSet = multipleChoiceSet;
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