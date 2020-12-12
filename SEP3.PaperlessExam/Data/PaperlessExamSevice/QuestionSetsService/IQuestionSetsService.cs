using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc.TagHelpers.Cache;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService
{
    public interface IQuestionSetsService
    {
        Task<MultipleChoiceSet> CreateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);

        Task<MultipleChoiceQuestion> AddMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);

        Task<QuestionOption> AddMultipleChoiceQuestionOption(QuestionOption questionOption);

        Task<MultipleChoiceSet> DeleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);

        Task<MultipleChoiceQuestion> DeleteMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);
        
        Task<QuestionOption> DeleteMultipleChoiceQuestionOption(QuestionOption questionOption);
        
        Task<WrittenSet> CreateWrittenSet(WrittenSet writtenSet);

        Task<WrittenQuestion> AddWrittenQuestion(WrittenQuestion writtenQuestion);

        Task<WrittenSet> DeleteWrittenSet(WrittenSet writtenSet);

        Task<WrittenQuestion> DeleteWrittenQuestion(WrittenQuestion writtenQuestion);
        Task<MultipleChoiceQuestion> RemoveMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion);
        Task <WrittenSet> GetWrittenSetWithAllChildElements( long writtenSetId);
    }
    
    
    
    
    
}