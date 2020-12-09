using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.ExamEvent
{
    public class ExamService : IExamService
    {
        private IList<WrittenSet> _writtenSets;
        private IList<MultipleChoiceSet> _multipleChoiceSets;

        private string uri = "";
        private readonly HttpClient client;

        public async Task<WrittenSet> AddWrittenSet(WrittenSet set)
        {
            WrittenSet writtenSetDeserialize = new WrittenSet();
            HttpResponseMessage responseMessage;
            string writtenSetSerialized = JsonSerializer.Serialize(set);
            var content = new StringContent(writtenSetSerialized, Encoding.UTF8, "application/json");
            try
            {
                responseMessage =
                    await client.GetAsync(uri+ "/exam/addWritten");
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
               writtenSetDeserialize = JsonSerializer.Deserialize<WrittenSet>(readAsStringAsync);
            
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
           

            return writtenSetDeserialize;
            }

        public async Task<MultipleChoiceSet> AddMultipleChoiceSet(MultipleChoiceSet set)
        {
            MultipleChoiceSet multipleChoiceSetDeserialize = new MultipleChoiceSet();
            HttpResponseMessage responseMessage;
            string multipleChoiceSetSerialized = JsonSerializer.Serialize(set);
            var content = new StringContent(multipleChoiceSetSerialized, Encoding.UTF8, "application/json");
            try
            {
                responseMessage =
                    await client.GetAsync(uri+ "/exam/addWritten");
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
                multipleChoiceSetDeserialize = JsonSerializer.Deserialize<MultipleChoiceSet>(readAsStringAsync);
            
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }

            return multipleChoiceSetDeserialize;
        }
    }
}