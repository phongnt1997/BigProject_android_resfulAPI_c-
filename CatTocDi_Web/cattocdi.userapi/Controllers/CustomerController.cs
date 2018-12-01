using cattocdi.Service.Interface;
using cattocdi.Service.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Web.Http;

namespace cattocdi.userapi.Controllers
{
    [RoutePrefix("api/Customer")]
    [Authorize(Roles = "User")]
    public class CustomerController : ApiController
    {
        ICustomerService _cusService;
        public CustomerController(ICustomerService cusService)
        {
            _cusService = cusService;
        } 
        // GET: api/Customer
        public IHttpActionResult Get()
        {
            try
            {
                var identity = (ClaimsIdentity)User.Identity;
                string username = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
                var profile = _cusService.GetCustomerProfile(username);
                return Json(profile);
            }
            catch(Exception ex)
            {
                return BadRequest("Get Salon Failed");
            }
            
        }

        [HttpPost]
        [Route("Profile")]
        public IHttpActionResult Update(ProfileViewModel profile)
        {
            var identity = (ClaimsIdentity)User.Identity;
            var accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
            profile.AccountId = accountId;
            
            bool result = _cusService.UpdateCustomerProfile(profile);
            if(result)
            {
                return Ok("Update Success");
            } else
            {
                return BadRequest();
            }
        }
    }
}
