using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice
{
    public class PaperlessExamServiceImpl :IPaperlessExamService
    {
        private string uri = "http://localhost:8080";
        private readonly HttpClient client;
        private User userDeserialize;

        public PaperlessExamServiceImpl()
        {
            client = new HttpClient();
        }

        public  User LoginUser(User user)
        {
          string userSerialized = JsonSerializer.Serialize(user);
             var content=new StringContent(userSerialized,Encoding.UTF8,"application/json");
         
             Task<HttpResponseMessage> responseMessage =
                  client.PostAsync(uri+"/login", content);
             string s =responseMessage.Result.Content.ReadAsStringAsync().Result;
             if (responseMessage.IsCompletedSuccessfully)
             {
                 Console.WriteLine(s);

                 Console.WriteLine("yes");

             }
             else
             {
                 Console.WriteLine(responseMessage.Result.StatusCode);
                 Console.WriteLine("no");
             }
             User userDeserialize = JsonSerializer.Deserialize<User>(s);




       //here the user object should be deserialized

        return new User("1111", "2222", "3333", "4444", "5555", new Role(2, "Student"));
        }
    }
 
}