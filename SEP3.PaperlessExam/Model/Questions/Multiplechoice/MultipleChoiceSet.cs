using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.MultipleChoice
{
    public class MultipleChoiceSet : QuestionsSet
    {
        
        [JsonPropertyName("user")]public User User { get; set; }
        
        [JsonPropertyName("updatedTimestamp")] public DateTime UpdatedTimestamp { get; set; }

        [JsonPropertyName("multipleChoiceQuestionList")]  public List<MultipleChoiceQuestion> MultipleChoiceQuestions { get; set; }
        
        public MultipleChoiceSet()
        {
            MultipleChoiceQuestions=new List<MultipleChoiceQuestion>();
    
        }

        public MultipleChoiceSet(string title, string topic, User user) : base(title, topic)
        { MultipleChoiceQuestions=new List<MultipleChoiceQuestion>();
            User = user;
        }
        
        public void AddQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
        {
            
            MultipleChoiceQuestions.Add(multipleChoiceQuestion);

        }
        
        public void RemoveQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
        {
            MultipleChoiceQuestions.Remove(multipleChoiceQuestion);
        }

     
    }
}