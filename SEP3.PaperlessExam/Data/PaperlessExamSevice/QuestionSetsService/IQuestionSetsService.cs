using System.Threading.Tasks;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService
{
    public interface IQuestionSetsService
    {
        Task<bool> ValidateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);

        Task<MultipleChoiceSet> GetMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet);
    }
    
    
    
    
    
}