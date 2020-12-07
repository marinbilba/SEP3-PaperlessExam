using System.Collections.Generic;
using System.Linq;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceSet : QuestionsSet
    {
        public List<MultipleChoiceQuestion> MultipleChoiceQuestions = new List<MultipleChoiceQuestion>();
        public User User;
        
        public MultipleChoiceSet() {}

        public MultipleChoiceSet(string title, string topic, User user) : base(title, topic)
        {
            User = user;
        }

        public void AddQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
        {
            MultipleChoiceQuestions.Add(multipleChoiceQuestion);
            multipleChoiceQuestion.MultipleChoiceSet = this;
        }

        public void RemoveQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
        {
            MultipleChoiceQuestions.Add(multipleChoiceQuestion);
            multipleChoiceQuestion.MultipleChoiceSet = this;
        }

        public void AddQuestionOption(MultipleChoiceQuestion multipleChoiceQuestion, QuestionOption questionOption)
        {
            foreach (var question in MultipleChoiceQuestions.Where(question => question.Equals(multipleChoiceQuestion)))
            {
                multipleChoiceQuestion.AddQuestionOption(questionOption);
            }
        }

        public List<MultipleChoiceQuestion> GetAllMultipleChoiceQuestions()
        {
            return MultipleChoiceQuestions;
        }
        
    }
}