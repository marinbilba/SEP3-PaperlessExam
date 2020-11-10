using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice
{
    public interface IPaperlessExamService
    {
        User LoginUser(User user);
    }
}