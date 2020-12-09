﻿namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class QuestionOption
    {
        public long Id { get; set; }
        public bool CorrectAnswer { get; set; }
        public string Answer { get; set; }

        public MultipleChoiceQuestion MultipleChoiceQuestion { get; set; }
        
        public QuestionOption() {}

        public QuestionOption(bool correctAnswer, string answer)
        {
            CorrectAnswer = correctAnswer;
            Answer = answer;
        }
        
    }
}