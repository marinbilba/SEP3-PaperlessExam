using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenSet : QuestionsSet
    {
        [JsonPropertyName("user")] public User User { get; set; } 
        
        [JsonPropertyName("updatedtimestamp")] public DateTime UpdatedTimestamp { get; set; }
        public WrittenSet() {}

        public WrittenSet(string title, string topic, User user) : base(title, topic)
        {
            User = user;
        }
    }
}