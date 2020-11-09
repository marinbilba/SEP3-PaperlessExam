// TEMP CLASS
namespace SEP3.PaperlessExam.Model
{
    public class User
    {
        private int id;
        private string username;
        private string password;

        
        public User(string username, string password)
        {
    
            this.username = username;
            this.password = password;
        }

        public int Id
        {
            get => id;
            set => id = value;
        }

        public string Username
        {
            get => username;
            set => username = value;
        }

        public string Password
        {
            get => password;
            set => password = value;
        }
    }
    
}