using System;
using System.Text.Json.Serialization;
using SEP3.PaperlessExam.Model.StudentSubmitPaper;

namespace SEP3.PaperlessExam.Model.TeacherPaperEvaluation
{
    public class TeacherEvaluationPaperResult
    {
        [JsonPropertyName("id")] public long Id { get; set; }
        [JsonPropertyName("submitTimestamp")]     public DateTime SubmitTimestamp { get; set; }
        [JsonPropertyName("evaluatedBy")]  public User EvaluatedBy { get; set; }
        [JsonPropertyName("studentSubmitExaminationPaper")]  public StudentSubmitExaminationPaper StudentSubmitExaminationPaper { get; set; }
        [JsonPropertyName("score")] public double Score { get; set; }
        [JsonPropertyName("multipleChoiceSetsTotalScore")]public double MultipleChoiceSetsTotalScore { get; set; }
        [JsonPropertyName("writtenSetsTotalScore")]public double WrittenSetsTotalScore { get; set; }

        public TeacherEvaluationPaperResult()
        {
        }

        public TeacherEvaluationPaperResult(DateTime submitTimestamp, User evaluatedBy, StudentSubmitExaminationPaper studentSubmitExaminationPaper, double score)
        {
            SubmitTimestamp = submitTimestamp;
            EvaluatedBy = evaluatedBy;
            StudentSubmitExaminationPaper = studentSubmitExaminationPaper;
            Score = score;
        }
    }
    
}