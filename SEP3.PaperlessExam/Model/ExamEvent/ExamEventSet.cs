using System;
using System.Collections.Generic;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Model.ExamEvent
{
    public class ExamEventSet
    {
        public string Title { get; set; }
        public IList<WrittenSet> WrittenSets { get; set; }
        public IList<MultipleChoiceSet> MultipleChoiceSets { get; set; }
        public IList<User> Students { get; set; }
        public DateTime Date { get; set; }
        

        public ExamEventSet()
        {
            
        }

        public void AddWrittenSet(WrittenSet set)
        {
            WrittenSets.Add(set);
        }

        public void AddMultipleChoiceSet(MultipleChoiceSet set)
        {
            MultipleChoiceSets.Add(set);
        }

        public IList<WrittenSet> GetAllWrittenSets()
        {
            return WrittenSets;
        }

        public IList<MultipleChoiceSet> GetAllMultipleChoiceSets()
        {
            return MultipleChoiceSets;
        }

        public void DeleteLocalWritten(WrittenSet set)
        {
            WrittenSets.Remove(set);
        }

        public void DeleteLocalMultiple(MultipleChoiceSet set)
        {
            MultipleChoiceSets.Remove(set);
        }
    }
    
}