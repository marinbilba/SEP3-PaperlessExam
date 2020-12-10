using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceSet : QuestionsSet
    {
        
        [JsonPropertyName("user")]public User User { get; set; }
        
        [JsonPropertyName("updatedtimestamp")] public DateTime UpdatedTimestamp { get; set; }

        public List<MultipleChoiceQuestion> MultipleChoiceQuestions = new List<MultipleChoiceQuestion>();

        public MultipleChoiceSet()
        {
            
        }

        public MultipleChoiceSet(string title, string topic, User user) : base(title, topic)
        {
            User = user;
        }
        
        public void AddQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
        {
            
            MultipleChoiceQuestions.Add(multipleChoiceQuestion);

        }
        
        public void RemoveLastQuestion()
        {
            MultipleChoiceQuestions.RemoveAt(MultipleChoiceQuestions.Count-1);
        }
        
        public MultipleChoiceQuestion GetLastQuestion()
        {
            return MultipleChoiceQuestions[MultipleChoiceQuestions.Count - 1];
        }    
        
        public MultipleChoiceQuestion GetQuestionBeforeLastQuestion()
        {
            return MultipleChoiceQuestions[MultipleChoiceQuestions.Count-2];
        }
    }
}