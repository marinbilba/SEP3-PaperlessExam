﻿using System.Collections.Generic;
 using System.Threading.Tasks;
using SEP3.PaperlessExam.Model;

namespace SEP3.PaperlessExam.Data.PaperlessExamSevice
{
    public interface IPaperlessExamService
    {
       Task <User> LoginUser(User user);
       Task CreateUserAsync(User createUser);
       Task<IList<User>> FindByFirstName(string name);
       Task<User> FindByUsername(string username);
       Task<string> DeleteUser(string username);
    }
}