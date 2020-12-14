using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;
using SEP3.PaperlessExam.Model.ExamEvent;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Model.StudentSubmitPaper
{
    public class StudentSubmitExaminationPaper
    {
        [JsonPropertyName("id")] public long Id { get; set; }
        [JsonPropertyName("submitTimestamp")]public DateTime SubmitTimestamp { get; set; }
        [JsonPropertyName("submitBy")]  public User SubmitBy { get; set; }

        [JsonPropertyName("examinationEvent")]   public ExaminationEvent ExaminationEvent { get; set; }
        [JsonPropertyName("submitWrittenSets")]  public IList<WrittenSet> WrittenSets { get; set; }
        [JsonPropertyName("submitMultipleChoiceSets")]  public IList<MultipleChoiceSet> MultipleChoiceSet { get; set; }

        public StudentSubmitExaminationPaper()
        {
        }

        public StudentSubmitExaminationPaper(DateTime submitTimestamp, User submitBy, ExaminationEvent examinationEvent, IList<WrittenSet> writtenSets, IList<MultipleChoiceSet> multipleChoiceSet)
        {
            SubmitTimestamp = submitTimestamp;
            this.SubmitBy = submitBy;
            ExaminationEvent = examinationEvent;
            WrittenSets = writtenSets;
            MultipleChoiceSet = multipleChoiceSet;
        }
    }
}