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
        Task<IList<WrittenSet>> FindWrittenQuestion(string type);
        Task<IList<MultipleChoiceSet>> FindMultipleChoiceQuestion(string type);
        
    }
}