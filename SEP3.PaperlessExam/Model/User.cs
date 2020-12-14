// TEMP CLASS

using System.Text.Json.Serialization;

namespace SEP3.PaperlessExam.Model
{
    public class User
    {
        [JsonPropertyName("id")] public int Id { get; set; }
        [JsonPropertyName("username")] public string Username { get; set; }
        [JsonPropertyName("email")] public string Email { get; set; }
        [JsonPropertyName("password")] public string Password { get; set; }
        [JsonPropertyName("confirmPassword")] public string ConfirmPassword { get; set; }
        [JsonPropertyName("firstName")] public string FirstName { get; set; }
         [JsonPropertyName("lastName")] public string LastName { get; set; }
        [JsonPropertyName("role")] public Role Role { get; set; }
        // required for evaluate exam for teacher
        public bool CanEvaluate { get; set; }
        
        public User()
        {  }
        
        public User(string username, string password)
        {
    
            this.Username = username;
            this.Password = password;
        }

        public User(string username, string email, string password, string confirmPassword, string firstName, string lastName, Role role)
        {
            Username = username;
            Email = email;
            Password = password;
            ConfirmPassword = confirmPassword;
            FirstName = firstName;
            LastName = lastName;
            Role = role;
        }

        public bool Equals( User y)
        {
            if (ReferenceEquals(this, y)) return true;
            if (ReferenceEquals(this, null)) return false;
            if (ReferenceEquals(y, null)) return false;
            if (this.GetType() != y.GetType()) return false;
            return this.Id == y.Id && this.Username == y.Username && this.Email == y.Email &&
                   this.Password == y.Password &&
                   this.ConfirmPassword == y.ConfirmPassword && this.FirstName == y.FirstName &&
                   this.LastName == y.LastName &&
                   this.Role.Equals(y.Role);
        }

    }
}