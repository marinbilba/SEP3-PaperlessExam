using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Model.ExamEvent
{
    public class ExaminationEvent
    {
        [JsonPropertyName("id")] private long id { get; set; }

        [JsonPropertyName("examTitle")] public string ExamTitle { get; set; }
        [JsonPropertyName("writtenSets")] public IList<WrittenSet> WrittenSets { get; set; }

        [JsonPropertyName("multipleChoiceSets")]
        public IList<MultipleChoiceSet> MultipleChoiceSets { get; set; }

        [JsonPropertyName("usersAssigned")] public IList<User> UsersAssigned { get; set; }
        [JsonPropertyName("examDateAndTime")] public DateTime ExamDateAndTime { get; set; }
        [JsonPropertyName("updatedTimestamp")] private DateTime UpdatedTimestamp { get; set; }

        public ExaminationEvent()
        {
        }

        public ExaminationEvent(string examTitle, IList<WrittenSet> writtenSets, IList<MultipleChoiceSet> multipleChoiceSets, IList<User> usersAssigned, DateTime examDateAndTime)
        {
            ExamTitle = examTitle;
            WrittenSets = writtenSets;
            MultipleChoiceSets = multipleChoiceSets;
            UsersAssigned = usersAssigned;
            ExamDateAndTime = examDateAndTime;
        }
        
    }
}