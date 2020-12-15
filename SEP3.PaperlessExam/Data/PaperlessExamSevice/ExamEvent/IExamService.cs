using System.Collections.Generic;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.ExamEvent;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;
using SEP3.PaperlessExam.Model.StudentSubmitPaper;
using SEP3.PaperlessExam.Model.TeacherPaperEvaluation;

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
        Task<ExaminationEvent> GetExaminationPaper(string examId);
        Task<StudentSubmitExaminationPaper> SubmitStudentPaper(StudentSubmitExaminationPaper studentSubmitExaminationPaper);
        Task<StudentSubmitExaminationPaper> GetStudentSubmittedPaper(int studentId, string examId);
        Task<TeacherEvaluationPaperResult> SubmitEvaluatedStudentPaperByTeacher(TeacherEvaluationPaperResult teacherEvaluationPaperResult);

        Task<TeacherEvaluationPaperResult> GetExaminationEventResultByExamIdAndStudentId( long currentUserId,long examId);
        
    }
}