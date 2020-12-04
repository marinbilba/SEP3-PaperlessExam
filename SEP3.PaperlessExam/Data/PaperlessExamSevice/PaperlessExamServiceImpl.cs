﻿using System;
 using System.Collections.Generic;
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
                    await client.PostAsync(uri + "/user/login", content);
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
        public async Task CreateUserAsync(User user)
        {
            User userDeserialize = null;
            HttpResponseMessage responseMessage;
            string userSerialized = JsonSerializer.Serialize(user);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/user/createUser", content);
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
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }   else if (responseMessage.StatusCode == HttpStatusCode.Unauthorized)
            {
                throw new Exception(serverMessage);
            }
            else if (responseMessage.StatusCode == HttpStatusCode.Conflict)
            {
                throw new Exception(serverMessage);
            }

            
        }

        public async Task<IList<User>> FindByFirstName(string name)
        {
            
            IList<User> usersDeserialize = null;
            HttpResponseMessage responseMessage;
            IList<string> usersSerialized = new[] {JsonSerializer.Serialize(name)};
            foreach (var s in usersSerialized)
            {
                var content = new StringContent(s, Encoding.UTF8, "application/json");
                // 1. Send POST request
                try
                {
                    responseMessage =
                        await client.PostAsync(uri + "/user/findUserByUsername", content);
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
                    usersDeserialize = JsonSerializer.Deserialize<IList<User>>(readAsStringAsync);
                    foreach (var x in usersDeserialize)
                    {
                        Console.WriteLine(x.Role.Name);
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
            
            }
            return usersDeserialize;
            // List<User> users = new List<User>();
            // users.Add(new User("HEJ", "tyuiop", "fgjukil", "dfgnhjmk", "tgyhuj", new Role("teacher"), ""));
            // return users;
        }
         
        public async Task<User> FindByUsername(string username)
        {
            User userDeserialize = null;
            HttpResponseMessage responseMessage;
            string userSerialized = JsonSerializer.Serialize(username);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/user/findUserByUsername", content);
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
            else if (responseMessage.StatusCode == HttpStatusCode.ServiceUnavailable)
            {
                throw new Exception(serverMessage);
            }   else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }
           
            // User user = new User("HEJ", "tyuiop", "fgjukil", "dfgnhjmk", "tgyhuj", new Role("teacher"),"@");
            return userDeserialize;
            // return user;
        }

        public async Task DeleteUser(string username)
        {
            User userDeserialize = null;
            HttpResponseMessage responseMessage;
            string userSerialized = JsonSerializer.Serialize(username);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/user/deleteUser", content);
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
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }  

        }

        public async Task UpdateUser(User user)
        {
            User userDeserialize = null;
            HttpResponseMessage responseMessage;
            string userSerialized = JsonSerializer.Serialize(user);
            var content = new StringContent(userSerialized, Encoding.UTF8, "application/json");
            // 1. Send POST request
            try
            {
                responseMessage =
                    await client.PostAsync(uri + "/user/updateUser", content);
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
            else if (responseMessage.StatusCode == HttpStatusCode.BadRequest)
            {
                throw new Exception(serverMessage);
            }   
           
        }
    }

}