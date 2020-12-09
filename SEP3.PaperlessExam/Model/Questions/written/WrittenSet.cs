using System;
using System.Collections.Generic;

namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenSet : QuestionsSet
    {
        private List<WrittenQuestion> WrittenQuestions = new List<WrittenQuestion>();
        private User User;
        public WrittenSet() {}

        public WrittenSet(string title, string topic,DateTime dateTime, User user) : base(title, topic, dateTime)
        {
            User = user;
        }

        public void AddQuestion(WrittenQuestion writtenQuestion)
        {
            WrittenQuestions.Add(writtenQuestion);
            writtenQuestion.WrittenSet = this;
        }

        public void RemoveQuestion(WrittenQuestion writtenQuestion)
        {
            WrittenQuestions.Remove(writtenQuestion);
            writtenQuestion.WrittenSet = this;
        }

        public List<WrittenQuestion> GetWrittenQuestions()
        {
            return WrittenQuestions;
        }
    }
}