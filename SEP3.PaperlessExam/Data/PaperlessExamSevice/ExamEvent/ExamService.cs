using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;
using SEP3.PaperlessExam.Model.ExamEvent;
using SEP3.PaperlessExam.Model.Questions.MultipleChoice;
using SEP3.PaperlessExam.Model.Questions.written;
using SEP3.PaperlessExam.Model.StudentSubmitPaper;
using SEP3.PaperlessExam.Model.TeacherPaperEvaluation;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice.ExamEvent
{
    public class ExamService : IExamService
    {
        
        private string uri = "http://localhost:8005";
             private readonly HttpClient client;

             public ExamService()
             {
                 client = new HttpClient();
             }
             
     


        public async Task<ExaminationEvent> CreateExaminationEvent(ExaminationEvent examinationEvent)
        {
            ExaminationEvent examinationEventDeserialize = null;
            HttpResponseMessage responseMessage;
            string examinationEventSerialized = JsonSerializer.Serialize(examinationEvent);
            Console.WriteLine(examinationEventSerialized);
            var content = new StringContent(examinationEventSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/examinationevent/createExaminationEvent", content);
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
                examinationEventDeserialize = JsonSerializer.Deserialize<ExaminationEvent>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return examinationEventDeserialize;
        }

        public async Task<IList<ExaminationEvent>> GetTeachersUpcomingExamEvents(int currentUserId)
        {
            String idToSend = currentUserId.ToString();
            IList<ExaminationEvent> fetchedExaminationEvents = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getTeachersUpcomingExamEvents/{idToSend}");
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
                fetchedExaminationEvents = JsonSerializer.Deserialize<IList<ExaminationEvent>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return fetchedExaminationEvents;
        }

        public async Task<IList<ExaminationEvent>> GetTeachersPassedExamEvents(int currentUserId)
        {
            String idToSend = currentUserId.ToString();
            IList<ExaminationEvent> fetchedExaminationEvents = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getTeachersPassedExamEvents/{idToSend}");
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
                fetchedExaminationEvents = JsonSerializer.Deserialize<IList<ExaminationEvent>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return fetchedExaminationEvents;
        }

        public async Task<IList<ExaminationEvent>> GetStudentsUpcomingExamEvents(int currentUserId)
        {
            String idToSend = currentUserId.ToString();
            IList<ExaminationEvent> fetchedExaminationEvents = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getStudentsUpcomingExamEvents/{idToSend}");
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
                fetchedExaminationEvents = JsonSerializer.Deserialize<IList<ExaminationEvent>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return fetchedExaminationEvents;  
        }

        public async   Task<IList<ExaminationEvent>> GetStudentsPassedExamEvents(int currentUserId)
        {
            String idToSend = currentUserId.ToString();
            IList<ExaminationEvent> fetchedExaminationEvents = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getStudentsPassedExamEvents/{idToSend}");
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
                fetchedExaminationEvents = JsonSerializer.Deserialize<IList<ExaminationEvent>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return fetchedExaminationEvents; 
        }

        public async Task<IList<ExaminationEvent>> GetStudentsOngoingExamEvents(int currentUserId)
        {
            String idToSend = currentUserId.ToString();
            IList<ExaminationEvent> fetchedExaminationEvents = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getStudentsOngoingExamEvents/{idToSend}");
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
                fetchedExaminationEvents = JsonSerializer.Deserialize<IList<ExaminationEvent>>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return fetchedExaminationEvents; 
        }

        public async Task<ExaminationEvent> GetExaminationPaper(string examId)
        {
           ExaminationEvent fetchedExaminationEventPaper = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getExaminationPaper/{examId}");
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
                fetchedExaminationEventPaper = JsonSerializer.Deserialize<ExaminationEvent>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return fetchedExaminationEventPaper; 
        }

        public async Task<StudentSubmitExaminationPaper> SubmitStudentPaper(StudentSubmitExaminationPaper studentSubmitExaminationPaper)
        {
            StudentSubmitExaminationPaper submittedPaperDeserialize = null;
            HttpResponseMessage responseMessage;
            string examinationEventSerialized = JsonSerializer.Serialize(studentSubmitExaminationPaper);
            Console.WriteLine(examinationEventSerialized);
            var content = new StringContent(examinationEventSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/examinationevent/submitStudentExaminationPaper", content);
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
                submittedPaperDeserialize = JsonSerializer.Deserialize<StudentSubmitExaminationPaper>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return submittedPaperDeserialize;
            
        }

        public async Task<StudentSubmitExaminationPaper> GetStudentSubmittedPaper(int studentId, string examId)
        {
            
            StudentSubmitExaminationPaper studentExamPaper = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getStudentSubmittedPaper/{studentId}/{examId}");
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
                studentExamPaper = JsonSerializer.Deserialize<StudentSubmitExaminationPaper>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }

            return studentExamPaper; 
        }

        public async Task<TeacherEvaluationPaperResult> SubmitEvaluatedStudentPaperByTeacher(
            TeacherEvaluationPaperResult teacherEvaluationPaperResult)
        {
            TeacherEvaluationPaperResult submittedTeacherEvaluationPaperResultDeserialize = null;
            HttpResponseMessage responseMessage;
            string examinationEventSerialized = JsonSerializer.Serialize(teacherEvaluationPaperResult);
            Console.WriteLine(examinationEventSerialized);
            var content = new StringContent(examinationEventSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/examinationevent/submitEvaluatedStudentPaper", content);
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
                submittedTeacherEvaluationPaperResultDeserialize = JsonSerializer.Deserialize<TeacherEvaluationPaperResult>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }

            return submittedTeacherEvaluationPaperResultDeserialize;  
        }

        public async Task<TeacherEvaluationPaperResult> GetExaminationEventResultByExamIdAndStudentId(long currentUserId,long examId)
        { 
            TeacherEvaluationPaperResult studentExamPaperResult = null;
            HttpResponseMessage responseMessage;
            // 1. Send GET request
            try
            {
                responseMessage =
                    await client.GetAsync($"{uri}/examinationevent/getExaminationEventResultByExamIdAndStudentId/{currentUserId}/{examId}");
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
                studentExamPaperResult = JsonSerializer.Deserialize<TeacherEvaluationPaperResult>(readAsStringAsync);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }

            return studentExamPaperResult; 
        }
    }
}