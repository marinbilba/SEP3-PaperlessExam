using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenSet : QuestionsSet
    {
        [JsonPropertyName("user")] public User User { get; set; } 
        
        [JsonPropertyName("updatedTimestamp")] public DateTime UpdatedTimestamp { get; set; }

        [JsonPropertyName("writtenQuestions")] public List<WrittenQuestion> WrittenQuestions { get; set; }
     
        public WrittenSet() {}

        public WrittenSet(string title, string topic, User user) : base(title, topic)
        {
            WrittenQuestions=new List<WrittenQuestion>();
            User = user;
        }
        
        

        public void AddQuestion(WrittenQuestion writtenQuestion)
        {
            WrittenQuestions.Add(writtenQuestion);
          
        }

        public void RemoveQuestion(WrittenQuestion writtenQuestion)
        {
            WrittenQuestions.Remove(writtenQuestion);
     
        }

        public List<WrittenQuestion> GetWrittenQuestions()
        {
            return WrittenQuestions;
        }
        
        public void RemoveLastQuestion()
        {
            WrittenQuestions.RemoveAt(WrittenQuestions.Count-1);
        }
        
        public WrittenQuestion GetQuestionBeforeLastQuestion()
        {
            return WrittenQuestions[WrittenQuestions.Count-2];
        }
        
        public WrittenQuestion GetLastQuestion()
        {
            return WrittenQuestions[WrittenQuestions.Count - 1];
        }
    }
}