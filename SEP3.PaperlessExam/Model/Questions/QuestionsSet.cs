using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions
{
    public class QuestionsSet
    {
        [JsonPropertyName("title")]private string Title { get; set; }
        [JsonPropertyName("topic")]private string Topic { get; set; } 
        
        public QuestionsSet(){}

        public QuestionsSet(string title, string topic)
        {
            this.Title = title;
            this.Topic = topic;
        }
    }
}