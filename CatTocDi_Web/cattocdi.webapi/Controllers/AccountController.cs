using cattocdi.salonservice.Constant;
using cattocdi.salonservice.Interface;
using cattocdi.salonservice.ViewModel;
using cattocdi.webapi.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Web.Http;

namespace cattocdi.webapi.Controllers
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
        public IdentityResult SalonRegister(SalonAccountModel model)
        {
            IdentityResult result = null;
            try
            {
                var userStore = new UserStore<ApplicationUser>(new ApplicationDbContext());
                var manager = new UserManager<ApplicationUser>(userStore);
                var user = new ApplicationUser()
                {
                    UserName = model.UserName,
                    Email = model.Email,     
                    PhoneNumber = model.PhoneNumber,                          
                };
                manager.PasswordValidator = new PasswordValidator()
                {
                    RequiredLength = 3
                };                
                result = manager.Create(user, model.Password);                
                if (result.Succeeded)
                {
                    var createRoleResult = manager.AddToRole(user.Id, RoleConstant.SALON);
                    if (createRoleResult.Succeeded)
                    {
                        var newSalon = new SalonViewModel
                        {
                            SalonName = model.SalonName,
                            Address = model.Address,
                            AccountId = user.Id,
                            IsForMen = model.IsForMen,
                            IsForWomen = model.IsForWomen,     
                            Phone = model.PhoneNumber,
                            Email = model.Email,
                            RatingAvarage = 0                            
                        };
                        _salonService.RegisterSalonAccount(newSalon);
                    }                                  
                }
            }
            catch(Exception ex)
            {
                Elmah.ErrorSignal.FromCurrentContext().Raise(ex);                               
            }          
            return result;
        }

        [HttpGet]
        [Route("~/api/GetUserClaims")]        
        public AccountModel GetUserClaims()
        {
            var identityClaims = (ClaimsIdentity)User.Identity;
            AccountModel model = null;
            try
            {                
                IEnumerable<Claim> claims = identityClaims.Claims;
                model = new AccountModel
                {
                    Email = identityClaims.FindFirst("Email").Value,
                    UserName = identityClaims.FindFirst("Username").Value,
                    LoggedOn = identityClaims.FindFirst("LoggedOn").Value
                };
            }
            catch(Exception ex)
            {
                Elmah.ErrorSignal.FromCurrentContext().Raise(ex);
            }            
            return model; 
        }
    }
}

//[Route("User/Register")]
//[HttpPost]
//[AllowAnonymous]
//public IdentityResult UserRegister(UserAccountModel model)
//{
//    IdentityResult result = null;
//    try
//    {
//        var userStore = new UserStore<ApplicationUser>(new ApplicationDbContext());
//        var manager = new UserManager<ApplicationUser>(userStore);
//        var user = new ApplicationUser()
//        {
//            UserName = model.UserName,
//            Email = model.Email,
//        };
//        manager.PasswordValidator = new PasswordValidator()
//        {
//            RequiredLength = 3
//        };
//        result = manager.Create(user, model.Password);
//        if (result.Succeeded && model.Role != null)
//        {
//            var newCustomer = new CustomerViewModel
//            {
//                FirstName = model.FirstName,
//                LastName = model.LastName,
//                AccountId = user.Id,
//                Gender = model.Gender
//            };
//            _customerService.CreateCustomerAccount(newCustomer);
//            manager.AddToRole(user.Id, "Admin");
//        }
//    }
//    catch(Exception ex)
//    {
//        Console.WriteLine("Error IN Customer Registter " + ex.Message);
//    }           
//    return result;
//}
