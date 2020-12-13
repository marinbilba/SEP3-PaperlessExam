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
        [JsonPropertyName("id")] public long Id { get; set; }

        [JsonPropertyName("examTitle")] public string ExamTitle { get; set; }
        [JsonPropertyName("examTimeDuration")] public string ExamTimeDuration { get; set; }
        [JsonPropertyName("writtenSets")] public IList<WrittenSet> WrittenSets { get; set; }

        [JsonPropertyName("multipleChoiceSets")]
        public IList<MultipleChoiceSet> MultipleChoiceSets { get; set; }

        [JsonPropertyName("usersAssigned")] public IList<User> UsersAssigned { get; set; }
        [JsonPropertyName("examDateAndTime")] public DateTime ExamDateAndTime { get; set; }
        [JsonPropertyName("updatedTimestamp")] private DateTime UpdatedTimestamp { get; set; }
        [JsonPropertyName("createdBy")] public User CreatedBy { get; set; }

        public ExaminationEvent()
        {
        }

        public ExaminationEvent(long id, string examTitle, IList<WrittenSet> writtenSets,
            IList<MultipleChoiceSet> multipleChoiceSets, IList<User> usersAssigned, DateTime examDateAndTime,
            DateTime updatedTimestamp, User createdBy)
        {
            Id = id;
            ExamTitle = examTitle;
            WrittenSets = writtenSets;
            MultipleChoiceSets = multipleChoiceSets;
            UsersAssigned = usersAssigned;
            ExamDateAndTime = examDateAndTime;
            UpdatedTimestamp = updatedTimestamp;
            CreatedBy = createdBy;
        }

        public ExaminationEvent(string examTitle, IList<WrittenSet> writtenSets,
            IList<MultipleChoiceSet> multipleChoiceSets, IList<User> usersAssigned, DateTime examDateAndTime,string examTimeDuration, 
            User createdBy)
        {
            ExamTitle = examTitle;
            WrittenSets = writtenSets;
            MultipleChoiceSets = multipleChoiceSets;
            UsersAssigned = usersAssigned;
            ExamDateAndTime = examDateAndTime;
            ExamTimeDuration = examTimeDuration;
            this.CreatedBy = createdBy;
        }
    }
}