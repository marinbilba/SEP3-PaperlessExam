using System;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions
{
    public class QuestionsSet
    {      [JsonPropertyName("id")]public long Id { get; set; }
        [JsonPropertyName("title")]public string Title { get; set; }
        [JsonPropertyName("topic")]public string Topic { get; set; }

        public QuestionsSet()
        {
        }

        public QuestionsSet(string title, string topic)
        {
            Title = title;
            Topic = topic;
        }
        
    }
}