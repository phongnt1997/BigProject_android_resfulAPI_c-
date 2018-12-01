using cattocdi.Service.Constant;
using cattocdi.Service.Interface;
using cattocdi.Service.ViewModel.User;
using cattocdi.userapi.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Web.Http;

namespace cattocdi.userapi.Controllers
{
    [RoutePrefix("api/Account")]
    public class AccountController : ApiController
    {
        private ISalonServices _salonService;
        private ICustomerService _customerService;
        public AccountController(ISalonServices salonService, ICustomerService customerService)
        {
            _salonService = salonService;
            _customerService = customerService;
        }
        [Route("Register")]
        [HttpPost]
        [AllowAnonymous]
        public IdentityResult UserRegister(UserAccountModel model)
        {
            IdentityResult result = null;
            try
            {
                var userStore = new UserStore<ApplicationUser>(new ApplicationDbContext());
                var manager = new UserManager<ApplicationUser>(userStore);
                var user = new ApplicationUser()
                {
                    UserName = model.UserName,
                    Email = model.Email
                    
                };
                manager.PasswordValidator = new PasswordValidator()
                {
                    RequiredLength = 3
                };
                result = manager.Create(user, model.Password);
                if (result.Succeeded)
                {
                    var newCustomer = new CustomerViewModel
                    {
                        FirstName = model.FirstName,
                        LastName = model.LastName,
                        AccountId = user.Id,
                        Gender = model.Gender,
                        Email = model.Email,
                        Phone = model.PhoneNumber
                    };
                    _customerService.CreateCustomerAccount(newCustomer);
                    manager.AddToRole(user.Id, RoleConstant.USER);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error IN Customer Registter " + ex.Message);
            }
            return result;
        }


      
        [HttpGet]
        [Route("~/api/GetUserClaims")]
        public AccountModel GetUserClaims()
        {
            var identityClaims = (ClaimsIdentity)User.Identity;
            IEnumerable<Claim> claims = identityClaims.Claims;
            AccountModel model = new AccountModel
            {
                Email = identityClaims.FindFirst("Email").Value,
                UserName = identityClaims.FindFirst("Username").Value,
                LoggedOn = identityClaims.FindFirst("LoggedOn").Value
            };
            return model;
        }

        
    }
}
