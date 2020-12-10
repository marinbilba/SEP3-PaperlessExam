﻿using System.Collections.Generic;
 using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice
{
    public interface IUserService
    {
       Task <User> LoginUser(User user);
       Task<User> CreateUserAsync(User createUser);
       Task<IList<User>> FindByFirstName(string firstName);
       Task<User> FindByUsername(string username);
       Task<User> DeleteUser(User user);
       Task<User> UpdateUser(User user);

       Task<User> FindStudentByUsername(string username);
       Task<IList<User>> FindStudentByFirstName(string firstName);
    }
}