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
            // MultipleChoiceSet multipleChoiceSetDeserialize = null;
            // HttpResponseMessage responseMessage;
            // string multipleChoiceSetSerialized = JsonSerializer.Serialize(multipleChoiceSet);
            // var content = new StringContent(multipleChoiceSetSerialized, Encoding.UTF8, "application/json");
            //
            // // 1. Send POST request
            // try
            // {
            //     responseMessage =
            //         await client.PostAsync(uri + "/questionsets/createMultipleChoiceSet", content);
            //     // 2. Check if the resource was found, else throw exception to the client
            //     if (responseMessage.StatusCode == HttpStatusCode.NotFound)
            //     {
            //         throw new Exception("Ooops, resource not found");
            //     }
            // }
            // // 3. Catch the exception in case the Server is not running
            // catch (HttpRequestException e)
            // {
            //     throw new Exception("No connection... Gfckyourself");
            // }
            //
            // string serverMessage = responseMessage.Content.ReadAsStringAsync().Result;
            // // 4. Check the response status codes, else throws the error message to the client
            //
            // if (responseMessage.IsSuccessStatusCode)
            // {
            //     // 5. Deserialize the object
            //     string readAsStringAsync = await responseMessage.Content.ReadAsStringAsync();
            //     multipleChoiceSetDeserialize = JsonSerializer.Deserialize<MultipleChoiceSet>(readAsStringAsync);
            //     Console.WriteLine(multipleChoiceSetDeserialize.User.Username);
            // }
            // else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            // {
            //     throw new Exception(serverMessage);
            // }
            // else if (responseMessage.StatusCode == HttpStatusCode.Unauthorized)
            // {
            //     throw new Exception(serverMessage);
            // }
            //
            // return multipleChoiceSetDeserialize;
            
            return new MultipleChoiceSet("valera", "jora", new User());
        }
        
        public async Task<MultipleChoiceQuestion> AddMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
        {
            MultipleChoiceQuestion multipleChoiceQuestionDeserialize = null;
            HttpResponseMessage responseMessage;
            string multipleChoiceQuestionSerialized = JsonSerializer.Serialize(multipleChoiceQuestion);
            var content = new StringContent(multipleChoiceQuestionSerialized, Encoding.UTF8, "application/json");

            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/addMultipleChoiceQuestion", content);
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
                multipleChoiceQuestionDeserialize = JsonSerializer.Deserialize<MultipleChoiceQuestion>(readAsStringAsync);
                Console.WriteLine(multipleChoiceQuestionDeserialize.question);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }
            
            return multipleChoiceQuestionDeserialize;
        } 
        
        public async Task<QuestionOption> AddMultipleChoiceQuestionOption(QuestionOption questionOption)
        {
            QuestionOption questionOptionDeserialize = null;
            HttpResponseMessage responseMessage;
            string questionOptionSerialize = JsonSerializer.Serialize(questionOption);
            var content = new StringContent(questionOptionSerialize, Encoding.UTF8, "application/json");
            Console.WriteLine(questionOption);
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/questionsets/addMultipleChoiceQuestionOption", content);
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
                questionOptionDeserialize = JsonSerializer.Deserialize<QuestionOption>(readAsStringAsync);
                Console.WriteLine(questionOptionDeserialize.Answer);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }
            
            return questionOptionDeserialize;
        } 
        
    }
    
    
}