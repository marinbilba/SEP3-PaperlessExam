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

        public MultipleChoiceSet()
        {
        }

        public MultipleChoiceSet(string title, string topic, DateTime dateTime, User user) : base(title, topic)
        {
            User = user;
        }
        
    }
}