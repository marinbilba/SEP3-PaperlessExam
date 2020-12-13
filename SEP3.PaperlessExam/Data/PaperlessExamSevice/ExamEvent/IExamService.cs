using System.Collections.Generic;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.ExamEvent;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.ExamEvent
{
    public interface IExamService
    {
 
        Task<IList<WrittenSet>> GetWrittenSets(User currentUser);
        Task<IList<MultipleChoiceSet>> GetMultipleChoiceSets(User currentUser);
        Task<ExaminationEvent> CreateExaminationEvent(ExaminationEvent examinationEvent);

        Task<IList<ExaminationEvent>> GetTeachersUpcomingExamEvents(int currentUserId);
        Task<IList<ExaminationEvent>> GetTeachersPassedExamEvents(int currentUserId);
        Task<IList<ExaminationEvent>> GetStudentsUpcomingExamEvents(int currentUserId);
        Task<IList<ExaminationEvent>> GetStudentsPassedExamEvents(int currentUserId);
        Task<IList<ExaminationEvent>> GetStudentsOngoingExamEvents(int currentUserId);
    }
}