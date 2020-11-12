using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Pages.AdminView.ManageAccounts;

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

        public async Task<User> LoginUser(User user)
        {
            User userDeserialize;
            HttpResponseMessage responseMessage;
            string userSerialized = JsonSerializer.Serialize(user);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");
            // Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/login", content);

                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception(responseMessage.ReasonPhrase);
                }
            }
            catch (HttpRequestException e)
            {
             
                throw new Exception("No connection could be made because the server is not responding");
            }

            string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();


            userDeserialize = JsonSerializer.Deserialize<User>(readAsStringAsync);
            Console.WriteLine(userDeserialize.Role.Name);

            //return userDeserialize;

            return new User("1111", "2222", "3333", "4444", "5555", new Role(2, "s"));
        }
        
        public async Task CreateUserAsync(User user)
        {
            string accountSerialized = JsonSerializer.Serialize(user);
            StringContent content = new StringContent(accountSerialized, Encoding.UTF8, "application/json");
            HttpResponseMessage responseMessage = await client.PostAsync(uri + "/createUser", content);
            Console.WriteLine(responseMessage.ToString());
        }
    }
}