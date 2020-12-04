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
        
        
        public User()
        {  }
        
        public User(string username, string password)
        {
    
            this.Username = username;
            this.Password = password;
        }
        
        public User(string firstName, string lastName, string username, string password, string confirmPassword, Role role, string email)
        {

            this.FirstName = firstName;
            this.LastName = lastName;
            this.Username = username;
            this.Password = password;
            this.ConfirmPassword = confirmPassword;
            this.Role = role;
        }
        

    }
    
}