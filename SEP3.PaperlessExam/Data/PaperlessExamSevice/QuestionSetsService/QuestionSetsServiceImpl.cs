using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.Questions;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;
using SEP3.PaperlessExam.Pages.TeacherView.QuestionManagement.CreateQuestionSet;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService
{
    public class QuestionSetsServiceImpl : IQuestionSetsService
    {
        private string uri = "";
        private readonly HttpClient client;
       
        
        public Task<bool> ValidateMultipleChoiceSet(MultipleChoiceSet multipleChoiceSet)
        {
            throw new System.NotImplementedException();
        }
        
        
        
        public async Task<IList<WrittenSet>>   FindWrittenQuestion(string type)
        {
            IList<WrittenSet> setsDeserialize = new List<WrittenSet>();
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/set/getSetByType/{type}");
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
                setsDeserialize = JsonSerializer.Deserialize<IList<WrittenSet>>(readAsStringAsync);
            
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }
            
           // WrittenSet q1 = new WrittenSet("caca", "maca", new User("steve", "fghjkl"));
           //
           //
           //  setsDeserialize.Add(q1);
            return setsDeserialize;
        }
        
         public async Task<IList<MultipleChoiceSet>>   FindMultipleChoiceQuestion(string type)
        {
            IList<MultipleChoiceSet> setsDeserialize = new List<MultipleChoiceSet>();
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/set/getSetByType/{type}");
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
                setsDeserialize = JsonSerializer.Deserialize<IList<MultipleChoiceSet>>(readAsStringAsync);
                if (type == "Written")
                {
                   
                }
                else if (type == "Multiple choice")
                {
                    
                }
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }
            
           // MultipleChoiceSet q2 = new MultipleChoiceSet("dfg", "mate", new User("mama", "yaa"));
           //
           //  setsDeserialize.Add(q2);
            return setsDeserialize;
        }
    }
}