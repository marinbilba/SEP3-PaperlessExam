namespace SEP3.PaperlessExam.Model.Questions.written
{
    public class WrittenQuestion : Question
    {
        public WrittenSet WrittenSet { get; set; }
        
        public WrittenQuestion(){}

        public WrittenQuestion(string question, double score, int questionNumber) : base(question, score,
            questionNumber)
        {
            
        }
    }
}