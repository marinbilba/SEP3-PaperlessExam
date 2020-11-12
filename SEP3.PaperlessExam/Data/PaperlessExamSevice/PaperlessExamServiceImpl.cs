using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice
{
    public class PaperlessExamServiceImpl : IPaperlessExamService
    {
        private string uri = "http://localhost:8080";
        private readonly HttpClient client;
   

        public PaperlessExamServiceImpl()
        {
            client = new HttpClient();
        }

        public  async Task<User> LoginUser(User user)
        {
            User userDeserialize;
            string userSerialized = JsonSerializer.Serialize(user);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");

            HttpResponseMessage responseMessage =
               await client.PostAsync(uri + "/login", content);
            
            string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();

            try
            {
                userDeserialize = JsonSerializer.Deserialize<User>(readAsStringAsync);
                Console.WriteLine(userDeserialize.Role.Name);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                throw;
            }
            //return userDeserialize;
           
       return new User("1111", "2222", "3333", "4444", "5555", new Role(2, "s"));
        }
    }
}

