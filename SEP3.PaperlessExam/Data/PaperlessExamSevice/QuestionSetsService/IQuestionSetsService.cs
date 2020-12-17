using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc.TagHelpers.Cache;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService
{
    public interface IQuestionSetsService
    {
        Task<MultipleChoiceSet> CreateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);
        Task<MultipleChoiceSet> DeleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);
        Task<WrittenSet> CreateWrittenSet(WrittenSet writtenSet);
        Task<WrittenSet> DeleteWrittenSet(WrittenSet writtenSet);
        Task<MultipleChoiceQuestion> RemoveMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);
        Task <WrittenSet> GetWrittenSetWithAllChildElements( long writtenSetId);
        Task<WrittenSet> UpdateWrittenSet(WrittenSet writtenSet);
        Task<MultipleChoiceSet> GetMultipleChoiceSetWithAllChildElements(long multipleChoiceSetId);
        Task<MultipleChoiceSet> UpdateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);
        Task<IList<WrittenSet>> GetWrittenSets(User currentUser);
        Task<IList<MultipleChoiceSet>> GetMultipleChoiceSets(User currentUser);
    }
    
    
    
    
    
}