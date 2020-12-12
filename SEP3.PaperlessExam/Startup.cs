using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Blazored.Toast;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using SEP3.PaperlessExam.Authentication;
using SEP3.PaperlessExam.Data;

using SEP3.PaperlessExam.Data.PaperlessExamSevice;
using SEP3.PaperlessExam.Data.PaperlessExamSevice.ExamEvent;
using SEP3.PaperlessExam.Data.PaperlessExamSevice.QuestionSetsService;

namespace SEP3.PaperlessExam
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddRazorPages();
            services.AddServerSideBlazor();
            services.AddSingleton<WeatherForecastService>();
            services.AddScoped<IUserService, UserServiceImpl>();
            services.AddScoped<IQuestionSetsService, QuestionSetsServiceImpl>();
            services.AddScoped<IExamService, ExamService>();
            services.AddBlazoredToast();
            
          services.AddScoped<AuthenticationStateProvider, CustomAuthenticationStateProvider>();
          
            
            services.AddAuthorization(options => {
                options.AddPolicy("MustBeStudent",  a => 
                    a.RequireAuthenticatedUser().RequireClaim("Role", "Student"));
                options.AddPolicy("MustBeTeacher",  a => 
                    a.RequireAuthenticatedUser().RequireClaim("Role", "Teacher"));
                options.AddPolicy("MustBeAdmin",  a => 
                    a.RequireAuthenticatedUser().RequireClaim("Role", "Admin"));

            });
            
        }
        

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Error");
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapBlazorHub();
                endpoints.MapFallbackToPage("/_Host");
            });
        }
    }
}