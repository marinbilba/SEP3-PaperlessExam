using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice
{
    public class PaperlessExamServiceImpl :IPaperlessExamService
    {
        private string uri = "http://localhost:8080/";
        private readonly HttpClient client;

        public PaperlessExamServiceImpl()
        {
            client = new HttpClient();
        }

        public async Task<User> LoginUser(User user)
        {
          string userSerialized = JsonSerializer.Serialize(user);
             StringContent content=new StringContent(userSerialized,Encoding.UTF8,"application/json");
         
             HttpResponseMessage responseMessage =
                 await client.PostAsync(uri+"/login", content);
       
       //here the user object should be deserialized
        string s=   await  responseMessage.Content.ReadAsStringAsync();
        Console.WriteLine(responseMessage);
        return null;
        }
    }
 
}