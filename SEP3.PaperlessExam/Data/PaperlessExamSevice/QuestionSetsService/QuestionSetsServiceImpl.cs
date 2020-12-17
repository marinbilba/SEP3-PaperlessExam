using System.Threading.Tasks;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService
{
    public class QuestionSetsServiceImpl : IQuestionSetsService
    {
        private string uri = "http://localhost:8080";
        private readonly HttpClient client;

        public QuestionSetsServiceImpl()
        {
            client = new HttpClient();
        }

        public async Task<MultipleChoiceSet> CreateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet)
        {
            MultipleChoiceSet multipleChoiceSetDeserialize = null;
            HttpResponseMessage responseMessage;
            string multipleChoiceSetSerialized = JsonSerializer.Serialize(multipleChoiceSet);
            var content = new StringContent(multipleChoiceSetSerialized, Encoding.UTF8, "application/json");

            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/createMultipleChoiceSet", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection... Gfckyourself");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                multipleChoiceSetDeserialize = JsonSerializer.Deserialize<MultipleChoiceSet>(readAsStringAsync);
                Console.WriteLine(multipleChoiceSetDeserialize.User.Username);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.Unauthorized)
            {
                throw new Exception(serverMessage);
            }

            return multipleChoiceSetDeserialize;

            // return new MultipleChoiceSet("valera", "jora", new User());
        }

        public async Task<MultipleChoiceSet> DeleteMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet)
        {
            MultipleChoiceSet multipleChoiceSetDeserialize = null;
            HttpResponseMessage responseMessage;
            string multipleChoiceSetSerialized = JsonSerializer.Serialize(multipleChoiceSet);
            Console.WriteLine(multipleChoiceSetSerialized);
            var content = new StringContent(multipleChoiceSetSerialized, Encoding.UTF8, "application/json");

            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/deleteMultipleChoiceSet", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection... Gfckyourself");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                multipleChoiceSetDeserialize = JsonSerializer.Deserialize<MultipleChoiceSet>(readAsStringAsync);
                Console.WriteLine(multipleChoiceSetDeserialize.User.Username);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }

            return multipleChoiceSetDeserialize;
        }
        public async Task<WrittenSet> CreateWrittenSet(WrittenSet writtenSet)
        {
            WrittenSet writtenSetDeserealize = null;
            HttpResponseMessage responseMessage;
            string writtenSetSerialize = JsonSerializer.Serialize(writtenSet);
            var content = new StringContent(writtenSetSerialize, Encoding.UTF8, "application/json");
            Console.WriteLine(writtenSet);
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/createWrittenSet", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection... Gfckyourself2");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                writtenSetDeserealize = JsonSerializer.Deserialize<WrittenSet>(readAsStringAsync);
                Console.WriteLine(writtenSetSerialize);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return writtenSetDeserealize;
        }
        public async Task<WrittenSet> DeleteWrittenSet(WrittenSet writtenSet)
        {
            WrittenSet deletedWrittenSet = null;
            HttpResponseMessage responseMessage;
            string writtenSetSerialize = JsonSerializer.Serialize(writtenSet);
            Console.WriteLine(writtenSetSerialize);
            var content = new StringContent(writtenSetSerialize, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/deleteWrittenSet", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection... ");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                deletedWrittenSet = JsonSerializer.Deserialize<WrittenSet>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }

            return deletedWrittenSet;
        }
        public async Task<MultipleChoiceQuestion> RemoveMultipleChoiceQuestion(
            MultipleChoiceQuestion multipleChoiceQuestion)
        {
            MultipleChoiceQuestion multipleChoiceQuestionDeserealize = null;
            HttpResponseMessage responseMessage;
            string multipleChoiceQuestionSerialize = JsonSerializer.Serialize(multipleChoiceQuestion);
            var content = new StringContent(multipleChoiceQuestionSerialize, Encoding.UTF8, "application/json");
            Console.WriteLine(multipleChoiceQuestionSerialize);
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/deleteMultipleChoiceQuestion", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection... Gfckyourself2");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                multipleChoiceQuestionDeserealize =
                    JsonSerializer.Deserialize<MultipleChoiceQuestion>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return multipleChoiceQuestionDeserealize;
        }

        public async Task<WrittenSet> GetWrittenSetWithAllChildElements(long writtenSetId)
        {
            WrittenSet writtenSetDeserealize = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/questionsets/getWrittenSetWithAllChildElements/{writtenSetId}");
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection... ");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                writtenSetDeserealize =
                    JsonSerializer.Deserialize<WrittenSet>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return writtenSetDeserealize;
        }

        public async Task<WrittenSet> UpdateWrittenSet(WrittenSet writtenSet)
        {
            WrittenSet writtenSetDeserealize = null;
            HttpResponseMessage responseMessage;
            string writtenSetSerialize = JsonSerializer.Serialize(writtenSet);
            var content = new StringContent(writtenSetSerialize, Encoding.UTF8, "application/json");
            Console.WriteLine(writtenSet);
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/updateWrittenSet", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection...");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                writtenSetDeserealize = JsonSerializer.Deserialize<WrittenSet>(readAsStringAsync);
                Console.WriteLine(writtenSetSerialize);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return writtenSetDeserealize;
        }

        public async Task<MultipleChoiceSet> GetMultipleChoiceSetWithAllChildElements(long multipleChoiceSetId)
        {
            MultipleChoiceSet multipleChoiceSetDeserealize = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/questionsets/getMultipleChoiceSetWithAllChildElements/{multipleChoiceSetId}");
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection...");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                multipleChoiceSetDeserealize = JsonSerializer.Deserialize<MultipleChoiceSet>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
          

            return multipleChoiceSetDeserealize;
        }

        public async Task<MultipleChoiceSet> UpdateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSetToUpdate)
        {
            MultipleChoiceSet multipleChoiceSetDeserealize = null;
            HttpResponseMessage responseMessage;
            string multipleChoiceSetToUpdateSerialize = JsonSerializer.Serialize(multipleChoiceSetToUpdate);
            var content = new StringContent(multipleChoiceSetToUpdateSerialize, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/updateMultipleChoiceSet", content);
                // 2. Check if the resource was found, else throw exception to the client
                if (responseMessage.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new Exception("Ooops, resource not found");
                }
            }
            // 3. Catch the exception in case the Server is not running
            catch (HttpRequestException e)
            {
                throw new Exception("No connection...");
            }

            string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // 4. Check the response status codes, else throws the error message to the client

            if (responseMessage.IsSuccessStatusCode)
            {
                // 5. Deserialize the object
                string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
                multipleChoiceSetDeserealize = JsonSerializer.Deserialize<MultipleChoiceSet>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return multipleChoiceSetDeserealize;
        }
        //TODO change uri
        public async Task<IList<WrittenSet>> GetWrittenSets(User currentUser)
        {
            IList<WrittenSet> writtenSetsDeserialize = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/questionsets/getAllUsersWrittenSets/{currentUser.Username}");
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
                writtenSetsDeserialize = JsonSerializer.Deserialize<IList<WrittenSet>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return writtenSetsDeserialize;
        }

        public async Task<IList<MultipleChoiceSet>> GetMultipleChoiceSets(User currentUser)
        {
            IList<MultipleChoiceSet> multipleChoiceSetDeserialize = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/questionsets/getAllUsersMultipleChoiceSets/{currentUser.Username}");
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
                multipleChoiceSetDeserialize = JsonSerializer.Deserialize<IList<MultipleChoiceSet>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return multipleChoiceSetDeserialize;
        }
    }
}