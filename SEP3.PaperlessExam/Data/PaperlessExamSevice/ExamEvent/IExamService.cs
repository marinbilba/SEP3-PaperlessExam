using System.Threading.Tasks;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.ExamEvent
{
    public interface IExamService
    {
        Task<WrittenSet> AddWrittenSet(WrittenSet set);
        Task<MultipleChoiceSet> AddMultipleChoiceSet(MultipleChoiceSet set);
    }
}