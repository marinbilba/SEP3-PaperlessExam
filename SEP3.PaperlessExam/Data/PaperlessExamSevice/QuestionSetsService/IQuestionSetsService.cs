using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService
{
    public interface IQuestionSetsService
    {
        Task<bool> ValidateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);
        Task<IList<WrittenSet>> FindWrittenSets(string type);
        Task<IList<MultipleChoiceSet>> FindMultipleChoiceSets(string type);

        Task<WrittenSet> DeleteWritten(WrittenSet set);
        Task<MultipleChoiceSet> DeleteMultipleChoice(MultipleChoiceSet set);
        Task<WrittenSet> FindWrittenSet(WrittenSet writtenSet);
        Task<MultipleChoiceSet> FindMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);

        Task<WrittenSet> UpdateWrittenSet(WrittenSet writtenSet);
        Task<MultipleChoiceSet> UpdateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);

    }
}