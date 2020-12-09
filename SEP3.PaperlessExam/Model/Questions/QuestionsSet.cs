using System;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions
{
    public class QuestionsSet
    {
        [JsonPropertyName("title")]public string Title { get; set; }
        [JsonPropertyName("topic")]public string Topic { get; set; } 
        [JsonPropertyName("date")] public DateTime DateTime { get; set; }
        
        public QuestionsSet(){}

        public QuestionsSet(string title, string topic, DateTime dateTime)
        {
            this.Title = title;
            this.Topic = topic;
            this.DateTime = dateTime;
        }
    }
}