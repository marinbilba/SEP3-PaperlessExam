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
            User userDeserialize = null;
            HttpResponseMessage responseMessage;
            string userSerialized = JsonSerializer.Serialize(user);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/login", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running 
            catch (HttpRequestException e)
            {
                throw new Exception("No connection could be made because the server is not responding");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client
            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                userDeserialize = JsonSerializer.Deserialize<User>(readAsStringAsync);
                Console.WriteLine(userDeserialize.Role.Name);
            }

            else if (responseMessage.StatusCode == HttpStatusCode.Forbidden)
            {
                Console.WriteLine();

                throw new Exception(serverMessage);
            }else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }

            return userDeserialize;
        }

        //
        // public async Task CreateUserAsync(User user)
        // {
        //     string accountSerialized = JsonSerializer.Serialize(user);
        //     StringContent content = new StringContent(accountSerialized, Encoding.UTF8, "application/json");
        //     HttpResponseMessage responseMessage = await client.PostAsync(uri + "/createUser", content);
        //     Console.WriteLine(responseMessage.ToString());
        // }
    }
}